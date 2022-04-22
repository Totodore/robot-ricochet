package com.example.robotricochet.systems;

import com.example.robotricochet.components.Direction;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.game.BoardObject;
import com.example.robotricochet.entities.game.Wall;

public class GameSystem {

    private final EntitySystem entitySystem;

    public GameSystem(EntitySystem entitySystem) {
        this.entitySystem = entitySystem;
    }

    public boolean isRobotHit(Vector2 position, Direction direction) {
        return entitySystem.findWhere(Wall.class, e -> e.getDirection() == direction && e.getPosition() == position).isPresent();
    }

    public boolean isWallHit(Vector2 position, Direction direction) {
        return entitySystem.findWhere(Wall.class, e -> e.getDirection() == direction && e.getPosition() == position).isPresent();
    }

    public boolean isObjectHit(Vector2 position, Direction direction) {
        return entitySystem.findWhere(BoardObject.class, e -> (!(e instanceof Wall wall) || wall.getDirection() == direction) && e.getBoardPosition() == position).isPresent();
    }
}
