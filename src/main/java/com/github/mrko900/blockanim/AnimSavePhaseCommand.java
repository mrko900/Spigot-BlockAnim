package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnimSavePhaseCommand implements AnimCommand {
    private AnimBuilderManager manager;
    private MessageManager messageManager;

    public AnimSavePhaseCommand(AnimBuilderManager manager, MessageManager messageManager) {
        this.manager = manager;
        this.messageManager = messageManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        AnimBuilder builder = manager.getAnimBuilder(player);
        if (builder == null) {
            player.sendMessage(messageManager.get("anim.notInBuilderMode"));
            return true;
        }
        if (!builder.isFirstPointSet() || !builder.isSecondPointSet()) {
            player.sendMessage(messageManager.get("anim.regionNotSpecified"));
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(messageManager.get("invalidCommand") + ' '
                               + messageManager.get("anim.cmdPhaseUsage"));
            return true;
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
        player.sendMessage(messageManager.get("anim.phase") + builder.getPhaseCount());
        return true;
    }
}
