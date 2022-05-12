package com.example.robotricochet;

public class Utils {
    /**
     * Run a code in a given amount of time.
     *
     * @param runnable The code to run.
     * @param delay    The amount of time to wait in ms.
     */
    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}
