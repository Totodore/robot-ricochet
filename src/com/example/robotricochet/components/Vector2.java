package com.example.robotricochet.components;

import java.awt.Point;
import java.util.Objects;

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


    public static Vector2 zero() {
        return new Vector2(0, 0);
    }

    public Vector2 translate(Vector2 v) {
        Vector2 v2 = new Vector2(this);
        v2.x += v.x;
        v2.y += v.y;
        return v2;
    }
    public Vector2 translate(int x, int y) {
        return this.translate(new Vector2(x, y));
    }
    public Vector2 translate(int s) {
        return this.translate(s, s);
    }
    public Vector2 reverse() {
        Vector2 v = new Vector2(this);
        v.x = -this.x;
        v.y = -this.y;
        return v;
    }
    public Vector2 scale(int s) {
        Vector2 v = new Vector2(this);
        v.x *= s;
        v.y *= s;
        return v;
    }
    public Vector2 scale(int x, int y) {
        Vector2 v = new Vector2(this);
        v.x *= x;
        v.y *= y;
        return v;
    }
    public Vector2 scale(Vector2 v) {
        return this.scale(v.x, v.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2 v) {
            return Objects.equals(this.x, v.x) && Objects.equals(this.y, v.y);
        } else return super.equals(obj);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}