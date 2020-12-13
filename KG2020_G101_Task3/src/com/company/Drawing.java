package com.company;

import java.awt.*;

public class Drawing {
    public static void DrawCandle(LineDrawer ld, ScreenConverter sc, int del, int width, double[] arrPoints) {
        //определение типа свечи
        if (arrPoints[0] < arrPoints[3]) {
            ld.setColor(Color.GREEN);
        } else {
            ld.setColor(Color.RED);
        }
        //расположение тени по центру свечи
        ScreenPoint p1 = new ScreenPoint(del + width / 2, sc.r2s(new RealPoint(del, arrPoints[1])).getY());
        ScreenPoint p2 = new ScreenPoint(del + width / 2, sc.r2s(new RealPoint(del, arrPoints[2])).getY());
        ld.drawLine(p1, p2);
        // заполнение свечи
        for (int i = 0; i <= width; i++) {
            ScreenPoint p3 = new ScreenPoint(del + i, sc.r2s(new RealPoint(del, arrPoints[0])).getY());
            ScreenPoint p4 = new ScreenPoint(del + i, sc.r2s(new RealPoint(del, arrPoints[3])).getY());
            ld.drawLine(p3, p4);
        }
    }

    public static void DrawGraphic(LineDrawer ld, ScreenConverter sc, Candles graphic, int x, int width) {
        //расстояние между свечами
        int interval = sc.r2s(new RealPoint(x, 0)).getX();
        for (Candles c : graphic.getCandles()) {
            DrawCandle(ld, sc, interval, width, c.getPoints());
            interval += 5 * width;
        }
    }
}
