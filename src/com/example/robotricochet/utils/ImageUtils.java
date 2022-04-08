package com.example.robotricochet.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Class for manipulating images (adding filters and effects)
 */
@AllArgsConstructor
public class ImageUtils {

    @Getter
    private BufferedImage image;

    public ImageUtils applyAntiAliasingFilter() {
        return resize(image.getWidth(), image.getHeight());
    }

    /**
     * Resize the image to the given width and height. If one of the dimensions is null the other dimension will be
     * resized to keep the ratio.
     */
    public ImageUtils resize(@Nullable Integer width, @Nullable Integer height) {
        if (width == null && height == null)
            return this;
        float ratio = (float) image.getWidth() / image.getHeight();
        if (width == null)
            width = (int) (ratio * height);
        else if (height == null)
            height = (int) (ratio / width);

        Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        image = dimg;
        return this;
    }
}
