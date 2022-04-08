package com.example.robotricochet;

import javax.swing.JFrame;
import java.awt.Color;
import java.util.logging.Logger;

public class Application extends JFrame {

    public static final float REFRESH_RATE = 60f;

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        Application app = new Application();
        app.setTitle("Robot Ricochet");
        app.setBounds(0, 0, 1366, 768);     // 16:9 aspect ratio
        app.setVisible(true);
        app.setOpacity(1);
        app.setResizable(false);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setBackground(Color.WHITE);
        app.setLocationRelativeTo(null);
        logger.info("Starting game");
    }
}
