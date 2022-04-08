package com.example.robotricochet.entities.ui;

import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.utils.Vector2i;
import com.example.robotricochet.windows.Window;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class CenteredText extends Entity {

    @Getter
    private String text;

    private Vector2i center;
    private final float fontSize;
    private Font font;

    @Getter
    private Color color;

    @Setter
    protected Graphics2D graphics;

    public CenteredText(Window window, String text, Vector2i center, float fontSize, Color color, Graphics2D graphics) {
        super(window);
        this.text = text;
        this.center = center;
        this.fontSize = fontSize;
        this.graphics = graphics;
        this.color = color;
    }

    @Override
    public void init() {
        graphics.setFont(font.deriveFont(fontSize));
        computeSize();
    }

    public void init(Graphics2D g) {
        graphics = g;
        font = graphics.getFont();
        init();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setFont(font.deriveFont(fontSize));
        g.setColor(color);
        g.drawString(text, getX(), getY() + g.getFontMetrics().getAscent());
    }

    public void setText(String text) {
        this.text = text;
        setDirty(true);
        computeSize();
    }

    public void setCenter(Vector2i point) {
        center = new Vector2i(point);
        computeSize();
    }

    public void setCenter(int x, int y) {
        center = new Vector2i(x, y);
        computeSize();
    }

    public void setColor(Color color) {
        this.color = color;
        setDirty(true);
    }

    private void computeSize() {
        if (graphics == null)
            return;
        setWidth(graphics.getFontMetrics().stringWidth(text));
        setHeight(graphics.getFontMetrics().getHeight());

        position = new Vector2i(center.x - getWidth() / 2, center.y - getHeight() / 2);
    }
}
