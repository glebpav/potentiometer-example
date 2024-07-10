package com.mygdx.game.utils;

import java.util.ArrayList;

public class ChartValues implements Comparable<ChartValues> {
    public int duration;
    public double value;

    public ChartValues(int duration, double value) {
        this.duration = duration;
        this.value = value;
    }

    @Override
    public int compareTo(ChartValues o) {
        return (int) ((this.value - o.value) * 1000);
    }

    public static float getValue(ArrayList<ChartValues> valuesList, int time) {
        int timeHolder = 0;
        for (ChartValues chartValues : valuesList) {
            if (timeHolder >= time) return (float) chartValues.value;
            timeHolder += chartValues.duration;
        }
        if (timeHolder > time) return (float) valuesList.get(valuesList.size() - 1).value;
        throw new IllegalArgumentException();
    }

    public static int getSumTime(ArrayList<ChartValues> valuesList) {
        return valuesList.stream().mapToInt(it -> it.duration).sum();
    }
}