package com.example.robotricochet.entities.game;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.CardType;
import com.example.robotricochet.components.RobotColor;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import lombok.Getter;
import lombok.Setter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Card extends Entity implements BoardObject {

    private Board board;
    @Getter
    private final CardType cardType;
    @Getter
    private final RobotColor color;

    @Getter
    @Setter
    private Vector2 boardPosition;

    public Card(CardType cardType, RobotColor color, Vector2 position) {
        this.cardType = cardType;
        this.color = color;
        this.boardPosition = position;
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
        g.drawImage(getCard(bounds.getSize().x), bounds.getPosition().x, bounds.getPosition().y, null);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                board.getPosition().translate(boardPosition.scale(board.cellSize)),
                new Vector2(board.cellSize)));
        resourceSystem.removeSizedImageAsset("tiles/" + color.toString() + "-" + cardType.toString() + ".png");

    }

    public BufferedImage getCard(int s) {
        return resourceSystem.getImageAsset("tiles/" + color.toString() + "-" + cardType.toString() + ".png", s, s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return cardType == card.cardType && color == card.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardType, color);
    }
}
