package com.example.robotricochet.entities;

import com.example.robotricochet.utils.Vector2i;
import com.example.robotricochet.windows.Window;
import com.example.robotricochet.managers.AssetsManager;
import com.example.robotricochet.utils.Bounds;
import com.example.robotricochet.utils.MathUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.awt.Graphics2D;
import java.util.logging.Logger;

@RequiredArgsConstructor
public abstract class Entity implements Drawable {

    protected final Window window;
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    protected static final AssetsManager assetsManager = new AssetsManager();

    @Getter
    protected Vector2i position;

    @Getter
    private int width, height;

    @Getter
    private boolean isVisible = true;

    @Getter
    @Setter
    private boolean dirty = true;

    @Getter
    private final int id;

    public Entity(Window window) {
        this.window = window;
        id = MathUtils.generateId();
    }
    public Entity() {
        this(null);
    }

    public abstract void init();

    public abstract void draw(Graphics2D g);

    public void setPosition(int x, int y) {
        this.position = new Vector2i(x, y);
        setDirty(true);
    }

    public void setPosition(Vector2i point) {
        this.position = new Vector2i(point);
        setDirty(true);
    }

    public void setWidth(int width) {
        this.width = width;
        setDirty(true);
    }

    public void setHeight(int height) {
        this.height = height;
        setDirty(true);
    }
    public void setVisible(boolean visible) {
        isVisible = visible;
        setDirty(true);
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public Bounds getBounds() {
        return new Bounds(position, width, height);
    }

    public boolean onClick(Vector2i position) {
        return false;
    }
    public boolean onHover(Vector2i position) {
        return false;
    }
    public boolean onEnter(Vector2i position) {
        return false;
    }
    public boolean onLeave(Vector2i position) {
        return false;
    }
}
