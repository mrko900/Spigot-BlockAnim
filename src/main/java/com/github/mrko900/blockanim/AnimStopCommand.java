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
            return false;
        }
        String name = args[0];
        if (!animManager.isAnimPlaying(name)) {
            sender.sendMessage("anim not playing.");
            return true;
        }
        sender.sendMessage(messageManager.get("anim.stop0") + name + messageManager.get("anim.stop1"));
        animManager.stopAnim(name);
        animManager.unloadAnim(name);
        return true;
    }
}
