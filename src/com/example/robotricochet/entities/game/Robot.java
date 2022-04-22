package com.example.robotricochet.entities.game;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.RobotColor;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import lombok.Getter;
import lombok.Setter;

import java.awt.Graphics2D;
import java.awt.Image;

public class Robot extends Entity implements BoardObject {

    private final RobotColor color;

    @Getter
    @Setter
    private Vector2 boardDestination;

    @Getter
    @Setter
    private Vector2 boardPosition;

    private Board board;

    public Robot(RobotColor color, Vector2 position) {
        this.color = color;
        this.boardPosition = this.boardDestination = position;
    }

    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
        setBoardDestination(new Vector2(0, 0));
    }

    @Override
    public void update(double delta) {
        if (isMoving()) {
            // Direction = (dest - origin) normalized
            Vector2 direction = getBoardDestination().translate(getBoardPosition().reverse()).normalize();
            // Translate from current position at 10px / ms
            Vector2 destination = board.getPosition().translate(getBoardDestination().scale(board.cellSize));
            bounds = new Bounds(
                    bounds.position.translate(direction.scale((float) (Math.max(1, delta * 0.1)))).clamp(destination, direction),
                    bounds.getSize());
            // If the robot reach the destination
            if (bounds.position == board.getPosition().translate(boardDestination.scale(board.cellSize)))
                boardPosition = boardDestination;
            setDirty(true);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(getTile(board.cellSize), getPosition().x, getPosition().y, null);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition().translate(boardPosition.scale(board.cellSize)),
                board.cellSize, board.cellSize
        ));
    }

    public boolean isMoving() {
        return boardDestination != boardPosition;
    }


    private Image getTile(int s) {
        return resourceSystem.getImageAsset("robots/" + color.toString() + "-robot.png", s, s, 0);
    }
}
