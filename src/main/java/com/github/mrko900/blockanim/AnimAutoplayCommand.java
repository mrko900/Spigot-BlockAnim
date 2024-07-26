package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class AnimAutoplayCommand implements AnimCommand {
    private Plugin plugin;
    private MessageManager messageManager;

    public AnimAutoplayCommand(Plugin plugin, MessageManager messageManager) {
        this.plugin = plugin;
        this.messageManager = messageManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            return false;
        }
        String name = args[0];
        FileConfiguration config = plugin.getConfig();
        String s;
        if (config.getStringList("autoplay").contains(name)) {
            List<String> list = config.getStringList("autoplay");
            list.remove(name);
            config.set("autoplay", list);
            s = messageManager.get("disabled");
        } else {
            List<String> list = config.getStringList("autoplay");
            list.add(name);
            config.set("autoplay", list);
            s = messageManager.get("enabled");
        }
        sender.sendMessage(messageManager.get("anim.autoplay0") + s
                           + messageManager.get("anim.autoplay1") + name
                           + messageManager.get("anim.autoplay2"));
        plugin.saveConfig();
        return true;
    }
}
