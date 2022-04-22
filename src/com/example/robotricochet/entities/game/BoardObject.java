package com.example.robotricochet.entities.game;

import com.example.robotricochet.components.Vector2;

public interface BoardObject {

    Vector2 getBoardPosition();
    void setBoardPosition(Vector2 position);
}
