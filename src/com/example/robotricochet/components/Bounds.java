package com.example.robotricochet.components;

import java.awt.geom.Rectangle2D;

public class Bounds {

    public Vector2 position;
    public Vector2 size;

    public Bounds(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }

    public Bounds(int x, int y, int width, int height) {
        this.position = new Vector2(x, y);
        this.size = new Vector2(width, height);
    }

    public Bounds(Bounds bounds) {
        this.position = new Vector2(bounds.position);
        this.size = new Vector2(bounds.size);
    }

    public Bounds(Vector2 position, int width, int height) {
        this.position = new Vector2(position);
        this.size = new Vector2(width, height);
    }

    public boolean contains(Vector2 point) {
        return contains(point.x, point.y);
    }

    public boolean contains(int x, int y) {
        return (x >= position.x && x < position.x + size.x && y >= position.y && y < position.y + size.y);
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }
    public Vector2 getSize() {
        return new Vector2(size);
    }

    public Rectangle2D toRect() {
        return new Rectangle2D.Float(position.x, position.y, size.x, size.y);
    }

    @Override
    public String toString() {
        return "Bounds{" +
                "position=" + position +
                ", size=" + size +
                '}';
    }
}
