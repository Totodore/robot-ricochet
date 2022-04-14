package com.example.robotricochet.components;

import java.awt.Point;

public class Position {

    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Position(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
}
