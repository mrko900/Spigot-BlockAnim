package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class AnimAutoplayCommand implements AnimCommand {
    private Plugin plugin;
    private MessageManager messageManager;
    private AnimManager animManager;

    public AnimAutoplayCommand(Plugin plugin, MessageManager messageManager, AnimManager animManager) {
        this.plugin = plugin;
        this.messageManager = messageManager;
        this.animManager = animManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(messageManager.get("invalidCommand") + ' '
                               + messageManager.get("anim.cmdAutoplayUsage"));
            return true;
        }
        String name = args[0];
        if (!animManager.animExists(name)) {
            sender.sendMessage(messageManager.get("anim.doesntExist0") + name
                               + messageManager.get("anim.doesntExist1"));
            return true;
        }
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
