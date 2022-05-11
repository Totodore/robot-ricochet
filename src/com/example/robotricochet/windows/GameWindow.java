package com.example.robotricochet.windows;


import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.CardType;
import com.example.robotricochet.components.Direction;
import com.example.robotricochet.components.RobotColor;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.game.Board;
import com.example.robotricochet.entities.game.BoardCenter;
import com.example.robotricochet.entities.game.Card;
import com.example.robotricochet.entities.game.PickedCard;
import com.example.robotricochet.entities.game.Robot;
import com.example.robotricochet.entities.game.Wall;
import com.example.robotricochet.entities.ui.Timer;
import com.example.robotricochet.systems.GameSystem;
import com.example.robotricochet.systems.LevelSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWindow extends Window {

    private final LevelSystem levelSystem = new LevelSystem();
    private final GameSystem gameSystem;

    public GameWindow() {
        super();
        gameSystem = new GameSystem(entitySystem, levelSystem);
        levelSystem.loadLevel(1);

//        board.setMoves(new Move[]{
//                new Move(new Vector2(0, 0), new Vector2(0, 4), robot),
//                new Move(new Vector2(0, 0), new Vector2(4, 0), robot),
//        });
    }

    @Override
    public void init() {
        initBoard();
        initWalls();
        initRobots();
        initGoals();
        super.init();
    }

    private void initBoard() {
        addEntity(new Board(), new BoardCenter(), new PickedCard(CardType.Moon, RobotColor.Red), new Timer());
    }

    private void initRobots() {
        Random random = new Random();
        Bounds center = new Bounds(7, 7, 2, 2);
        for (int i = 0; i < 4; i++) {
            int x, y;
            Robot robot;
            do {
                x = random.nextInt(16);
                y = random.nextInt(16);
            } while (levelSystem.hasWallNear(new Vector2(x, y)) || center.contains(new Vector2(x, y)));
            robot = new Robot(RobotColor.values()[i], new Vector2(x, y));
            addEntity(robot);
        }
    }

    private void initGoals() {
        ArrayList<Card> cards = new ArrayList<>();
        Random random = new Random();
        List<Vector2> positions = levelSystem.getCardPositions();
        for (int i = 0; i < 16; i++) {
            Card card;
            do {
                card = new Card(CardType.values()[random.nextInt(4)], RobotColor.values()[random.nextInt(4)], positions.get(i));
            } while (cards.contains(card));
            cards.add(card);
            addEntity(card);
        }
    }

    private void initWalls() {
        for (Vector2 pos : levelSystem.getVerticalWalls()) {
            addEntity(new Wall(pos, Direction.Vertical));
        }
        for (Vector2 pos : levelSystem.getHorizontalWalls()) {
            addEntity(new Wall(pos, Direction.Horizontal));
        }
    }
}
