package com.example.robotricochet.entities.ui;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.entities.game.Board;

import java.awt.Color;
import java.awt.Graphics2D;

public class MoveCounter extends Entity {

    private Board board;
    private int counter = 0;

    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
    }

    public void tick() {
        counter++;
        setDirty(true);
    }

    public void reset() {
        counter = 0;
        setDirty(true);
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
        g.drawString("Coups effectués : " + counter, bounds.getPosition().x + 25, bounds.getPosition().y + 25);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition().x + board.getSize().x + (screenSize.x - board.getPosition().x - board.getSize().x) / 2 - 125,
                screenSize.y / 2 + 400,
                250, 100
        ));
    }

}
