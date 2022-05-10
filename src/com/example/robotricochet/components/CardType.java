package com.example.robotricochet.components;

public enum CardType {
    Cross,
    Gear,
    Moon,
    Planet;
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
