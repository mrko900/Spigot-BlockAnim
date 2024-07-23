package com.github.mrko900.blockanim;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlockAnim {
    private int x0, y0, z0, x1, y1, z1;
    private List<Phase> phases = new ArrayList<>();
    private World world;

    public class Phase {
        int duration;
        String[][][] blockData = new String[x1 - x0 + 1][y1 - y0 + 1][z1 - z0 + 1];
    }

    public void start() {
//        for (Phase phase : phases) {
//
//        }
        setPhase(0);
    }

    public void setPhase(int i) {
        for (int x = x0; x <= x1; ++x) {
            for (int y = y0; y <= y1; ++y) {
                for (int z = z0; z <= z1; ++z) {
                    world.getBlockAt(x, y, z).setBlockData(Bukkit.createBlockData(
                            phases.get(i).blockData[x - x0][y - y0][z - z0]
                    ));
                }
            }
        }
    }

    public static BlockAnim fromFile(File file, Server server) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        BlockAnim anim = new BlockAnim();
        anim.world = server.getWorld(UUID.fromString(config.getString("world")));
        anim.x0 = config.getInt("x0");
        anim.x1 = config.getInt("x1");
        anim.y0 = config.getInt("y0");
        anim.y1 = config.getInt("y1");
        anim.z0 = config.getInt("z0");
        anim.z1 = config.getInt("z1");
        int w = anim.x1 - anim.x0 + 1;
        int h = anim.y1 - anim.y0 + 1;
        int d = anim.z1 - anim.z0 + 1;
        ConfigurationSection phases = config.getConfigurationSection("phases");
        for (String key : phases.getKeys(false)) {
            Phase phase = anim.new Phase();
            int i = 0;
            ConfigurationSection phaseSection = phases.getConfigurationSection(key);
            phase.duration = phaseSection.getInt("duration");
            for (Object o : phaseSection.getList("blocks")) {
                int x = i / (h * d);
                int y = i % (h * d) / d;
                int z = i % (h * d) % d;
                String blockData = (String) o;
                phase.blockData[x][y][z] = blockData;
                ++i;
            }
            anim.phases.add(phase);
        }
        return anim;
    }
}
