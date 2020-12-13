package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
    ScreenConverter sc = new ScreenConverter(-5, 140, 200, 200, 800, 600);

    private final Candles graphic;

    DrawPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        graphic = new Candles();
        graphic.addCandle(200);//добавление свечей
    }

    private void AxisY(LineDrawer ld) {
        int min = -400;
        ScreenPoint sP1 = new ScreenPoint(10, 0);
        ScreenPoint sP2 = new ScreenPoint(10, getHeight());
        ld.drawLine(sP1, sP2);
        //длина штрихов ОY и расстояние между ними
        for (int i = min; i < 30; i++) {
            if (i * 10 >= (min - 10)) {
                RealPoint rp3 = new RealPoint(-2, i * 10);
                RealPoint rp4 = new RealPoint(2, i * 10);
                ScreenPoint sP3 = new ScreenPoint(10 - getWidth() / 100, sc.r2s(rp3).getY());
                ScreenPoint sP4 = new ScreenPoint(10 + getWidth() / 100, sc.r2s(rp4).getY());
                ld.drawLine(sP3, sP4);
            }
        }
    }

    private void AxisX(LineDrawer ld, int widthCandles, int count) {
        RealPoint rp1 = new RealPoint(0, 0);
        RealPoint rp2 = new RealPoint(10 + widthCandles * count , 0);
        ScreenPoint sP1 = new ScreenPoint(0, sc.r2s(rp1).getY());
        ScreenPoint sP2 = new ScreenPoint(getWidth(), sc.r2s(rp2).getY());
        ld.drawLine(sP1, sP2);
        for (int i = 0; i < count; i++) {
            int x = sc.r2s(new RealPoint(0, 0)).getX();
            //длина штрихов ОХ и расстояние между ними
            ScreenPoint p1 = new ScreenPoint(x + 5 * widthCandles * i + widthCandles / 2, sc.r2s(new RealPoint(0, -2)).getY());
            ScreenPoint p2 = new ScreenPoint(x + 5 * widthCandles * i + widthCandles / 2, sc.r2s(new RealPoint(0, 2)).getY());
            ld.drawLine(p1, p2);
        }
    }

    private void drawDiagram(LineDrawer ld) {
        int count = graphic.getCandles().size();//кол-во свеч на графике
        int candleW = getWidth() / 200; //ширина свечи
        //оси
        AxisY(ld);
        AxisX(ld, candleW, count);
        Drawing.DrawGraphic(ld, sc, graphic, 0, candleW);
    }

    @Override
    public void paint(Graphics g) {
        sc.setsW(getWidth());
        sc.setsH(getHeight());
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics2D gr = bufferedImage.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.dispose();
        PixelDrawer pd = new BufferedImagePixelDrawer(bufferedImage);
        LineDrawer ld = new DDALineDrawer(pd);
        drawDiagram(ld);
        g.drawImage(bufferedImage, 0, 0, null);
    }

    private ScreenPoint oldPoint = null;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        oldPoint = new ScreenPoint(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        oldPoint = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            ScreenPoint p1 = sc.r2s(new RealPoint(0, 0));
            ScreenPoint p2 = sc.r2s(new RealPoint(0, 0));
            ScreenPoint currPoint = new ScreenPoint(e.getX(), e.getY());
            //ограничение сдвига по высоте
            if (!((p1.getY() <= this.getHeight() || currPoint.getY() < oldPoint.getY())
                    && (p2.getY() >= 0 || currPoint.getY() > oldPoint.getY()))) {
                currPoint = new ScreenPoint(currPoint.getX(), oldPoint.getY());
            }
            //ограничение сдвига влево
            if (!(p1.getX() <= getWidth() / 100 || currPoint.getX() < oldPoint.getX())) {
                currPoint = new ScreenPoint(oldPoint.getX(), currPoint.getY());
            }
            //движение по графику
            if (oldPoint != null) {
                ScreenPoint deltaScreen = new ScreenPoint(currPoint.getX() - oldPoint.getX(), currPoint.getY() - oldPoint.getY());
                RealPoint deltaReal = sc.s2r(deltaScreen);
                double vectorX = deltaReal.getX() - sc.getrX();
                double vectorY = deltaReal.getY() - sc.getrY();
                sc.setrX(sc.getrX() - vectorX);
                sc.setrY(sc.getrY() - vectorY);
                oldPoint = currPoint;
            }
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

