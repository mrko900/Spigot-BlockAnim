package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnimPosCommand implements AnimCommand {
    private AnimBuilderManager manager;
    private MessageManager messageManager;
    private boolean first;

    public AnimPosCommand(AnimBuilderManager manager, MessageManager messageManager, boolean first) {
        this.manager = manager;
        this.messageManager = messageManager;
        this.first = first;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 3) {
            sender.sendMessage(messageManager.get("invalidCommand") + ' '
                               + messageManager.get(first ? "anim.cmdPos1Usage" : "anim.cmdPos2Usage"));
            return true;
        }
        int x, y, z;
        try {
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
            z = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage(messageManager.get("invalidNumberFormat"));
            return true;
        }
        AnimBuilder anim = manager.getAnimBuilder(player);
        if (anim == null) {
            player.sendMessage(messageManager.get("anim.notInBuilderMode"));
            return true;
        }
        if (anim.isFirstPointSet() && anim.isSecondPointSet()) {
            player.sendMessage(messageManager.get("anim.regionAlreadySpecified"));
            return true;
        }
        if (first) {
            manager.setFirstPoint(player, x, y, z);
        } else {
            manager.setSecondPoint(player, x, y, z);
        }
        return true;
    }
}
