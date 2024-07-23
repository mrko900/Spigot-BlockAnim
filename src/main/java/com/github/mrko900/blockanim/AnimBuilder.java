package com.github.mrko900.blockanim;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimBuilder {
    public enum State {
        EMPTY, P1_SET, P2_SET
    }

    private State state = State.EMPTY;
    private String name;
    private List<AnimPhase> phases = new ArrayList<>();
    private World world;
    private int x0, y0, z0, x1, y1, z1;

    public AnimBuilder(World world) {
        this.world = world;
    }

    public AnimPhase getCurrentPhase() {
        phases.add(new AnimPhase(world));
        return phases.getLast();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPoint(int x, int y, int z) {
        if (state == State.P2_SET) {
            throw new IllegalStateException("bounds already set");
        } else if (state == State.EMPTY) {
            x0 = x;
            y0 = y;
            z0 = z;
            state = State.P1_SET;
        } else if (state == State.P1_SET) {
            x1 = x;
            y1 = y;
            z1 = z;
            if (x0 > x1) {
                int tmp = x0;
                x0 = x1;
                x1 = tmp;
            }
            if (y0 > y1) {
                int tmp = y0;
                y0 = y1;
                y1 = tmp;
            }
            if (z0 > z1) {
                int tmp = z0;
                z0 = z1;
                z1 = tmp;
            }
            state = State.P2_SET;
        }
    }

    public void saveAnim(String folder) throws IOException {
        YamlConfiguration yml = new YamlConfiguration();
        yml.createSection("phases");
        yml.set("x0", x0);
        yml.set("x1", x1);
        yml.set("y0", y0);
        yml.set("y1", y1);
        yml.set("z0", z0);
        yml.set("z1", z1);
        yml.set("world", world.getUID().toString());
        int i = 0;
        for (AnimPhase phase : phases) {
            String s = "phases." + i;
            yml.createSection(s);
            yml.set(s + ".duration", phase.getDuration());
            List<String> blocks = new ArrayList<>();
            for (int x = x0; x <= x1; ++x) {
                for (int y = y0; y <= y1; ++y) {
                    for (int z = z0; z <= z1; ++z) {
                        blocks.add(phase.getBlockData(x - x0, y - y0, z - z0));
                    }
                }
            }
            yml.set(s + ".blocks", blocks);
            ++i;
        }
        yml.save(folder + "\\" + getName() + ".yml");
    }

    public State getState() {
        return state;
    }

    public int getX0() {
        return x0;
    }

    public int getX1() {
        return x1;
    }

    public int getY0() {
        return y0;
    }

    public int getY1() {
        return y1;
    }

    public int getZ0() {
        return z0;
    }

    public int getZ1() {
        return z1;
    }
}
