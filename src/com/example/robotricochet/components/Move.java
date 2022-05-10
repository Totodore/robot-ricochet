package com.example.robotricochet.components;

import com.example.robotricochet.entities.game.Robot;
import lombok.Getter;

public class Move {
    @Getter
    private final Vector2 origin;
    @Getter
    private final Vector2 destination;
    @Getter
    private final Robot robot;

    public Move(Vector2 origin, Vector2 destination, Robot robot) {
        this.origin = origin;
        this.destination = destination;
        this.robot = robot;
    }
}
