package com.example.robotricochet;

import com.example.robotricochet.systems.ResourceSystem;
import com.example.robotricochet.windows.GameWindow;
import com.example.robotricochet.windows.MenuWindow;
import com.example.robotricochet.windows.Window;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Logger;

public class Application extends JFrame {

    public static final float REFRESH_RATE = 60f;

    private static final Logger logger = Logger.getLogger(Application.class.getName());
    private final ResourceSystem resourceSystem = new ResourceSystem();
    private Window window;

    public static void main(String[] args) {
        Application app = new Application();
        app.setTitle("Robot Ricochet");
        app.setSize(1366, 768);     // 16:9 aspect ratio
        app.setOpacity(1);
        app.setResizable(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setBackground(Color.BLACK);

        app.setIconImage(app.resourceSystem.getImageAsset("robots/yellow-robot.png"));
        // Fullscreen in release mode
        if (System.getenv("ENVIRONMENT") == null || System.getenv("ENVIRONMENT").startsWith("prod")) {
            app.setExtendedState(JFrame.MAXIMIZED_BOTH);
            app.setUndecorated(true);
        }

        app.setVisible(true);
        app.setLocationRelativeTo(null);
        app.playMusic();
        app.showMenu();
        // Window entity configuration
        app.window.setSize(app.getWidth(), app.getHeight());
        app.window.setPreferredSize(app.window.getSize());
        app.window.init();
        app.window.setVisible(true);
        app.add(app.window);
        System.out.println("Music started");

        logger.info("Starting game");
    }

    public void showGame() {
        if (window != null)
            remove(window);
        window = new GameWindow((Void) -> showMenu());
        window.setSize(getWidth(), getHeight());
        window.setPreferredSize(window.getSize());
        window.init();
        window.setVisible(true);
        add(window);
    }

    public void showMenu() {
        if (window != null)
            remove(window);
        window = new MenuWindow((Void) -> showGame());
        window.setSize(getWidth(), getHeight());
        window.setPreferredSize(window.getSize());
        window.init();
        window.setVisible(true);
        add(window);
    }

    public void playMusic() {
        try {
            Clip clip = AudioSystem.getClip();
            InputStream audioSrc = Objects.requireNonNull(getClass().getResourceAsStream("/sounds/bossa.wav"));
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
