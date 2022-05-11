package com.example.robotricochet.windows;


import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.CardType;
import com.example.robotricochet.components.Direction;
import com.example.robotricochet.components.RobotColor;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.game.Board;
import com.example.robotricochet.entities.game.BoardCenter;
import com.example.robotricochet.entities.game.Card;
import com.example.robotricochet.entities.game.Move;
import com.example.robotricochet.entities.game.MoveDone;
import com.example.robotricochet.entities.game.PickedCard;
import com.example.robotricochet.entities.game.Robot;
import com.example.robotricochet.entities.game.Wall;
import com.example.robotricochet.entities.ui.ResetPositionButton;
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
        setMinSize(new Vector2(1000, 300));
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
        initGoals();
        initRobots();
        super.init();
    }

    private void initBoard() {
        addEntity(new Board(), new BoardCenter(), new Timer());
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

    public void drawCard() {
        entitySystem.remove(PickedCard.class);
        Random random = new Random();
        PickedCard card = new PickedCard(CardType.values()[random.nextInt(4)], RobotColor.values()[random.nextInt(4)]);
        addEntity(card);
    }

    public void showMoves(Robot robot) {
        // Removing previous moves
        entitySystem.removeMany(Move.class);
        for (Vector2 dest : gameSystem.getDestinations(robot)) {
            if (dest == null) {
                continue;
            }
            logger.info("Destination: " + dest);
            addEntity(new Move(robot.getBoardPosition(), dest, robot));
        }
    }

    /**
     * Move the robot to the move destination and clear all moves
     *
     * @param robot       The robot to move
     * @param destination The board destination of the robot
     */
    public void moveRobot(Robot robot, Vector2 destination, Move move) {
        if (entitySystem.find(ResetPositionButton.class).isEmpty()) {
            addEntity(new ResetPositionButton(robot, robot.getBoardPosition()));
        }
        addEntity(new MoveDone(move));
        robot.setBoardDestination(destination);
        entitySystem.removeMany(Move.class);
    }

    public void resetRobotPosition(Robot robot, Vector2 position) {
        robot.setBoardPosition(position);
        entitySystem.remove(ResetPositionButton.class);
        entitySystem.removeMany(MoveDone.class);
        entitySystem.removeMany(Move.class);
    }
}
