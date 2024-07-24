package com.github.mrko900.blockanim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class PlayAnimCommand implements AnimCommand {
    private Plugin plugin;

    public PlayAnimCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            return false;
        }
        String name = args[0];
        BlockAnim anim = BlockAnim.fromFile(
                new File(plugin.getDataFolder().getPath() + "\\" + name + ".yml"), plugin
        );
        anim.start();
        return true;
    }
}
