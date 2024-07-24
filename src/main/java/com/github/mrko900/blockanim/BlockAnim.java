package com.github.mrko900.blockanim;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.joml.Vector3i;

import java.io.File;
import java.util.*;

public class BlockAnim {
    private int x0, y0, z0, x1, y1, z1;
    private List<Phase> phases = new ArrayList<>();
    private World world;
    private Plugin plugin;

    public class Phase {
        int duration;
        Map<Vector3i, String> blockData = new HashMap<>();
    }

    private int currentPhase;

    private class Task extends BukkitRunnable {
        private int i;

        Task(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            new Task((i + 1) % phases.size()).runTaskLater(plugin, phases.get(i).duration);
            setPhase(i);
        }
    }

    public void start() {
        new Task(0).run();
    }

    public void setPhase(int i) {
        for (Map.Entry<Vector3i, String> entry : phases.get(i).blockData.entrySet()) {
            int x = entry.getKey().x;
            int y = entry.getKey().y;
            int z = entry.getKey().z;
            world.getBlockAt(x, y, z).setBlockData(Bukkit.createBlockData(entry.getValue()));
        }
    }

    public static BlockAnim fromFile(File file, Plugin plugin) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        BlockAnim anim = new BlockAnim();
        anim.world = plugin.getServer().getWorld(UUID.fromString(config.getString("world")));
        anim.x0 = config.getInt("x0");
        anim.x1 = config.getInt("x1");
        anim.y0 = config.getInt("y0");
        anim.y1 = config.getInt("y1");
        anim.z0 = config.getInt("z0");
        anim.z1 = config.getInt("z1");
        anim.plugin = plugin;
//        int w = anim.x1 - anim.x0 + 1;
//        int h = anim.y1 - anim.y0 + 1;
//        int d = anim.z1 - anim.z0 + 1;
        ConfigurationSection phases = config.getConfigurationSection("phases");
        for (String key : phases.getKeys(false)) {
            Phase phase = anim.new Phase();
            int i = 0;
            ConfigurationSection phaseSection = phases.getConfigurationSection(key);
            phase.duration = phaseSection.getInt("duration");
            ConfigurationSection blocksSection = phaseSection.getConfigurationSection("blocks");
            for (String xyz : blocksSection.getKeys(false)) {
                String[] arr = xyz.split(",");
                int x = Integer.parseInt(arr[0]);
                int y = Integer.parseInt(arr[1]);
                int z = Integer.parseInt(arr[2]);
//                int x = i / (h * d);
//                int y = i % (h * d) / d;
//                int z = i % (h * d) % d;
                String blockData = blocksSection.getString(xyz);
                phase.blockData.put(new Vector3i(x, y, z), blockData);
                ++i;
            }
            anim.phases.add(phase);
        }
        return anim;
    }
}
