package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;

import java.io.IOException;

public class AnimDeleteCommand implements AnimCommand {
    private AnimManager animManager;
    private MessageManager messageManager;

    public AnimDeleteCommand(AnimManager animManager, MessageManager messageManager) {
        this.animManager = animManager;
        this.messageManager = messageManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(messageManager.get("invalidCommand") + ' '
                               + messageManager.get("anim.cmdDeleteUsage"));
            return true;
        }
        String name = args[0];
        if (!animManager.animExists(name)) {
            sender.sendMessage(messageManager.get("anim.doesntExist0") + name
                               + messageManager.get("anim.doesntExist1"));
            return true;
        }
        if (animManager.isAnimPlaying(name)) {
            animManager.stopAnim(name);
        }
        if (animManager.isAnimLoaded(name)) {
            animManager.unloadAnim(name);
        }
        try {
            animManager.deleteAnim(name);
        } catch (IOException e) {
            sender.sendMessage("IO exception has occurred");
            e.printStackTrace();
            return true;
        }
        sender.sendMessage(messageManager.get("anim.deleted0") + name + messageManager.get("anim.deleted1"));
        return true;
    }
}
