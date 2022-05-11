package com.example.robotricochet.entities.game;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import lombok.Getter;
import lombok.Setter;

import java.awt.Graphics2D;

public class MoveDone extends Entity {

    @Getter
    private final Robot robot;

    @Getter
    @Setter
    private Vector2 boardPosition;

    @Getter
    private final Vector2 boardDestination;

    private final static float ANIMATION_DURATION = 250;  // Duration in ms
    private float progression = 0;

    private Board board;

    public MoveDone(Vector2 pos, Vector2 destination, Robot robot) {
        this.boardPosition = pos;
        this.boardDestination = destination;
        this.robot = robot;
    }

    public MoveDone(Move move) {
        this.boardPosition = move.getBoardPosition();
        this.boardDestination = move.getBoardDestination();
        this.robot = move.getRobot();
    }

    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
    }

    @Override
    public void update(float delta) {
        if (isMoving()) {
            progression += (delta * (bounds.getSize().max() / ANIMATION_DURATION)) / bounds.getSize().max();
            progression = progression > 1 ? 1 : progression;
            setDirty(true);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getRobot().getColor().getTransparentColor());
        int cells = (int) ((bounds.getSize().max() / board.cellSize) * progression);
        for (int i = 0; i < cells; i++) {
            if (bounds.getSize().x == board.cellSize) {
                g.fillRect((int) (bounds.getPosition().x + 0.5 * board.cellSize - 2.5), (int) (bounds.getPosition().y + i * board.cellSize + 0.5 * board.cellSize - 10), 5, 20);
            } else {
                g.fillRect((int) (bounds.getPosition().x + i * board.cellSize + 0.5 * board.cellSize - 10), (int) (bounds.getPosition().y + 0.5 * board.cellSize - 2.5), 20, 5);
            }
        }
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

    private boolean isMoving() {
        return progression != 1;
    }
}
