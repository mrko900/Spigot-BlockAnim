package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;

public class AnimPlayCommand implements AnimCommand {
    private AnimManager animManager;
    private MessageManager messageManager;

    public AnimPlayCommand(AnimManager animManager, MessageManager messageManager) {
        this.animManager = animManager;
        this.messageManager = messageManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(messageManager.get("invalidCommand") + ' '
                               + messageManager.get("anim.cmdPlayUsage"));
            return true;
        }
        String name = args[0];
        if (!animManager.loadAnim(name)) {
            sender.sendMessage(messageManager.get("anim.doesntExist0") + name
                               + messageManager.get("anim.doesntExist1"));
            return true;
        }
        sender.sendMessage(messageManager.get("anim.play0") + name + messageManager.get("anim.play1"));
        animManager.playAnim(name);
        return true;
    }
}
