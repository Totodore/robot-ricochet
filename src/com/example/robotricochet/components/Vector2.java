package com.example.robotricochet.components;

import java.awt.Point;

public class Vector2 {

    public int x;
    public int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
}
