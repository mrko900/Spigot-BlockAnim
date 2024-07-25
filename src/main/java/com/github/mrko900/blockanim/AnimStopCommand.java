package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;

public class AnimStopCommand implements AnimCommand {
    private AnimManager animManager;

    public AnimStopCommand(AnimManager animManager) {
        this.animManager = animManager;
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
        animManager.stopAnim(name);
        animManager.unloadAnim(name);
        return true;
    }
}
