package com.github.mrko900.blockanim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class SaveAnimCommand implements CommandExecutor {
    private AnimBuilderListener animBuilderListener;
    private String pluginFolder;

    public SaveAnimCommand(AnimBuilderListener listener, String pluginFolder) {
        this.animBuilderListener = listener;
        this.pluginFolder = pluginFolder;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("you are not a player");
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 1) {
            return false;
        }
        AnimBuilder builder = animBuilderListener.getAnimBuilder(player);
        if (builder == null) {
            sender.sendMessage("...");
            return true;
        }
        builder.setName(args[0]);
        animBuilderListener.removePlayer(player);
        try {
            builder.saveAnim(pluginFolder);
        } catch (IOException e) {
            sender.sendMessage("io exception");
            return true;
        }
        player.sendMessage("anim has been saved.");
        return true;
    }
}
