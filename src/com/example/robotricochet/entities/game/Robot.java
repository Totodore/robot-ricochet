package com.example.robotricochet.entities.game;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.CursorType;
import com.example.robotricochet.components.RobotColor;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.windows.GameWindow;
import lombok.Getter;
import lombok.Setter;

import java.awt.Graphics2D;
import java.awt.Image;

public class Robot extends Entity implements BoardObject {


    @Getter
    private final RobotColor color;

    @Getter
    @Setter
    private Vector2 boardDestination;

    @Getter
    private Vector2 boardPosition;

    private Board board;

    private final static float ANIMATION_DURATION = 250;  // Duration in ms

    public Robot(RobotColor color, Vector2 position) {
        this.color = color;
        this.boardPosition = this.boardDestination = position;
    }

    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
    }

    @Override
    public void update(float delta) {
        if (isMoving()) {
            setDirty(true);
            // Direction = (dest - origin) normalized
            Vector2 direction = getBoardDestination().translate(getBoardPosition().reverse()).normalize();
            // Origin and destinations are translated to the board origin
            Vector2 origin = board.getPosition().translate(getBoardPosition().scale(board.cellSize));
            Vector2 destination = board.getPosition().translate(getBoardDestination().scale(board.cellSize));
            // Speed = distance / duration
            float speed = destination.translate(origin.reverse()).length() / ANIMATION_DURATION;
            // Position = currentPos + direction * speed * delta (clamped to the destination)
            Vector2 position = bounds.position.translate(direction.scale(Math.max(1, delta * speed))).clamp(destination, direction);
            bounds = new Bounds(position, bounds.getSize());
            // If the robot reach the destination
            if (position.equals(destination))
                boardPosition = boardDestination;
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
        resourceSystem.removeSizedImageAsset("robots/" + color.toString() + "-robot.png");
    }

    @Override
    public boolean onClick(Vector2 position) {
        logger.info("Click on robot");
        ((GameWindow) window).showMoves(this);
        return false;
    }

    @Override
    public void onEnter(Vector2 position) {
        window.setCursor(CursorType.POINTER);
    }

    public void onLeave(Vector2 position) {
        window.setCursor(CursorType.BASE);
    }

    public boolean isMoving() {
        return !boardDestination.equals(boardPosition);
    }

    public void setBoardPosition(Vector2 boardPosition) {
        this.boardPosition = boardPosition;
        this.boardDestination = boardPosition;
        onResize(null);
        setDirty(true);
    }

    private Image getTile(int s) {
        return resourceSystem.getImageAsset("robots/" + color.toString() + "-robot.png", s, s, 0);
    }

}
