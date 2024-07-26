package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class AnimSaveCommand implements AnimCommand {
    private AnimBuilderManager animBuilderManager;
    private String pluginFolder;
    private MessageManager messageManager;

    public AnimSaveCommand(AnimBuilderManager listener, String pluginFolder, MessageManager messageManager) {
        this.animBuilderManager = listener;
        this.pluginFolder = pluginFolder;
        this.messageManager = messageManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 1) {
            sender.sendMessage(messageManager.get("invalidCommand") + ' '
                               + messageManager.get("anim.cmdSaveUsage"));
            return true;
        }
        AnimBuilder builder = animBuilderManager.getAnimBuilder(player);
        if (builder == null) {
            sender.sendMessage(messageManager.get("anim.notInBuilderMode"));
            return true;
        }
        if (builder.getPhaseCount() < 2) {
            sender.sendMessage(messageManager.get("anim.notEnoughPhases"));
            return true;
        }
        builder.setName(args[0]);
        animBuilderManager.removePlayer(player);
        try {
            builder.saveAnim(pluginFolder + File.separator + "anims");
        } catch (IOException e) {
            sender.sendMessage("IO exception has occurred");
            e.printStackTrace();
            return true;
        }
        player.sendMessage(messageManager.get("anim.save0") + args[0] + messageManager.get("anim.save1"));
        return true;
    }
}
