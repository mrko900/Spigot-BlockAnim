package com.github.mrko900.blockanim;

import org.bukkit.command.CommandSender;

import java.util.List;

public class AnimListCommand implements AnimCommand {
    private AnimManager animManager;
    private MessageManager messageManager;

    public AnimListCommand(AnimManager animManager, MessageManager messageManager) {
        this.animManager = animManager;
        this.messageManager = messageManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        List<String> anims = animManager.listAnimations();
        StringBuilder msg = new StringBuilder();
        msg.append(messageManager.get("anim.list0"));
        msg.append(anims.size());
        msg.append(messageManager.get("anim.list1"));
        for (int i = 0; i < anims.size() - 1; ++i) {
            msg.append(anims.get(i));
            msg.append(messageManager.get("anim.list_delim"));
        }
        if (!anims.isEmpty()) {
            msg.append(anims.getLast());
        }
        sender.sendMessage(msg.toString());
        return true;
    }
}
