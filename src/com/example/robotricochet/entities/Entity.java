package com.example.robotricochet.entities;

import com.example.robotricochet.components.Position;
import com.example.robotricochet.components.Bounds;
import com.example.robotricochet.utils.MathUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.logging.Logger;

@RequiredArgsConstructor
public abstract class Entity {

    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Getter
    @Setter
    protected Bounds bounds;

    @Getter
    @Setter
    private boolean isVisible = true;

    @Getter
    @Setter
    private boolean dirty = true;

    @Getter
    private final int id;

    public Entity() {
        id = MathUtils.generateId();
    }
    public boolean onClick(Position position) {
        return false;
    }
    public boolean onHover(Position position) {
        return false;
    }
    public boolean onEnter(Position position) {
        return false;
    }
    public boolean onLeave(Position position) {
        return false;
    }
}
