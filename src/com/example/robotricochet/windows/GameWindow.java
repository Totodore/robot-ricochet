package com.example.robotricochet.windows;


import com.example.robotricochet.entities.game.Board;

public class GameWindow extends Window {

    public GameWindow() {
        super(true);
        addEntity(new Board());
    }
}
