package com.example.robotricochet.entities.game;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.CardType;
import com.example.robotricochet.components.RobotColor;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PickedCard extends Entity {

    private Board board;
    private final CardType cardType;
    private final RobotColor color;

    public PickedCard(CardType cardType, RobotColor color) {
        this.cardType = cardType;
        this.color = color;
    }

    @Override
    public void init() {
        board = entitySystem.find(Board.class).orElseThrow();
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void draw(Graphics2D g) {
//        g.setPaint(new TexturePaint(getCard(board.cellSize), new Rectangle2D.Float(0, 0, board.cellSize, board.cellSize)));
//        g.fillRect(bounds.getPosition().x, bounds.getPosition().y, bounds.getSize().x, bounds.getSize().y);
        g.drawImage(getCard(bounds.getSize().x), bounds.getPosition().x, bounds.getPosition().y, null);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition().translate(board.getSize().scale(0.5f).translate(-(board.cellSize * 2) / 2)),
                new Vector2(board.cellSize).scale(2)));
    }

    public BufferedImage getCard(int s) {
        return resourceSystem.getImageAsset("tiles/" + color.toString() + "-" + cardType.toString() + ".png", s, s);
    }
}