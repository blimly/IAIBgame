package inc.heterological.iaibgame.desktop.managers;

public class GameKeys {

    private static boolean[] keys;
    private static boolean[] pressedKeys;

    private static final int NUM_OF_KEYS = 8;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int KICK = 4;
    public static final int JAB = 5;
    public static final int ENTER = 6;
    public static final int BACKSPACE = 7;

    static {
        keys = new boolean[NUM_OF_KEYS];
        pressedKeys = new boolean[NUM_OF_KEYS];
    }

    public static void update() {
        for (int i = 0; i < NUM_OF_KEYS; i++) {
            pressedKeys[i] = keys[i];
        }
    }

    public static void setKeys(int key, boolean b) {
        keys[key] = b;
    }

    public static boolean isDown(int key) {
        return keys[key];
    }

    public static boolean isPressed(int key) {
        return keys[key] && !pressedKeys[key];
    }


}