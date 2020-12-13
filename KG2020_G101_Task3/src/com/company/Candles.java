package com.company;

import java.util.ArrayList;
import java.util.List;

public class Candles {

    private final List<Candles> candles = new ArrayList<>();
    private final double[] Points = new double[4];

    public static double randomValue(double min, double max) {
        double del = max - min;
        return (Math.random() * del) + min;
    }


    public Candles() {

        double num = randomValue(-100, 100);
        //тело свечи
        Points[0] = num;//начало
        Points[3] = randomValue(num - 50, num + 50);//конец
        double max = Math.max(Points[0], Points[3]);
        double min = Math.min(Points[0], Points[3]);
        //тени
        Points[1] = randomValue(max, max + 20);//макс значение
        Points[2] = randomValue(min - 20, min);//мин значение
    }

    public void addCandle(int count) {
        for (int i = 0; i < count; i++) {
            candles.add(new Candles());
        }
    }

    public double[] getPoints() {
        return Points;
    }
    public List<Candles> getCandles() {
        return candles;
    }

}
