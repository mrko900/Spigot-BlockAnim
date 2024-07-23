package com.github.mrko900.blockanim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class PlayAnimCommand implements CommandExecutor {
    private Plugin plugin;

    public PlayAnimCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
