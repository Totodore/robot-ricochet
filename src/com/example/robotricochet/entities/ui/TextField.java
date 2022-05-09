package com.example.robotricochet.entities.ui;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.entities.game.Board;

import javax.swing.*;
import java.awt.*;

public class TextField extends Entity {
    private Board board;
    private JTextField text = new JTextField("Test");

    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
        text.setBounds(500,500,100,100);
        text.setColumns(20);
        text.setEditable(true);


    }

    /**
     * @param delta le nombre de ms écoulé entre deux appels de update
     */
    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        text.setBounds(500,500,100,100);
        text.setColumns(20);
        text.paint(g);
        //g.drawImage(getCard(bounds.getSize().x), bounds.getPosition().x, bounds.getPosition().y, null)




    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition().x + board.getSize().x + 100,
                screenSize.y / 2,
                0, 0
        ));
    }
}

