package com.github.mrko900.blockanim;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class BlockAnimPlugin extends JavaPlugin {
    private FileConfiguration config;
    private AnimManager animManager;

    @Override
    public void onEnable() {
        super.onEnable();

        try {
            loadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AnimBuilderManager animBuilderManager = new AnimBuilderManager();
        Bukkit.getPluginManager().registerEvents(animBuilderManager, this);
        Map<String, AnimCommand> executors = new HashMap<>();
        animManager = new AnimManager(this);
        executors.put("new", new AnimNewCommand(animBuilderManager));
        executors.put("phase", new AnimSavePhaseCommand(animBuilderManager));
        executors.put("save", new AnimSaveCommand(animBuilderManager, getDataFolder().getPath()));
        executors.put("play", new AnimPlayCommand(animManager));
        executors.put("stop", new AnimStopCommand(animManager));
        executors.put("pos1", new AnimPosCommand(animBuilderManager, true));
        executors.put("pos2", new AnimPosCommand(animBuilderManager, false));
        executors.put("autoplay", new AnimAutoplayCommand(this));
        executors.put("help", new AnimHelpCommand(this));
        getCommand("anim").setExecutor(new AnimCommandExecutor(executors));

        autoPlayAnims();
    }

    private void loadConfig() throws IOException {
        if (!new File(getDataFolder(), "config.yml").exists()) {
            createDefaultConfig();
        }
        config = getConfig();
    }

    private void createDefaultConfig() throws IOException {
        getDataFolder().mkdirs();
        File file = new File(getDataFolder(), "config.yml");
        Files.copy(getResource("config.yml"), file.toPath());
    }

    private void autoPlayAnims() {
        for (String name : config.getStringList("autoplay")) {
            if (animManager.loadAnim(name)) {
                animManager.playAnim(name);
            }
        }
    }
}
