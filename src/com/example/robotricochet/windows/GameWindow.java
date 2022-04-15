package com.example.robotricochet.windows;


import com.example.robotricochet.components.Direction;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.game.Board;
import com.example.robotricochet.entities.game.Wall;
import com.example.robotricochet.systems.LevelSystem;

import java.util.Arrays;

public class GameWindow extends Window {

    private final LevelSystem levelSystem = new LevelSystem();
    public GameWindow() {
        super(true);
        levelSystem.loadLevel(1);
        entitySystem.add(new Board());
        for(Vector2 pos : levelSystem.getVerticalWalls())
            entitySystem.add(new Wall(pos, Direction.Vertical));
        for(Vector2 pos : levelSystem.getHorizontalWalls())
            entitySystem.add(new Wall(pos, Direction.Horizontal));
    }
}
