package com.github.mrko900.blockanim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnimSavePhaseCommand implements AnimCommand {
    AnimBuilderManager manager;

    public AnimSavePhaseCommand(AnimBuilderManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        AnimBuilder builder = manager.getAnimBuilder(player);
        if (builder == null) {
            player.sendMessage("....");
            return true;
        }
        if (!builder.isFirstPointSet() || !builder.isSecondPointSet()) {
            player.sendMessage("set bounds first");
            return true;
        }
        if (args.length != 1) {
            return false;
        }
        int val;
        try {
            val = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("invalid number format");
            return true;
        }
        AnimPhase phase = builder.getCurrentPhase();
        phase.setDuration(val);
        phase.saveBlocks(
                builder.getX0(), builder.getY0(), builder.getZ0(), builder.getX1(), builder.getY1(), builder.getZ1()
        );
        player.sendMessage("phase complete");
        return true;
    }
}
