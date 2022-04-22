package com.example.robotricochet.entities.ui;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.entities.game.Board;

import java.awt.*;

public class Timer extends Entity {

    private Board board;
    private int timer = 0;

    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
    }

    /**
     * @param delta le nombre de ms écoulé entre deux appels de update
     */
    @Override
    public void update(float delta) {
        if (timer < 120000) {
            timer += Math.min(delta, 120000);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        int min = timer / 60000;
        int sec = (timer % 60000) / 1000;
        int millis = (timer % 60000) % 1000;
        //g.drawImage(getCard(bounds.getSize().x), bounds.getPosition().x, bounds.getPosition().y, null)
        g.drawString(pad(min) + ":" + pad(sec) + ":" + pad(millis), bounds.getPosition().x, bounds.getPosition().y);
        if (timer>120000) {
            g.drawString("Time's Up", 10, 10);
        }


    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition().x + board.getSize().x + 100,
                screenSize.y / 2,
                0, 0
        ));
    }

    private String pad(int val) {
        return String.format("%1$2s", val).replace(' ', '0');


    }
}

