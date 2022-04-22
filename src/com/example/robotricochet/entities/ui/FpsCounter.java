package com.example.robotricochet.entities.ui;

import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;

import java.awt.Color;
import java.awt.Graphics2D;

public class FpsCounter extends Entity {
    private int frameCount = 0;
    private double dt = 0.0;
    private double fps = 0.0;

    @Override
    public void init() {

    }

    @Override
    public void update(double delta) {
        frameCount++;
        dt += delta / 1000;
        float updateRate = 4.0f;
        if (dt > 1.0 / updateRate) {
            fps = frameCount / dt;
            frameCount = 0;
            dt -= 1.0 / updateRate;
        }
        setDirty(true);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawString("FPS: " + fps, 10, 15);
    }

    @Override
    public void onResize(Vector2 screenSize) {

    }
}