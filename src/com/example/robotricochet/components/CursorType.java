package com.example.robotricochet.components;

import lombok.Getter;
import lombok.Setter;

import java.awt.Cursor;

public enum CursorType {
    BASE,
    POINTER;

    public String getName() {
        return name();
    }

    public String getPath() {
        return "cursors/cursor_" + name().toLowerCase() + ".png";
    }

    @Getter
    @Setter
    private Cursor cursor;
}
