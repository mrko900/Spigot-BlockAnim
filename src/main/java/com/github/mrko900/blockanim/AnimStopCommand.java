package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;

public class AnimStopCommand implements AnimCommand {
    private AnimManager animManager;
    private MessageManager messageManager;

    public AnimStopCommand(AnimManager animManager, MessageManager messageManager) {
        this.animManager = animManager;
        this.messageManager = messageManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(messageManager.get("invalidCommand") + ' '
                               + messageManager.get("anim.cmdStopUsage"));
            return true;
        }
        String name = args[0];
        if (!animManager.animExists(name)) {
            sender.sendMessage(messageManager.get("anim.doesntExist0") + name
                               + messageManager.get("anim.doesntExist1"));
            return true;
        }
        if (!animManager.isAnimPlaying(name)) {
            sender.sendMessage(messageManager.get("anim.notPlaying0") + name
                               + messageManager.get("anim.notPlaying1"));
            return true;
        }
        sender.sendMessage(messageManager.get("anim.stop0") + name + messageManager.get("anim.stop1"));
        animManager.stopAnim(name);
        animManager.unloadAnim(name);
        return true;
    }
}
