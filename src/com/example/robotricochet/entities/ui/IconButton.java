package com.example.robotricochet.entities.ui;

import com.example.robotricochet.utils.Vector2i;
import com.example.robotricochet.entities.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

@Builder
public class IconButton extends Entity {

    @Getter
    @Setter
    @Builder.Default
    private int iconSize = 20;

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
    private Consumer<IconButton> onClick;

    @Builder.Default
    private Vector2i padding = Vector2i.zero();
    @Builder.Default
    private Vector2i offset = Vector2i.zero();

    @Getter
    private String iconName;
    private BufferedImage icon;

    private boolean autoSize;

    @Override
    public void init() {
//        icon = assetsManager.getSvgAsset("icons/" + iconName, iconSize, iconSize);
        if (autoSize) {
            setWidth(icon.getWidth() + padding.x * 2);
            setHeight(icon.getHeight() + padding.y * 2);
        }
        super.setHeight(getHeight());
        super.setWidth(getWidth());
        setPosition(center.translate(-width / 2, -height / 2));
    }

    @Override
    public void draw(Graphics2D g) {
        if (!isVisible()) return;
        g.setColor(getBgColor());
        g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 100, 100);
        Vector2i iconPos = getCenter().translate(offset).translate(-icon.getWidth() / 2, -icon.getHeight() / 2);
        g.drawImage(icon, iconPos.x, iconPos.y, icon.getWidth(), icon.getHeight(), null);
    }

    public void setIconName(String text) {
        iconName = text;
//        icon = assetsManager.getSvgAsset("icons/" + iconName, iconSize, iconSize);
        if (autoSize) {
            setWidth(iconSize + padding.x * 2);
            setHeight(iconSize + padding.y * 2);
            super.setHeight(getHeight());
            super.setWidth(getWidth());
        }
        setPosition(center.translate(-width / 2, -height / 2));
    }

    @Override
    public boolean onClick(Vector2i position) {
        onClick.accept(this);
        return false;
    }
}
