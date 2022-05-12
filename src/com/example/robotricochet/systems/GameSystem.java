package com.example.robotricochet.systems;

import com.example.robotricochet.components.Direction;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.game.BoardObject;
import com.example.robotricochet.entities.game.Robot;
import com.example.robotricochet.entities.game.Wall;

public class GameSystem {

    private final EntitySystem entitySystem;
    private final LevelSystem levelSystem;

    public GameSystem(EntitySystem entitySystem, LevelSystem levelSystem) {
        this.entitySystem = entitySystem;
        this.levelSystem = levelSystem;
    }

    public boolean isRobotHit(Vector2 position) {
        return entitySystem.findWhere(Robot.class, e ->e.getBoardPosition().equals(position)).isPresent();
    }

    public boolean isWallHit(Vector2 position, Direction direction) {
        return entitySystem.findWhere(Wall.class, e -> e.getDirection() == direction && e.getBoardPosition().equals(position)).isPresent();
    }

    public boolean isWallOrRobotHit(Vector2 position, Direction direction) {
        return entitySystem.findWhere(BoardObject.class, e -> {
            if (e instanceof Wall wall) {
                return wall.getBoardPosition().equals(position) && wall.getDirection() == direction;
            } else if (e instanceof Robot robot) {
                return robot.getBoardPosition().equals(position);
            } else
                return false;
        }).isPresent();
    }

    public Vector2[] getDestinations(Robot robot) {
        Vector2[] destinations = new Vector2[4];
        for (int i = robot.getBoardPosition().x; i < 16; i++) {
            Vector2 currentPos = new Vector2(i, robot.getBoardPosition().y);
            if (i == robot.getBoardPosition().x && isWallHit(currentPos, Direction.Vertical)) {
                break;
            } else if (i != robot.getBoardPosition().x && isRobotHit(currentPos)) {
                destinations[0] = new Vector2(i, robot.getBoardPosition().y);
                break;
            } else if (i != robot.getBoardPosition().x && isWallHit(currentPos, Direction.Vertical)) {
                destinations[0] = new Vector2(i + 1, robot.getBoardPosition().y);
                break;
            }
        }
        for (int i = robot.getBoardPosition().x; i >= -1; i--) {
            Vector2 currentPos = new Vector2(i, robot.getBoardPosition().y);
            if (i == robot.getBoardPosition().x && isWallHit(new Vector2(i - 1, robot.getBoardPosition().y), Direction.Vertical)) {
                break;
            } else if (i != robot.getBoardPosition().x && isWallOrRobotHit(currentPos, Direction.Vertical)) {
                destinations[1] = new Vector2(i + 1, robot.getBoardPosition().y);
                break;
            }
        }

        for (int i = robot.getBoardPosition().y; i < 16; i++) {
            Vector2 currentPos = new Vector2(robot.getBoardPosition().x, i);
            if (i == robot.getBoardPosition().y && isWallHit(currentPos, Direction.Horizontal)) {
                break;
            } else if (i != robot.getBoardPosition().y && isRobotHit(currentPos)) {
                destinations[2] = new Vector2(robot.getBoardPosition().x, i);
                break;
            } else if (i != robot.getBoardPosition().y && isWallHit(currentPos, Direction.Horizontal)) {
                destinations[2] = new Vector2(robot.getBoardPosition().x, i + 1);
                break;
            }
        }

        for (int i = robot.getBoardPosition().y; i >= -1; i--) {
            Vector2 currentPos = new Vector2(robot.getBoardPosition().x, i);
            if (i == robot.getBoardPosition().y && isWallHit(new Vector2(robot.getBoardPosition().x, i - 1), Direction.Horizontal)) {
                break;
            } else if (i != robot.getBoardPosition().y && isWallOrRobotHit(currentPos, Direction.Horizontal)) {
                destinations[3] = new Vector2(robot.getBoardPosition().x, i + 1);
                break;
            }
        }

        return destinations;
    }
}
