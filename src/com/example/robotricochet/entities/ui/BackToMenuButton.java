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
import java.util.function.Consumer;

public class BackToMenuButton extends Entity {

    private Board board;
    private final Consumer<Void> backToMenu;

    public BackToMenuButton(Consumer<Void> backToMenu) {
        this.backToMenu = backToMenu;
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
        g.setFont(new Font(null, Font.PLAIN, 40));
        g.fillRoundRect(
                bounds.getPosition().x - 10,
                bounds.getPosition().y - g.getFontMetrics().getHeight() / 2 - 10,
                bounds.getSize().x, bounds.getSize().y, 5, 5);
        g.setColor(Color.white);
        g.drawString("Menu", bounds.getPosition().x + 60, bounds.getPosition().y + 25);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition().x + board.getSize().x + (screenSize.x - board.getPosition().x - board.getSize().x) / 2 - 125,
                screenSize.y / 2 - 200,
                250, 100
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
        backToMenu.accept(null);
        return false;
    }
}
