package com.example.robotricochet.entities.ui;

import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.utils.ImageUtils;
import com.example.robotricochet.windows.Window;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background extends Entity {

    private BufferedImage image;
    private static final String ASSET = "Background2.png";

    public Background(Window window) {
        super(window);
    }

    @SuppressWarnings("unused")
    public static void load() throws IOException {
        assetsManager.load(ASSET);
    }

    @Override
    public void init() {
        image = new ImageUtils(assetsManager.getImageAsset(ASSET)).resize(null, window.getHeight()).getImage();
        setPosition(0, 0);
        setWidth(image.getWidth());
        setHeight(image.getHeight());
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
    }
}
