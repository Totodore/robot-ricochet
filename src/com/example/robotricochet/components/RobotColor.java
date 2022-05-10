package com.example.robotricochet.components;

import lombok.Getter;

import java.awt.Color;

public enum RobotColor {
    Red(new Color(0xFF0000)),
    Green(new Color(0x0000FF)),
    Blue(new Color(0xBA4AE1)),
    Silver(new Color(0xB2ACAC));

    @Getter
    private final Color color;

    RobotColor(Color color) {
        this.color = color;
    }

    public Color getTransparentColor() {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), 0x50);
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
