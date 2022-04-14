package com.example.robotricochet.systems;

import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

public class RessourceSystem {

    private static final String DIRECTORY = "/img/";

    private static final HashMap<String, Image> images = new HashMap<>();
    private final Logger logger = Logger.getLogger(RessourceSystem.class.getName());

    public Image getImageAsset(String name) {
        return getImageAsset(name, -1, -1);
    }


    @Nullable
    public Image getImageAsset(String name, int width, int height) {
        String key = name + "-" + width + "x" + height;
        if (images.containsKey(key))
            return images.get(key);
        try {
            Image image = ImageIO.read(Objects.requireNonNull(getClass().getResource(DIRECTORY + name)));
            images.put(key, image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Loaded image: " + key + " - current cache size: " + images.size());
        return images.get(key);
    }

    public void removeSizedImageAsset(String name) {
        for (String key : images.keySet()) {
            if (key.startsWith(name))
                images.remove(key);
        }
    }

    public void removeImageAsset(String name) {
        images.remove(name);
    }
}
