package com.github.mrko900.blockanim;

import org.bukkit.World;

public class AnimPhase {
    private int duration;
    private World world;
    private String[][][] blockData;

    public AnimPhase(World world) {
        this.world = world;
    }

    public void saveBlocks(int x0, int y0, int z0, int x1, int y1, int z1) {
        blockData = new String[x1 - x0 + 1][y1 - y0 + 1][z1 - z0 + 1];
        for (int x = x0; x <= x1; ++x) {
            for (int y = y0; y <= y1; ++y) {
                for (int z = z0; z <= z1; ++z) {
                    blockData[x - x0][y - y0][z - z0] = world.getBlockAt(x, y, z).getBlockData().getAsString();
                }
            }
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getBlockData(int x, int y, int z) {
        return blockData[x][y][z];
    }
}
