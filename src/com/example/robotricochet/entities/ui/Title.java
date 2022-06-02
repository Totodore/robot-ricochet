package com.example.robotricochet.entities.ui;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Title extends Entity {


    public Title() {
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(66, 66, 241));
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Robot Ricochet", bounds.getPosition().x, bounds.getPosition().y);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                new Vector2(
                        screenSize.x / 2 - 200,
                        screenSize.y / 2 - 50
                ),
                new Vector2(
                        300,
                        50
                )
        ));
    }
}
