package com.example.robotricochet.entities.ui;

import com.example.robotricochet.windows.Window;
import com.example.robotricochet.entities.Entity;

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FpsCounter extends Entity implements ActionListener, Runnable {
    private final int REFRESH_TIME = 1000;
    private final Timer resetTimer = new Timer(REFRESH_TIME, this);
    private int current, last;

    private FpsCounter(Window window) {
        super(window);
    }

    public static FpsCounter create(Window window) {
        FpsCounter counter = new FpsCounter(window);
        new Thread(counter).start();
        return counter;
    }

    @Override
    public void run() {
        start();
    }

    @Override
    public void init() {
        setPosition(10, 30);
    }

    @Override
    public synchronized void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawString("FPS: " + Math.max(0, getFps()), getX(), getY());
    }

    public synchronized void start() {
        resetTimer.start();
        current = 0;
        last--;
    }

    public synchronized void stop() {
        resetTimer.stop();
        current--;
    }

    public synchronized void tick() {
        current++;
    }

    @Override
    public synchronized void actionPerformed(final ActionEvent e) {
        last = current;
        current = 0;
        setDirty(true);
    }

    private synchronized int getFps() {
        return last * 1000 / REFRESH_TIME;
    }
}