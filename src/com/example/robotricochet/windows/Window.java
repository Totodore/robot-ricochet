package com.example.robotricochet.windows;

import com.example.robotricochet.Application;
import com.example.robotricochet.components.Position;
import com.example.robotricochet.entities.Entity;

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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class Window extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    private final List<Entity> entities = new ArrayList<>();
    protected final Timer windowTimer = new Timer((int) (1 / Application.REFRESH_RATE * 1000), this);
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    protected static final Color REFRESH_COLOR = new Color(0x0E121D);

    public Window() {
        super();
        setBackground(REFRESH_COLOR);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
    }

    public void init() {
        for (Entity entity : entities) {
            System.out.println(entity.getClass().getSimpleName());
        }
    }

    /**
     * Need Repaint duration : 0.0034 ms
     * Repaint duration: 1.9141 ms
     */
    protected void checkRepaintJob() {
        boolean isRepaintNeeded = false;
        for (Entity entity : entities) {
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
        Position pos = new Position(e.getPoint());
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
        Position pos = new Position(e.getPoint());
//        for (Entity entity : entityManager.getEntitiesAtScreenCoords(pos)) {
//            if (entity.onHover(pos.translate(entity.getPosition().reverse())))
//                break;
//        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
