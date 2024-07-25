package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class AnimHelpCommand implements AnimCommand {
    private Plugin plugin;

    public AnimHelpCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage("tipo help");
        return true;
    }
}
