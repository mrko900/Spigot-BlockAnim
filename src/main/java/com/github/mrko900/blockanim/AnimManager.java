package com.github.mrko900.blockanim;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
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

    public void deleteAnim(String name) throws IOException {
        Files.delete(getAnimFile(name).toPath());
    }

    private File getAnimFile(String name) {
        return new File(plugin.getDataFolder().getPath() + File.separator + "anims"
                        + File.separator + name + ".yml");
    }

    public boolean loadAnim(String name) {
        if (isAnimLoaded(name)) {
            return true;
        }
        File file = getAnimFile(name);
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

    public boolean animExists(String name) {
        return getAnimFile(name).exists();
    }

    public void setAutoplay(String name, boolean enabled) {
        FileConfiguration config = plugin.getConfig();
        List<String> list = config.getStringList("autoplay");
        if (enabled) {
            list.add(name);
        } else {
            list.remove(name);
        }
        config.set("autoplay", list);
        plugin.saveConfig();
    }
}
