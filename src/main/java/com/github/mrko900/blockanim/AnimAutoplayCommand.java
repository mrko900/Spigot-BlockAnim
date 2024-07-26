package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class AnimAutoplayCommand implements AnimCommand {
    private Plugin plugin;
    private AnimManager animManager;
    private MessageManager messageManager;

    public AnimAutoplayCommand(Plugin plugin, AnimManager animManager, MessageManager messageManager) {
        this.plugin = plugin;
        this.animManager = animManager;
        this.messageManager = messageManager;
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
            animManager.setAutoplay(name, false);
            s = messageManager.get("disabled");
        } else {
            animManager.setAutoplay(name, true);
            s = messageManager.get("enabled");
        }
        sender.sendMessage(messageManager.get("anim.autoplay0") + s
                           + messageManager.get("anim.autoplay1") + name
                           + messageManager.get("anim.autoplay2"));
        plugin.saveConfig();
        return true;
    }
}
