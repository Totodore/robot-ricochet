package com.example.robotricochet.entities.ui;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.CursorType;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.entities.game.Board;
import com.example.robotricochet.windows.GameWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Timer extends Entity {

    private Board board;
    private long timer = 120_000;
    private boolean started = false;

    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
    }

    public void start() {
        started = true;
        setDirty(true);
    }

    public void reset() {
        timer = 120_000;
        started = false;
        setDirty(true);
    }

    /**
     * @param delta le nombre de ms écoulé entre deux appels de update
     */
    @Override
    public void update(float delta) {
        if (!started)
            return;
        if (timer > 0) {
            timer -= delta;
            setDirty(true);
        }
        if (timer < 0) {
            timer = 0;
            setDirty(true);
            started = false;
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
                bounds.getSize().x, bounds.getSize().y, 5, 5);
        g.setColor(Color.white);
        if (started) {
            String time = pad(min) + ":" + pad(sec) + ":" + pad(millis);
            g.drawString(time, bounds.getPosition().x + 25, bounds.getPosition().y + 25);
        } else if (timer == 0) {
            g.drawString("Time's Up !", bounds.getPosition().x + 20, bounds.getPosition().y + 25);
        } else {
            g.drawString("Start", bounds.getPosition().x + 70, bounds.getPosition().y + 25);
        }
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition().x + board.getSize().x + (screenSize.x - board.getPosition().x - board.getSize().x) / 2 - 125,
                screenSize.y / 2,
                250, 100
        ));
    }

    @Override
    public boolean onClick(Vector2 position) {
        if (!started) {
            ((GameWindow) window).drawCard();
            start();
        } else {
            timer = 120_000;
            setDirty(true);
            started = false;
        }
        return false;
    }

    @Override
    public void onEnter(Vector2 position) {
        window.setCursor(CursorType.POINTER);
    }

    @Override
    public void onLeave(Vector2 position) {
        window.setCursor(CursorType.BASE);
    }

    private String pad(int val) {
        return String.format("%1$2s", val).replace(' ', '0');
    }
}

