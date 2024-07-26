package com.github.mrko900.blockanim;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnimBuilderManager implements Listener {
    private Map<UUID, AnimBuilder> animMap = new HashMap<>();
    private MessageManager messageManager;

    public AnimBuilderManager(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        AnimBuilder anim = animMap.get(event.getPlayer().getUniqueId());
        if (anim == null || anim.isFirstPointSet() && anim.isSecondPointSet()) {
            return;
        }
        event.setCancelled(true);
        String xyz = event.getBlock().getX() + " " + event.getBlock().getY() + " " + event.getBlock().getZ();
        if (!anim.isFirstPointSet()) {
            event.getPlayer().sendMessage(messageManager.get("anim.p1set") + xyz);
        } else {
            event.getPlayer().sendMessage(messageManager.get("anim.p2set") + xyz);
        }
        anim.addPoint(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
        checkBothSet(event.getPlayer(), anim);
    }

    public void setFirstPoint(Player player, int x, int y, int z) {
        AnimBuilder anim = animMap.get(player.getUniqueId());
        if (anim == null) {
            return;
        }
        String xyz = x + " " + y + " " + z;
        anim.setFirstPoint(x, y, z);
        player.sendMessage(messageManager.get("anim.p1set") + xyz);
        checkBothSet(player, anim);
    }

    public void setSecondPoint(Player player, int x, int y, int z) {
        AnimBuilder anim = animMap.get(player.getUniqueId());
        if (anim == null) {
            return;
        }
        String xyz = x + " " + y + " " + z;
        anim.setSecondPoint(x, y, z);
        player.sendMessage(messageManager.get("anim.p2set") + xyz);
        checkBothSet(player, anim);
    }

    private void checkBothSet(Player player, AnimBuilder anim) {
        if (anim.isFirstPointSet() && anim.isSecondPointSet()) {
            player.sendMessage(messageManager.get("anim.bothPointsSet"));
        }
    }

    public void addPlayer(Player player) {
        AnimBuilder builder = new AnimBuilder(player.getWorld());
        animMap.put(player.getUniqueId(), builder);
    }

    public void removePlayer(Player player) {
        animMap.remove(player.getUniqueId());
    }

    public AnimBuilder getAnimBuilder(Player player) {
        return animMap.get(player.getUniqueId());
    }
}
