package com.example.robotricochet.entities.game;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.CursorType;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.windows.GameWindow;
import lombok.Getter;
import lombok.Setter;

import java.awt.Graphics2D;

public class Move extends Entity implements BoardObject {
    @Getter
    private final Robot robot;

    @Getter
    @Setter
    private Vector2 boardPosition;

    @Getter
    private final Vector2 boardDestination;

    private Board board;

    public Move(Vector2 pos, Vector2 destination, Robot robot) {
        this.boardPosition = pos;
        this.boardDestination = destination;
        this.robot = robot;
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
        g.setColor(getRobot().getColor().getTransparentColor());

        g.fillRect(bounds.getPosition().x, bounds.getPosition().y, bounds.getSize().x, bounds.getSize().y);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        Vector2 realOrigin = board.getPosition().translate(Vector2.min(boardPosition, boardDestination).scale(board.cellSize));
        Vector2 realDestination = board.getPosition().translate(Vector2.max(boardPosition, boardDestination).scale(board.cellSize));
        Vector2 size = realDestination.translate(realOrigin.reverse());
        if (size.y == 0) {
            setBounds(new Bounds(
                    realOrigin,
                    size.x, board.cellSize
            ));
        } else {
            setBounds(new Bounds(
                    realOrigin,
                    board.cellSize, size.y
            ));
        }
    }

    @Override
    public void onEnter(Vector2 position) {
        window.setCursor(CursorType.POINTER);
    }

    public void onLeave(Vector2 position) {
        window.setCursor(CursorType.BASE);
    }

    @Override
    public boolean onClick(Vector2 position) {
        Vector2 destination = getBoardDestination();
        if (destination.x - getBoardPosition().x > 0)
            destination.x -= 1;
        else if (destination.y - getBoardPosition().y > 0)
            destination.y -= 1;
        ((GameWindow) window).moveRobot(robot, destination, this);
        return false;
    }
}
