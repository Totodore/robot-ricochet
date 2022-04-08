package com.example.robotricochet.managers;

import com.example.robotricochet.utils.ImageUtils;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class AssetsManager {

    private static final String DIRECTORY = "/img/";

    private static final HashMap<String, BufferedImage> images = new HashMap<>();

    @Nullable
    public BufferedImage getImageAsset(String name) {
        if (images.containsKey(name))
            return images.get(name);
        try {
            images.put(name, ImageIO.read(Objects.requireNonNull(AssetsManager.class.getResourceAsStream(DIRECTORY + name))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images.get(name);
    }

    public void load(String name) {
        if (getImageAsset(name) == null)
            throw new NullPointerException("Image " + name + " not found");
    }
    public void removeImageAsset(String name) {
        images.remove(name);
    }
}
