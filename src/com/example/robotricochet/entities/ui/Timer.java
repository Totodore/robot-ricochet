package com.example.robotricochet.entities.ui;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.entities.game.Board;

import javax.swing.*;
import java.awt.*;

public class Timer extends Entity {

    private Board board;
    private int timer = 1200;

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
        }
        if (timer < 0){
            timer=0;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        int min = timer / 60000;
        int sec = (timer % 60000) / 1000;
        int millis = (timer % 60000) % 1000;
        //g.drawImage(getCard(bounds.getSize().x), bounds.getPosition().x, bounds.getPosition().y, null)
        g.setColor(new Color(0xD14C4C));
        g.setFont(new Font(null, Font.PLAIN, 40));
        g.fillRoundRect(bounds.getPosition().x - 10, bounds.getPosition().y -g.getFontMetrics().getHeight() / 2 - 10, 300, 100, 5, 5);
        g.setColor(Color.white);
        g.drawString(pad(min) + ":" + pad(sec) + ":" + pad(millis), bounds.getPosition().x, bounds.getPosition().y);
        if (timer==0) {
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

