package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class AnimPlayCommand implements AnimCommand {
    private AnimManager animManager;

    public AnimPlayCommand(AnimManager animManager) {
        this.animManager = animManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            return false;
        }
        String name = args[0];
        if (!animManager.loadAnim(name)) {
            sender.sendMessage("no such anim.");
            return true;
        }
        animManager.playAnim(name);
        return true;
    }
}
