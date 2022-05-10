package com.example.robotricochet.entities.ui;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.entities.game.Board;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.TextField;

public class TextInput extends Entity {
    private Board board;
    private final TextField text = new TextField("Test");

    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
        text.setColumns(20);
        text.setEditable(true);
        window.add(text);
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
        text.setColumns(20);
        text.paintAll(g);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition().x + board.getSize().x + 100,
                screenSize.y / 2 + 75,
                200, 50
        ));
    }

    @Override
    public void setBounds(Bounds bounds) {
        super.setBounds(bounds);
        text.setBounds(bounds.getPosition().x, bounds.getPosition().y, bounds.getSize().x, bounds.getSize().y);
    }
}

