package com.example.robotricochet.entities.game;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Direction;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import lombok.Getter;
import lombok.Setter;

import java.awt.Graphics2D;
import java.awt.Image;

public class Wall extends Entity implements BoardObject {

    @Setter
    @Getter
    private Direction direction;

    @Getter
    @Setter
    private Vector2 boardPosition;

    private Board board;

    public Wall(Vector2 position, Direction direction) {
        this.boardPosition = position;
        this.direction = direction;
    }

    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(getTile(board.cellSize), getPosition().x, getPosition().y, null);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition()
                        .translate(boardPosition.scale(board.cellSize))
                        .translate(direction == Direction.Vertical ? board.cellSize - 2 : 0, direction == Direction.Horizontal ? board.cellSize - 2 : 0),                          // Position relative to the board
                direction == Direction.Vertical ? board.cellSize : (int) (board.cellSize * 0.1),      // Width
                direction == Direction.Horizontal ? board.cellSize : (int) (board.cellSize * 0.1)));     // Height
        resourceSystem.removeSizedImageAsset("tiles/wall.png");
    }

    private Image getTile(int s) {
        if (direction == Direction.Horizontal)
            return resourceSystem.getImageAsset("tiles/wall.png", s, s * 0.1f, 0);
        else
            return resourceSystem.getImageAsset("tiles/wall.png", s, s, 90);
    }
}
