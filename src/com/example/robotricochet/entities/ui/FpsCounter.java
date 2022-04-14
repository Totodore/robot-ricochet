package com.example.robotricochet.entities.ui;

import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;

import java.awt.Color;
import java.awt.Graphics2D;

public class FpsCounter extends Entity {
    private float timer = 0;
    private int frameCount = 0;

    public int getFps() {
        if (timer == 0)
            return 0;
        return (int) (frameCount / timer);
    }

    @Override
    public void update(float delta) {
        if (timer >= 1000) {
            timer = 0;
            frameCount = 0;
        }
        timer += delta;
        frameCount++;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawString("FPS: " + getFps(), 10, 15);
    }

    @Override
    public void onResize(Vector2 screenSize) {

    }
}