package com.example.robotricochet.entities.game;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;

import java.awt.Color;
import java.awt.Graphics2D;

public class BoardCenter extends Entity implements BoardObject {

    private Board board;
    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(0x555555));
        g.fillRect(bounds.position.x, bounds.position.y, bounds.size.x, bounds.size.y);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition().translate(new Vector2(board.cellSize).scale(7)),
                new Vector2(board.cellSize).scale(2)
        ));
    }

    @Override
    public Vector2 getBoardPosition() {
        return new Vector2(7, 7);
    }

    @Override
    public void setBoardPosition(Vector2 position) {

    }
}
