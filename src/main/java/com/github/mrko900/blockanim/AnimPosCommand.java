package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnimPosCommand implements AnimCommand {
    private AnimBuilderManager manager;
    private boolean first;

    public AnimPosCommand(AnimBuilderManager manager, boolean first) {
        this.manager = manager;
        this.first = first;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("0-0");
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 3) {
            return false;
        }
        int x, y, z;
        try {
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
            z = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage("invalid number format");
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
