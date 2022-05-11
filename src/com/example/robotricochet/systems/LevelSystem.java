package com.example.robotricochet.systems;

import com.example.robotricochet.components.Bounds;
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
    @Getter
    private final List<Vector2> cardPositions = new ArrayList<>();

    public void loadLevel(int level) {
        try {
            final byte[] data = Objects.requireNonNull(getClass().getResourceAsStream(DIRECTORY + "level" + level + ".rr")).readAllBytes();
            int state = 0;
            for (int i = 0; i < data.length; i++) {
                if (data[i] == 0x00 && data[i + 1] == 0x00)
                    state++;
                if (data[i] == 0x00)   // We skip separators but this assume that a wall at 0,0 cannot exist
                    continue;
                int x = (data[i] & 0xFF) >> 4;          // The first 4 bytes are the x coordinate
                int y = (data[i] & 0xFF) & 0x0F;        // The last 4 bytes are the y coordinate
                if (state == 0)
                    verticalWalls.add(new Vector2(x, y));
                else if (state == 1)
                    horizontalWalls.add(new Vector2(x, y));
                else
                    cardPositions.add(new Vector2(x, y));
            }
            // Borders
            for (int i = 0; i < 16; i++) {
                verticalWalls.add(new Vector2(-1, i));
                verticalWalls.add(new Vector2(15, i));
                horizontalWalls.add(new Vector2(i, -1));
                horizontalWalls.add(new Vector2(i, 15));
            }
            // Center
            for (int i = 7; i < 9; i++) {
                verticalWalls.add(new Vector2(6, i));
                verticalWalls.add(new Vector2(8, i));
                horizontalWalls.add(new Vector2(i, 6));
                horizontalWalls.add(new Vector2(i, 8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if there is a wall near this position (1 tile away)
     *
     * @param position The position to check
     */
    public boolean hasWallNear(Vector2 position) {
        Bounds bound = new Bounds(position.x - 1, position.y - 1, 2, 2);
        for (Vector2 wall : verticalWalls) {
            if (bound.contains(wall))
                return true;
        }
        for (Vector2 wall : horizontalWalls) {
            if (bound.contains(wall))
                return true;
        }
        return false;
    }

    /**
     * Return all the wall corners of the board
     */
    public Vector2[] getCorners() {
        Vector2[] corners = new Vector2[16];
        return new Vector2[]{};
    }
}
