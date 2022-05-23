package com.example.robotricochet.entities.ui;

import com.example.robotricochet.Utils;
import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.CursorType;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class MenuButton extends Entity {

    private final Consumer<Void> onClick;
    private final String img;
    private boolean isClicked = false;

    public MenuButton(String img, Consumer<Void> onClick) {
        this.img = img;
        this.onClick = onClick;
    }

    @Override
    public void init() {
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(getImg(bounds.getSize().x), bounds.getPosition().x, bounds.getPosition().y, null);
    }

    @Override
    public void onResize(Vector2 screenSize) {
        setBounds(new Bounds(
                new Vector2(
                        screenSize.x / 2 - 150,
                        screenSize.y / 2
                ),
                new Vector2(
                        300,
                        50
                )
        ));
    }

    @Override
    public boolean onClick(Vector2 position) {
        isClicked = true;
        onClick.accept(null);
        return true;
    }

    @Override
    public void onEnter(Vector2 position) {
        window.setCursor(CursorType.POINTER);
    }

    @Override
    public void onLeave(Vector2 position) {
        window.setCursor(CursorType.BASE);
    }

    public BufferedImage getImg(int s) {
        if (!isClicked)
            return resourceSystem.getImageAsset("buttons/" + img + ".png", s, -1);
        else
            return resourceSystem.getImageAsset("buttons/" + img + "-click.png", s, -1);
    }
}
