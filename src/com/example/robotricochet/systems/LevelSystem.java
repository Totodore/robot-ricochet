package com.example.robotricochet.systems;

import com.example.robotricochet.components.Vector2;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LevelSystem {

    private static final String DIRECTORY = "/levels/";

    @Getter
    private final List<Vector2> verticalWalls = new ArrayList<>();
    @Getter
    private final List<Vector2> horizontalWalls = new ArrayList<>();

    public void loadLevel(int level) {
        try {
            final byte[] data = Objects.requireNonNull(getClass().getResourceAsStream(DIRECTORY + "level" + level + ".rr")).readAllBytes();
            boolean isVertical = false;
            for (int i = 0; i < data.length; i++) {
                if (data[i] == 0x00 && data[i + 1] == 0x00 && !isVertical)
                    isVertical = true;
                if (data[i] == 0x00)   // We skip separators but this assume that a wall at 0,0 cannot exist
                    continue;
                int x = (data[i] & 0xFF) >> 4;          // The first 4 bytes are the x coordinate
                int y = (data[i] & 0xFF) & 0x0F;        // The last 4 bytes are the y coordinate
                if (isVertical)
                    verticalWalls.add(new Vector2(x, y));
                else
                    horizontalWalls.add(new Vector2(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
