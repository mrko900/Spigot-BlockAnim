package com.github.mrko900.blockanim;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimBuilder {
    private String name;
    private List<AnimPhase> phases = new ArrayList<>();
    private World world;

    public AnimBuilder(World world) {
        this.world = world;
    }

    public AnimPhase getCurrentPhase() {
        return phases.getLast();
    }

    public void newPhase() {
        phases.add(new AnimPhase(world));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void saveAnim(String folder) throws IOException {
        YamlConfiguration yml = new YamlConfiguration();
        int i = 0;
        for (AnimPhase phase : phases) {
            if (phase.getState() != AnimPhase.State.COMPLETE) {
                continue;
            }
            String s = Integer.toString(i);
            yml.createSection(s);
            yml.set(s + ".duration", phase.getDuration());
            List<String> blocks = new ArrayList<>();
            for (int x = phase.getX0(); x <= phase.getX1(); ++x) {
                for (int y = phase.getY0(); y <= phase.getY1(); ++y) {
                    for (int z = phase.getZ0(); z <= phase.getZ1(); ++z) {
                        blocks.add(phase.getBlockData(x - phase.getX0(), y - phase.getY0(), z - phase.getZ0()));
                    }
                }
            }
            yml.set(s + ".blocks", blocks);
            ++i;
        }
        yml.save(folder + "\\" + getName() + ".yml");
    }
}
