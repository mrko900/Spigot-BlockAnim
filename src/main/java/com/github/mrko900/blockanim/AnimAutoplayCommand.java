package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class AnimAutoplayCommand implements AnimCommand {
    private Plugin plugin;

    public AnimAutoplayCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            return false;
        }
        String name = args[0];
        FileConfiguration config = plugin.getConfig();
        if (config.getStringList("autoplay").contains(name)) {
            List<String> list = config.getStringList("autoplay");
            list.remove(name);
            config.set("autoplay", list);
            sender.sendMessage("autoplay disabled");
        } else {
            List<String> list = config.getStringList("autoplay");
            list.add(name);
            config.set("autoplay", list);
            sender.sendMessage("autoplay enabled");
        }
        plugin.saveConfig();
        return true;
    }
}
