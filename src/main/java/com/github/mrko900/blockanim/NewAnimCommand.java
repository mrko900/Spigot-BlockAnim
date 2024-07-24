package com.github.mrko900.blockanim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NewAnimCommand implements AnimCommand {
    private AnimBuilderManager listener;

    public NewAnimCommand(AnimBuilderManager listener) {
        this.listener = listener;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        player.sendMessage("new anim");
        listener.addPlayer(player);
        return true;
    }
}
