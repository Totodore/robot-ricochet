package com.example.robotricochet.entities.ui;

import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.utils.Vector2i;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.function.Consumer;

@Builder
public class Button extends Entity {

    @Getter
    private String text;

    @Getter
    @Setter
    private float fontSize;

    @Getter
    @Setter
    private Color fontColor;

    @Getter
    @Setter
    @Builder.Default
    private Color bgColor = new Color(0x2C969696, true);

    @Getter
    @Setter
    private int width;

    @Getter
    @Setter
    private int height;

    @Getter
    @Setter
    private Vector2i center;

    @Getter
    private Consumer<Button> onClick;

    @Builder.Default
    private Vector2i padding = Vector2i.zero();

    private boolean autoSize;
    private Graphics2D graphics;

    private CenteredText textEntity;

    @Override
    public void init() {

    }

    public void init(Graphics2D g) {
        init();
        textEntity = new CenteredText(window, getText(), center, getFontSize(), getFontColor(), graphics);
        textEntity.init(g);
        if(autoSize) {
            setWidth(textEntity.getWidth() + padding.x * 2);
            setHeight(textEntity.getHeight() + padding.y * 2);
        }
        setPosition(center.translate(- width / 2, - height / 2));
        super.setHeight(getHeight());
        super.setWidth(getWidth());
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getBgColor());
        g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 10, 10);
//        g.drawRoundRect(getX(), getY(), getWidth(), getHeight(), 10, 10);
        textEntity.draw(g);
    }

    public void setText(String text) {
        this.text = text;
        textEntity.setText(text);
        if(autoSize) {
            setWidth(textEntity.getWidth() + padding.x * 2);
            setHeight(textEntity.getHeight() + padding.y * 2);
            super.setHeight(getHeight());
            super.setWidth(getWidth());
        }
        setPosition(center.translate(- width / 2, - height / 2));
    }

    @Override
    public boolean onClick(Vector2i position) {
        System.out.println("Clicked on button");
        onClick.accept(this);
        return false;
    }
}
