package com.github.mrko900.blockanim;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnimBuilderListener implements Listener {
    private Map<UUID, AnimBuilder> animMap = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        AnimBuilder anim = animMap.get(event.getPlayer().getUniqueId());
        if (anim == null) {
            return;
        }
        event.setCancelled(true);
        if (anim.getCurrentPhase().getState() == AnimPhase.State.COMPLETE) {
            event.getPlayer().sendMessage("type the duration!");
            return;
        }
        anim.getCurrentPhase().addPoint(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
        switch (anim.getCurrentPhase().getState()) {
            case P1_SET:
                event.getPlayer().sendMessage("first point set");
                break;
            case COMPLETE:
                event.getPlayer().sendMessage("phase completed. type duration");
                break;
            default:
                throw new AssertionError();
        }
    }

    @EventHandler
    public void durationListener(AsyncPlayerChatEvent event) {
        if (!animMap.containsKey(event.getPlayer().getUniqueId())) {
            return;
        }
        AnimBuilder builder = animMap.get(event.getPlayer().getUniqueId());
        if (builder.getCurrentPhase().getState() != AnimPhase.State.COMPLETE) {
            return;
        }
        event.setCancelled(true);
        int val;
        try {
            val = Integer.parseInt(event.getMessage());
        } catch (NumberFormatException e) {
            event.getPlayer().sendMessage("invalid number format");
            return;
        }
        event.getPlayer().sendMessage("ok number " + val);
        builder.getCurrentPhase().setDuration(val);
        builder.newPhase();
    }

    public void addPlayer(Player player) {
        AnimBuilder builder = new AnimBuilder(player.getWorld());
        builder.newPhase();
        animMap.put(player.getUniqueId(), builder);
    }

    public void removePlayer(Player player) {
        animMap.remove(player.getUniqueId());
    }

    public AnimBuilder getAnimBuilder(Player player) {
        return animMap.get(player.getUniqueId());
    }
}
