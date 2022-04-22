package com.example.robotricochet.entities;

import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.systems.EntitySystem;
import com.example.robotricochet.systems.ResourceSystem;
import lombok.Getter;
import lombok.Setter;

import java.awt.Graphics2D;
import java.util.logging.Logger;

public abstract class Entity {

    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    protected final ResourceSystem resourceSystem = new ResourceSystem();

    @Setter
    protected EntitySystem entitySystem;

    @Getter
    @Setter
    protected Bounds bounds;

    @Getter
    @Setter
    private boolean isVisible = true;

    @Getter
    @Setter
    private boolean dirty = true;

    public abstract void init();

    public abstract void update(double delta);

    public abstract void draw(Graphics2D g);

    public abstract void onResize(Vector2 screenSize);

    public boolean onClick(Vector2 position) {
        return false;
    }

    public boolean onHover(Vector2 position) {
        return false;
    }

    public boolean onEnter(Vector2 position) {
        return false;
    }

    public boolean onLeave(Vector2 position) {
        return false;
    }

    public Vector2 getPosition() {
        return bounds.getPosition();
    }
    public Vector2 getSize() {
        return bounds.getSize();
    }
}
