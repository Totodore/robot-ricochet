package com.example.robotricochet.utils;

public class Bounds {

    public Vector2i position;
    public Vector2i size;

    public Bounds(Vector2i position, Vector2i size) {
        this.position = position;
        this.size = size;
    }

    public Bounds(int x, int y, int width, int height) {
        this.position = new Vector2i(x, y);
        this.size = new Vector2i(width, height);
    }

    public Bounds(Bounds bounds) {
        this.position = new Vector2i(bounds.position);
        this.size = new Vector2i(bounds.size);
    }

    public Bounds(Vector2i position, int width, int height) {
        this.position = new Vector2i(position);
        this.size = new Vector2i(width, height);
    }

    public boolean contains(Vector2i point) {
        return contains(point.x, point.y);
    }

    public boolean contains(int x, int y) {
        return (x >= position.x && x < position.x + size.x && y >= position.y && y < position.y + size.y);
    }

    @Override
    public String toString() {
        return "Bounds{" +
                "position=" + position +
                ", size=" + size +
                '}';
    }
}
