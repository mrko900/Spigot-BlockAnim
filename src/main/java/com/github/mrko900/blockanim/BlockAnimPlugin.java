package com.github.mrko900.blockanim;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockAnimPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        AnimBuilderListener animBuilderListener = new AnimBuilderListener();
        Bukkit.getPluginManager().registerEvents(animBuilderListener, this);
        getCommand("newanim").setExecutor(new NewAnimCommand(animBuilderListener));
        getCommand("saveanim").setExecutor(new SaveAnimCommand(animBuilderListener, getDataFolder().getPath()));
    }
}
