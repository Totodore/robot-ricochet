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

    public Vector2(int d) {
        this.x = d;
        this.y = d;
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
    public static Vector2 min(Vector2 v1, Vector2 v2) {
        return new Vector2(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y));
    }
    public static Vector2 max(Vector2 v1, Vector2 v2) {
        return new Vector2(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y));
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

    public Vector2 scale(float s) {
        Vector2 v = new Vector2(this);
        v.x *= s;
        v.y *= s;
        return v;
    }

    public Vector2 scale(float x, float y) {
        Vector2 v = new Vector2(this);
        v.x *= x;
        v.y *= y;
        return v;
    }

    /**
     * Clamps the current vector to another vector according to its direction.
     * The current vector won't be able to go beyond the other vector.
     * @param point The limiting vector.
     * @param direction The direction of the current vector.
     * @return The clamped vector.
     */
    public Vector2 clamp(Vector2 point, Vector2 direction) {
        Vector2 v = new Vector2(this);
        if (v.x < point.x && direction.x == -1) v.x = point.x;
        if (v.y < point.y && direction.y == -1) v.y = point.y;
        if (v.x > point.x && direction.x == 1) v.x = point.x;
        if (v.y > point.y && direction.y == 1) v.y = point.y;
        return v;
    }

    public int length() {
        return (int) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public int max() {
        return Math.max(this.x, this.y);
    }

    public Vector2 normalize() {
        Vector2 v = new Vector2(this);
        v.x = (int) Math.signum(v.x);
        v.y = (int) Math.signum(v.y);
        return v;
    }

    @Override
    public Vector2 clone() {
        return new Vector2(this);
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
