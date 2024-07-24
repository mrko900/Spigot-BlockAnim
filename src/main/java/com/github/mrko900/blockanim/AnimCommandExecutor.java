package com.github.mrko900.blockanim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Map;

public class AnimCommandExecutor implements CommandExecutor {
    private Map<String, AnimCommand> executors;

    public AnimCommandExecutor(Map<String, AnimCommand> executors) {
        this.executors = executors;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || !executors.containsKey(args[0])) {
            return false;
        }
        return executors.get(args[0]).execute(sender, Arrays.copyOfRange(args, 1, args.length));
    }
}
