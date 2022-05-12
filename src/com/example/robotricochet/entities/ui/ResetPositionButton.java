package com.example.robotricochet.entities.ui;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.CursorType;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.entities.game.Board;
import com.example.robotricochet.entities.game.Robot;
import com.example.robotricochet.windows.GameWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashMap;

public class ResetPositionButton extends Entity {

    private Board board;
    private final HashMap<Robot, Vector2> originalPositions = new HashMap<>();

    public boolean hasRobot(Robot robot) {
        return originalPositions.containsKey(robot);
    }
    public void addRobotPosition(Robot robot, Vector2 originalPosition) {
        this.originalPositions.put(robot, originalPosition);
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
        g.setColor(new Color(0xD14C4C));
        g.setFont(new Font(null, Font.PLAIN, 30));
        g.fillRoundRect(
                bounds.getPosition().x - 10,
                bounds.getPosition().y - g.getFontMetrics().getHeight() / 2 - 10,
                bounds.getSize().x, bounds.getSize().y, 5, 5);
        g.setColor(Color.white);
        g.drawString("Revenir à la position", bounds.getPosition().x + 30, bounds.getPosition().y + 10);
        g.drawString("de départ", bounds.getPosition().x + 100, bounds.getPosition().y + 55);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition().x + board.getSize().x + (screenSize.x - board.getPosition().x - board.getSize().x) / 2 - 165,
                screenSize.y / 2 + 200,
                350, 100
        ));
    }


    @Override
    public void onEnter(Vector2 position) {
        window.setCursor(CursorType.POINTER);
    }

    @Override
    public void onLeave(Vector2 position) {
        window.setCursor(CursorType.BASE);
    }

    @Override
    public boolean onClick(Vector2 position) {
        ((GameWindow) window).resetRobotPosition(originalPositions);
        window.setCursor(CursorType.BASE);
        return false;
    }
}
