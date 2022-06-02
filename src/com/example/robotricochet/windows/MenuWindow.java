package com.example.robotricochet.windows;

import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.ui.MenuButton;

import java.util.function.Consumer;

public class MenuWindow extends Window {

    public MenuWindow(Consumer<Void> onStart) {
        setMinSize(new Vector2(1200, 500));
        addEntity(new MenuButton("play", onStart));
    }
}
