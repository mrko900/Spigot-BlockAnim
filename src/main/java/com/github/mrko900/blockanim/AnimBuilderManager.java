package com.github.mrko900.blockanim;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnimBuilderManager implements Listener {
    private Map<UUID, AnimBuilder> animMap = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        AnimBuilder anim = animMap.get(event.getPlayer().getUniqueId());
        if (anim == null || anim.isFirstPointSet() && anim.isSecondPointSet()) {
            return;
        }
        event.setCancelled(true);
        if (!anim.isFirstPointSet()) {
            event.getPlayer().sendMessage("first point set");
        } else {
            event.getPlayer().sendMessage("second point set");
        }
        anim.addPoint(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
    }

    public void setFirstPoint(Player player, int x, int y, int z) {
        AnimBuilder anim = animMap.get(player.getUniqueId());
        if (anim == null) {
            return;
        }
        anim.setFirstPoint(x, y, z);
    }

    public void setSecondPoint(Player player, int x, int y, int z) {
        AnimBuilder anim = animMap.get(player.getUniqueId());
        if (anim == null) {
            return;
        }
        anim.setSecondPoint(x, y, z);
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
