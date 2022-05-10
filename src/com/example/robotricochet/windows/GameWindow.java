package com.example.robotricochet.windows;


import com.example.robotricochet.components.CardType;
import com.example.robotricochet.components.Direction;
import com.example.robotricochet.components.RobotColor;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.game.Board;
import com.example.robotricochet.entities.game.BoardCenter;
import com.example.robotricochet.entities.game.PickedCard;
import com.example.robotricochet.entities.game.Robot;
import com.example.robotricochet.entities.game.Wall;
import com.example.robotricochet.entities.ui.TextInput;
import com.example.robotricochet.entities.ui.Timer;
import com.example.robotricochet.systems.GameSystem;
import com.example.robotricochet.systems.LevelSystem;

public class GameWindow extends Window {

    private final LevelSystem levelSystem = new LevelSystem();
    private final GameSystem gameSystem;

    public GameWindow() {
        super();
        gameSystem = new GameSystem(entitySystem);
        levelSystem.loadLevel(1);
        entitySystem.add(new Board(), new BoardCenter());
        for (Vector2 pos : levelSystem.getVerticalWalls()) {
            entitySystem.add(new Wall(pos, Direction.Horizontal));
        }
        for (Vector2 pos : levelSystem.getHorizontalWalls()) {
            entitySystem.add(new Wall(pos, Direction.Vertical));
        }
        Robot robot = new Robot(RobotColor.Red, new Vector2(4, 4));
        entitySystem.add(robot, new PickedCard(CardType.Moon, RobotColor.Red), new Timer());
    }
}
