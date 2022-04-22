package com.example.robotricochet.windows;

import com.example.robotricochet.Application;
import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.systems.EntitySystem;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;

public abstract class Window extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    private long lastFrameTime;
    protected final EntitySystem entitySystem = new EntitySystem();
    protected final Timer windowTimer = new Timer((int) ((1 / Application.REFRESH_RATE) * 1000), this);
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    protected static final Color REFRESH_COLOR = new Color(0x00);

    public Window(boolean showFps) {
        super();
        setBackground(REFRESH_COLOR);
        setFocusable(true);
//        if (showFps)
//            entitySystem.add(new FpsCounter());
    }

    public Window() {
        this(false);
    }

    public void init() {
        for (Entity entity : entitySystem.getAllEntities()) {
            entity.init();
            entity.onResize(new Vector2(getWidth(), getHeight()));
            logger.info("Entity " + entity.getClass().getSimpleName() + " initialized");
        }
        lastFrameTime = System.nanoTime() / 1000000;
        windowTimer.start();
        addKeyListener(this);
        addMouseListener(this);
        addResizeListener();
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
        for (Entity entity : entitySystem.getAllEntities())
            entity.draw(g2d);
        lastFrameTime = System.nanoTime() / 1000000;    // in ms
    }

    /**
     * Need Repaint duration : 0.0034 ms
     * Repaint duration: 1.9141 ms
     *
     * @param delta delta time between two frames in ms
     */
    protected void checkRepaintJob(float delta) {
        boolean repaint = false;
        for (Entity entity : entitySystem.getAllEntities()) {
            entity.update(delta);
            if (entity.isDirty()) {
                repaint = true;
                entity.setDirty(false);
            }
        }
        if (repaint) repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        checkRepaintJob((System.nanoTime() / 1000000f) - lastFrameTime);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed");
//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_ESCAPE -> System.exit(0);
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key pressed");

    }

    /**
     * If the mouse is clicked we get all the entities that are under the mouse and call their onClick method
     * If the onClick method returns true, we stop the event propagation
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        logger.info("Clicked: " + e.getPoint());
        Vector2 pos = new Vector2(e.getPoint());
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
        for (Entity entity : entitySystem.getEntitiesAtScreenCoords(pos)) {
            if (entity.onHover(pos.translate(entity.getPosition().reverse())))
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    private void addResizeListener() {
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                logger.info("Window Resized");
                for (Entity entity : entitySystem.getAllEntities()) {
                    entity.onResize(new Vector2(e.getComponent().getWidth(), e.getComponent().getHeight()));
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
}
