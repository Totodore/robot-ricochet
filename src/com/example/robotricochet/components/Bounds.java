package com.example.robotricochet.components;

public class Bounds {

    public Position position;
    public Position size;

    public Bounds(Position position, Position size) {
        this.position = position;
        this.size = size;
    }

    public Bounds(int x, int y, int width, int height) {
        this.position = new Position(x, y);
        this.size = new Position(width, height);
    }

    public Bounds(Bounds bounds) {
        this.position = new Position(bounds.position);
        this.size = new Position(bounds.size);
    }

    public Bounds(Position position, int width, int height) {
        this.position = new Position(position);
        this.size = new Position(width, height);
    }

    public boolean contains(Position point) {
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
