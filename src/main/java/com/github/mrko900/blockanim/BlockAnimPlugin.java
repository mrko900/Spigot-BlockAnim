package com.github.mrko900.blockanim;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BlockAnimPlugin extends JavaPlugin {
    private FileConfiguration config;
    private AnimManager animManager;
    private Properties messages;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        super.onEnable();

        try {
            loadConfig();
            loadMessages();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AnimBuilderManager animBuilderManager = new AnimBuilderManager(messageManager);
        Bukkit.getPluginManager().registerEvents(animBuilderManager, this);
        Map<String, AnimCommand> executors = new HashMap<>();
        animManager = new AnimManager(this);
        executors.put("new", new AnimNewCommand(animBuilderManager, messageManager));
        executors.put("phase", new AnimSavePhaseCommand(animBuilderManager, messageManager));
        executors.put("save", new AnimSaveCommand(animBuilderManager, getDataFolder().getPath(), messageManager));
        executors.put("play", new AnimPlayCommand(animManager, messageManager));
        executors.put("stop", new AnimStopCommand(animManager, messageManager));
        executors.put("pos1", new AnimPosCommand(animBuilderManager, messageManager, true));
        executors.put("pos2", new AnimPosCommand(animBuilderManager, messageManager, false));
        executors.put("autoplay", new AnimAutoplayCommand(this, animManager, messageManager));
        executors.put("delete", new AnimDeleteCommand(animManager, messageManager));
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

    private void loadMessages() throws IOException {
        File file = new File(getDataFolder(), "messages.properties");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            Files.copy(getResource("messages.properties"), file.toPath());
        }
        messages = new Properties();
        try (FileReader fr = new FileReader(file)) {
            messages.load(fr);
        }

        Properties sample = new Properties();
        sample.load(getResource("messages.properties"));
        for (Object key : sample.keySet()) {
            if (!messages.containsKey(key)) {
                throw new RuntimeException("todo");
            }
        }

        messageManager = new MessageManager(messages);
    }
}
