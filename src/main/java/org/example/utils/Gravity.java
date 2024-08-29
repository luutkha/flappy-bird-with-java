package org.example.utils;

public class Gravity {
    public static final int JUMP_FORCE = 100;

    private Gravity() {
    }

    public static int getGravity(int timeOfDrop) {
        return 50 * timeOfDrop / 120;
    }

    public static int getGravityUp(int timeOfDrop) {
        return 100 * timeOfDrop / 120;
    }
}
