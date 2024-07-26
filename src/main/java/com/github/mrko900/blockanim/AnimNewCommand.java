package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnimNewCommand implements AnimCommand {
    private AnimBuilderManager listener;
    private MessageManager messageManager;

    public AnimNewCommand(AnimBuilderManager listener, MessageManager messageManager) {
        this.listener = listener;
        this.messageManager = messageManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        player.sendMessage(messageManager.getLines("anim.new"));
        listener.addPlayer(player);
        return true;
    }
}
