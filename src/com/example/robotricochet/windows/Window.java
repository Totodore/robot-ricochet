package com.example.robotricochet.windows;

import com.example.robotricochet.components.CursorType;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.entities.ui.FpsCounter;
import com.example.robotricochet.systems.EntitySystem;
import com.example.robotricochet.systems.ResourceSystem;
import lombok.Getter;
import lombok.Setter;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.logging.Logger;

public abstract class Window extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    private long lastFrameTime;
    private final ResourceSystem resourceSystem = new ResourceSystem();
    protected final EntitySystem entitySystem = new EntitySystem();
    protected final Timer windowTimer = new Timer(0, this);
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    protected static final Color REFRESH_COLOR = new Color(0x00);

    @Setter
    @Getter
    private Vector2 minSize;
    private boolean undersized = false;

    public Window(boolean showFps) {
        super();
        loadCursors();
        setCursor(CursorType.BASE);
        setBackground(REFRESH_COLOR);
        setFocusable(true);
        if (showFps)
            entitySystem.add(new FpsCounter());
    }

    public Window() {
        this(false);
    }

    public void init() {
        lastFrameTime = System.nanoTime();    // in ms
        windowTimer.start();
        addMouseListener(this);
        addMouseMotionListener(this);
        addResizeListener();
        Vector2 size = new Vector2(getWidth(), getHeight());
        for (Entity entity : entitySystem.getAllEntities()) {
            entity.onResize(size);
            entity.setDirty(true);
        }
    }

    public void addEntity(Entity entity) {
        entity.setWindow(this);
        entity.setEntitySystem(entitySystem);
        entity.init();
        entity.onResize(new Vector2(getWidth(), getHeight()));
        entitySystem.add(entity);
        logger.info("Entity " + entity.getClass().getSimpleName() + " initialized");
    }

    public void addEntity(Entity... entities) {
        for (Entity entity : entities) {
            addEntity(entity);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintComponent((Graphics2D) g);
        Toolkit.getDefaultToolkit().sync();
    }

    public void paintComponent(Graphics2D g2d) {
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB));
        // Max quality
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(REFRESH_COLOR);
        g2d.drawRect(0, 0, getWidth(), getHeight());
        if (undersized) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font(null, Font.BOLD, 30));
            String msg = "La fenêtre est trop petite !";
            g2d.drawString(msg, getWidth() / 2 - g2d.getFontMetrics().stringWidth(msg) / 2, getHeight() / 2);
            return;
        }
        for (Entity entity : entitySystem.getAllEntities()) {
            entity.draw(g2d);
            entity.setDirty(false);
        }
    }

    /**
     * Need Repaint duration : 0.0034 ms
     * Repaint duration: 1.9141 ms
     *
     */
    protected void checkRepaintJob() {
        float delta = (System.nanoTime() - lastFrameTime) / 1000000f;
        lastFrameTime = System.nanoTime();    // in ms
        boolean repaint = false;
        for (Entity entity : entitySystem.getAllEntities()) {
            entity.update(delta);
            if (entity.isDirty())
                repaint = true;
        }
        if (repaint) repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        checkRepaintJob();
    }

    /**
     * If the mouse is clicked we get all the entities that are under the mouse and call their onClick method
     * If the onClick method returns true, we stop the event propagation
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Vector2 pos = new Vector2(e.getPoint());
        for (Entity entity : entitySystem.getEntitiesAtScreenCoords(pos)) {
            if (entity.onClick(pos))
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Vector2 pos = new Vector2(e.getPoint());
        List<Entity> hoveredEntities = entitySystem.findManyWhere(Entity.class, Entity::isMouseover);
        List<Entity> entitiesUnderMouse = entitySystem.getEntitiesAtScreenCoords(pos);
        for (Entity entity : entitiesUnderMouse) {
            entity.onHover(pos.translate(entity.getPosition().reverse()));
            if (!entity.isMouseover()) {
                entity.setMouseover(true);
                entity.onEnter(pos);
            }
        }
        for (Entity entity : hoveredEntities) {
            if (!entitiesUnderMouse.contains(entity)) {
                entity.setMouseover(false);
                entity.onLeave(pos);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    private void addResizeListener() {
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                Vector2 size = new Vector2(e.getComponent().getWidth(), e.getComponent().getHeight());
                if (minSize != null && (size.x < minSize.x || size.y < minSize.y)) {
                    undersized = true;
                    entitySystem.getAllEntities().forEach(entity -> entity.setDirty(true));
                    return;
                }
                undersized = false;
                logger.info("Window Resized to " + e.getComponent().getSize());
                for (Entity entity : entitySystem.getAllEntities()) {
                    entity.onResize(size);
                    entity.setDirty(true);
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    private void loadCursors() {
        for (CursorType cursorType : CursorType.values()) {
            Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(resourceSystem.getImageAsset(cursorType.getPath()), new Point(0, 0), cursorType.getName());
            cursorType.setCursor(c);
        }
    }

    public void setCursor(CursorType cursorType) {
        setCursor(cursorType.getCursor());
    }
}
