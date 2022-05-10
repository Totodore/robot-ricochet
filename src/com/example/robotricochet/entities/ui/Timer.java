package com.example.robotricochet.entities.ui;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.entities.game.Board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Timer extends Entity {

    private Board board;
    private long timer = 120_000;

    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
    }

    /**
     * @param delta le nombre de ms écoulé entre deux appels de update
     */
    @Override
    public void update(float delta) {
        if (timer > 0) {
            timer -= delta;
            setDirty(true);
        }
        if (timer < 0) {
            timer = 0;
            setDirty(true);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        // Convert ns to min sec and millis
        int min = (int) (timer / 60000);
        int sec = (int) (timer / 1000) % 60;
        int millis = (int) (timer % 1000);
        g.setColor(new Color(0xD14C4C));
        g.setFont(new Font(null, Font.PLAIN, 40));
        g.fillRoundRect(
                bounds.getPosition().x - 10,
                bounds.getPosition().y - g.getFontMetrics().getHeight() / 2 - 10,
                250, 100, 5, 5);
        g.setColor(Color.white);
        g.drawString(pad(min) + ":" + pad(sec) + ":" + pad(millis),
                bounds.getPosition().x + 25, bounds.getPosition().y + 25);
        if (timer == 0) {
            g.drawString("Time's Up", 30, 30);
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

    @Override
    public boolean onClick(Vector2 position) {

        return super.onClick(position);
    }

    private String pad(int val) {
        return String.format("%1$2s", val).replace(' ', '0');
    }
}

