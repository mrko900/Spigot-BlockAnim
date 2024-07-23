package com.github.mrko900.blockanim;

import org.bukkit.World;
import org.bukkit.block.Block;

public class AnimPhase {
    public enum State {
        EMPTY, P1_SET, COMPLETE
    }

    private State state = State.EMPTY;
    private int x0, y0, z0, x1, y1, z1;
    private int duration;
    private World world;
    private String[][][] blockData;

    public AnimPhase(World world) {
        this.world = world;
    }

    private void saveBlocks() {
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
        blockData = new String[x1 - x0 + 1][y1 - y0 + 1][z1 - z0 + 1];
        for (int x = x0; x <= x1; ++x) {
            for (int y = y0; y <= y1; ++y) {
                for (int z = z0; z <= z1; ++z) {
                    blockData[x - x0][y - y0][z - z0] = world.getBlockAt(x, y, z).getBlockData().getAsString();
                }
            }
        }
    }

    public void addPoint(int x, int y, int z) {
        if (state == State.COMPLETE) {
            throw new IllegalStateException("already complete");
        } else if (state == State.EMPTY) {
            x0 = x;
            y0 = y;
            z0 = z;
            state = State.P1_SET;
        } else if (state == State.P1_SET) {
            x1 = x;
            y1 = y;
            z1 = z;
            state = State.COMPLETE;
            saveBlocks();
        }
    }

    public State getState() {
        return state;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public String getBlockData(int x, int y, int z) {
        return blockData[x][y][z];
    }
}
