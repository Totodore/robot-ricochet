package com.example.robotricochet.systems;

import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

public class RessourceSystem {

    private static final String DIRECTORY = "/img/";

    private static final HashMap<String, BufferedImage> images = new HashMap<>();
    private final Logger logger = Logger.getLogger(RessourceSystem.class.getName());

    public Image getImageAsset(String name) {
        return getImageAsset(name, -1, -1);
    }

    public Image getImageAsset(String name, int width, int height) {
        return getImageAsset(name, width, height, 0);
    }

    @Nullable
    public Image getImageAsset(String name, int width, int height, int rotation) {
        String key = name + "-" + width + "x" + height + "-" + rotation;
        if (images.containsKey(key))
            return images.get(key);
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResource(DIRECTORY + name)));
            if (width != -1 || height != -1 || rotation != 0)
                image = transformImage(image, width, height, rotation);
            images.put(key, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Loaded image: " + key + " - current cache size: " + images.size());
        return images.get(key);
    }

    public void removeSizedImageAsset(String name) {
        final ArrayList<String> keys = new ArrayList<>();
        for (String key : images.keySet()) {
            if (key.startsWith(name))
                keys.add(key);
        }
        for (String key : keys)
            images.remove(key);
    }

    private BufferedImage transformImage(BufferedImage image, float w, float h, float angle) {
        final AffineTransform at = new AffineTransform();
        final float scaleX;
        final float scaleY;
        int originalWidth = image.getWidth(null);
        int originalHeight = image.getHeight(null);

        // Scaling
        if (w == -1 && h != -1)
            scaleX = scaleY = h / originalHeight;
        else if (w != -1 && h == -1)
            scaleX = scaleY = w / originalWidth;
        else {
            scaleX = w / originalWidth;
            scaleY = h / originalHeight;
        }
        at.scale(scaleX, scaleY);

        // Rotation
        if (angle != 0) {
            final double rads = Math.toRadians(angle);
            final double sin = Math.abs(Math.sin(rads));
            final double cos = Math.abs(Math.cos(rads));
            final int currentW = (int) Math.floor(originalWidth * cos + originalHeight * sin);
            final int currentH = (int) Math.floor(originalHeight * cos + originalWidth * sin);
            at.translate(currentW / 2f, currentH / 2f);
            at.rotate(rads, 0, 0);
            at.translate(- originalWidth / 2f, - originalHeight / 2f);
        }

        // Transformation
        BufferedImage transformedImage = new BufferedImage(originalWidth, originalHeight, BufferedImage.TYPE_INT_ARGB);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(image, transformedImage);
        return transformedImage;
    }

    public void removeImageAsset(String name) {
        images.remove(name);
    }
}
