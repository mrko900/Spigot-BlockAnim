package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;

public class AnimHelpCommand implements AnimCommand {
    private MessageManager messageManager;

    public AnimHelpCommand(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage(messageManager.get("anim.help"));
        return true;
    }
}
