package com.github.mrko900.blockanim;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AnimManager {
    private Map<String, BlockAnim> animMap = new HashMap<>();
    private Plugin plugin;

    public AnimManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void playAnim(String name) {
        if (!isAnimLoaded(name)) {
            throw new IllegalStateException("not loaded");
        }
        animMap.get(name).start();
    }

    public void stopAnim(String name) {
        if (!isAnimPlaying(name)) {
            throw new IllegalStateException("not playing");
        }
        animMap.get(name).stop();
    }

    public boolean loadAnim(String name) {
        if (isAnimLoaded(name)) {
            return true;
        }
        File file = new File(plugin.getDataFolder().getPath() + File.separator + "anims"
                             + File.separator + name + ".yml");
        if (!file.exists()) {
            return false;
        }
        animMap.put(name, BlockAnim.fromFile(file, plugin));
        return true;
    }

    public void unloadAnim(String name) {
        animMap.remove(name);
    }

    public boolean isAnimLoaded(String name) {
        return animMap.containsKey(name);
    }

    public boolean isAnimPlaying(String name) {
        return isAnimLoaded(name) && animMap.get(name).isRunning();
    }
}
