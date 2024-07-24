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

    private boolean p1set, p2set;
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
        if (p1set && p2set) {
            throw new IllegalStateException("bounds already set");
        }
        if (!p1set) {
            setFirstPoint(x, y, z);
        } else {
            setSecondPoint(x, y, z);
        }
    }

    public void setFirstPoint(int x, int y, int z) {
        x0 = x;
        y0 = y;
        z0 = z;
        p1set = true;
        if (p2set) {
            validate();
        }
    }

    public void setSecondPoint(int x, int y, int z) {
        x1 = x;
        y1 = y;
        z1 = z;
        p2set = true;
        if (p1set) {
            validate();
        }
    }

    private void validate() {
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
    }

    public void saveAnim(String folder) throws IOException {
        YamlConfiguration yml = new YamlConfiguration();
        yml.set("x0", x0);
        yml.set("x1", x1);
        yml.set("y0", y0);
        yml.set("y1", y1);
        yml.set("z0", z0);
        yml.set("z1", z1);
        yml.set("world", world.getUID().toString());
        yml.createSection("phases");
        for (int i = 0; i < phases.size(); ++i) {
            AnimPhase phase = phases.get(i);
            AnimPhase prevPhase = phases.get((i - 1 + phases.size()) % phases.size());
            String s = "phases." + i;
            yml.createSection(s);
            yml.set(s + ".duration", phase.getDuration());
            yml.createSection(s + ".blocks");
            for (int x = x0; x <= x1; ++x) {
                for (int y = y0; y <= y1; ++y) {
                    for (int z = z0; z <= z1; ++z) {
                        if (!phase.getBlockData(x - x0, y - y0, z - z0)
                                .equals(prevPhase.getBlockData(x - x0, y - y0, z - z0))) {
                            yml.set(s + ".blocks." + x + "," + y + "," + z, phase.getBlockData(x - x0, y - y0, z - z0));
                        }
                    }
                }
            }
        }
        yml.save(folder + "\\" + getName() + ".yml");
    }

    public boolean isFirstPointSet() {
        return p1set;
    }

    public boolean isSecondPointSet() {
        return p2set;
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
