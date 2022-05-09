package com.example.robotricochet.windows;

import com.example.robotricochet.entities.ui.MenuButton;

import java.util.function.Consumer;

public class MenuWindow extends Window {

    public MenuWindow(Consumer<Void> onStart) {
        entitySystem.add(new MenuButton("play", onStart));
    }
}
