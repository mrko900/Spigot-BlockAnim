package com.github.mrko900.blockanim;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockAnimPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        AnimBuilderManager animBuilderManager = new AnimBuilderManager();
        Bukkit.getPluginManager().registerEvents(animBuilderManager, this);
        getCommand("newanim").setExecutor(new NewAnimCommand(animBuilderManager));
        getCommand("saveanim").setExecutor(new SaveAnimCommand(animBuilderManager, getDataFolder().getPath()));
        getCommand("savephase").setExecutor(new SavePhaseCommand(animBuilderManager));
        getServer().getWorld("as").getUID();
    }
}
