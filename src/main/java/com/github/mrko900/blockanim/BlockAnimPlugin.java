package com.github.mrko900.blockanim;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class BlockAnimPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        AnimBuilderManager animBuilderManager = new AnimBuilderManager();
        Bukkit.getPluginManager().registerEvents(animBuilderManager, this);
        Map<String, AnimCommand> executors = new HashMap<>();
        AnimManager animManager = new AnimManager(this);
        executors.put("new", new NewAnimCommand(animBuilderManager));
        executors.put("phase", new SavePhaseCommand(animBuilderManager));
        executors.put("save", new SaveAnimCommand(animBuilderManager, getDataFolder().getPath()));
        executors.put("play", new PlayAnimCommand(animManager));
        executors.put("stop", new StopAnimCommand(animManager));
        executors.put("pos1", new AnimPosCommand(animBuilderManager, true));
        executors.put("pos2", new AnimPosCommand(animBuilderManager, false));
        getCommand("anim").setExecutor(new AnimCommandExecutor(executors));
    }
}
