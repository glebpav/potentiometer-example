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

    public static double getValue(ArrayList<ChartValues> valuesList, int time) {
        int timeHolder = 0;
        for (ChartValues chartValues : valuesList) {
            if (timeHolder >= time) return chartValues.value;
            timeHolder += chartValues.duration;
        }
        return 0;
    }

    public static int getSumTime(ArrayList<ChartValues> valuesList) {
        return valuesList.stream().mapToInt(it -> it.duration).sum();
    }
}