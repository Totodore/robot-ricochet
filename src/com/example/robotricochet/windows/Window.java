package com.example.robotricochet.windows;

import com.example.robotricochet.Application;
import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.managers.EntityManager;
import com.example.robotricochet.utils.Vector2i;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public abstract class Window extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    private final Class<? extends Entity>[] defaultEntities;
    protected final Timer windowTimer = new Timer(1 / Application.REFRESH_RATE * 1000, this);
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    protected static final Color REFRESH_COLOR = new Color(0x0E121D);

    protected static final EntityManager entityManager = new EntityManager();


    public Window(Class<? extends Entity>[] defaultEntities) {
        super();
        this.defaultEntities = defaultEntities;
        setBackground(REFRESH_COLOR);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
    }

    public void load() {
        for (Class<? extends Entity> entity : defaultEntities) {
            try {
                Method m = entity.getMethod("load");
                m.invoke(null);
            } catch (InvocationTargetException | IllegalAccessException e) {
                logger.info("Error while loading entity " + entity.getSimpleName());
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                logger.info("No load method for entity " + entity.getSimpleName());
            }
        }

    }

    public void init() {
        for (Class<? extends Entity> entity : defaultEntities) {
            try {
                Entity e = entity.getConstructor(Window.class).newInstance(this);
                System.out.println(e.getClass().getSimpleName());
                e.init();
                entityManager.add(e);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Need Repaint duration : 0.0034 ms
     * Repaint duration: 1.9141 ms
     */
    protected void checkRepaintJob() {
        boolean isRepaintNeeded = false;
        for (Entity entity : entityManager.getAllEntities()) {
            if (entity.isDirty()) {
                isRepaintNeeded = true;
                entity.setDirty(false);
            }
        }
        if (isRepaintNeeded) {
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkRepaintJob();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed");
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> System.exit(0);
        }
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
        Vector2i pos = new Vector2i(e.getPoint());
        for (Entity entity : entityManager.getEntitiesAtScreenCoords(pos)) {
            if (entity.onClick(pos.translate(entity.getPosition().reverse())))
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
        Vector2i pos = new Vector2i(e.getPoint());
        for (Entity entity : entityManager.getEntitiesAtScreenCoords(pos)) {
            if (entity.onHover(pos.translate(entity.getPosition().reverse())))
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
