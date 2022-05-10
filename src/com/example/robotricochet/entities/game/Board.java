package com.example.robotricochet.entities.game;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Move;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

public class Board extends Entity {

    public int cellSize;

    @Nullable
    @Getter
    private Move[] moves;

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(0x252525));    // Border color
        // Drawing the cells with border radius for the corners
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int x = getPosition().x + i * cellSize;
                int y = getPosition().y + j * cellSize;
                g.drawImage(getTile(cellSize), x, y, null);
                g.drawLine(x, y, x + cellSize, y);
                g.drawLine(x, y, x, y + cellSize);
            }
        }
        if (moves != null && moves.length > 0) {
            for (Move move : moves) {
                if (move == null)
                    continue;
                g.setColor(move.getRobot().getColor().getTransparentColor());
                Vector2 realOrigin = move.getOrigin().scale(cellSize).translate(getPosition());
                Vector2 realDestination = move.getDestination().scale(cellSize).translate(getPosition());
                if (move.getDestination().y == move.getOrigin().y)
                    g.fillRect(realOrigin.x, realOrigin.y, realDestination.x - realOrigin.x, cellSize);
                else
                    g.fillRect(realOrigin.x, realOrigin.y, cellSize, realDestination.y - realOrigin.y);
            }
        }
    }

    @Override
    public void onResize(Vector2 screenSize) {
        logger.info("Resizing board");
        cellSize = (int) ((screenSize.y * 0.94) / 16);
        setBounds(new Bounds(48, (int) (screenSize.y * 0.03), cellSize * 16, cellSize * 16));
        resourceSystem.removeSizedImageAsset("tiles/tile.png");
    }

    public void setMoves(Move[] moves) {
        this.moves = moves;
        setDirty(true);
    }

    private Image getTile(int s) {
        return resourceSystem.getImageAsset("tiles/tile.png", s, s);
    }
}
