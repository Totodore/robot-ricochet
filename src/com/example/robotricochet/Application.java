package com.example.robotricochet;

import com.example.robotricochet.windows.GameWindow;
import com.example.robotricochet.windows.MenuWindow;
import com.example.robotricochet.windows.Window;

import javax.swing.JFrame;
import java.awt.Color;
import java.util.logging.Logger;

public class Application extends JFrame {

    public static final float REFRESH_RATE = 60f;

    private static final Logger logger = Logger.getLogger(Application.class.getName());
    private Window window;

    public static void main(String[] args) {
        Application app = new Application();
        app.setTitle("Robot Ricochet");
        app.setSize(1366, 768);     // 16:9 aspect ratio
        app.setOpacity(1);
        app.setResizable(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setBackground(Color.BLACK);

        // Fullscreen in release mode
        if (System.getenv("ENVIRONMENT") == null || System.getenv("ENVIRONMENT").startsWith("prod")) {
            app.setExtendedState(JFrame.MAXIMIZED_BOTH);
            app.setUndecorated(true);
        }

        app.setVisible(true);
        app.setLocationRelativeTo(null);
//        app.window = new MenuWindow((Void) -> app.window = new GameWindow());
        app.window = new GameWindow();
        // Window entity configuration
        app.window.setSize(app.getWidth(), app.getHeight());
        app.window.setPreferredSize(app.window.getSize());
        app.window.init();
        app.window.setVisible(true);
        app.add(app.window);

        logger.info("Starting game");
    }

}
