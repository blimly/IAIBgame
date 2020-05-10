package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ArenaButton {

    public static int buttonX, buttonY;
    public static int width = 128, height = 84;
    public ARENA_BUTTON_STATE state;
    public ArenaButton(int x, int y, ARENA_BUTTON_STATE state) {
        this.buttonX = x;
        this.buttonY = y;
        this.state = ARENA_BUTTON_STATE.UP;
    }

    public void draw(SpriteBatch batch, ARENA_BUTTON_STATE state) {
        switch (state) {
            case READY:
                batch.draw(Assets.arenaButton[1], buttonX, buttonY, width, height);
                break;
            case DOWN:
                batch.draw(Assets.arenaButton[2], buttonX, buttonY, width, height);
                break;
            default:  // UP
                batch.draw(Assets.arenaButton[0], buttonX, buttonY, width, height);
                break;
        }
    }

    public static boolean playerOnButton(Vector2 playerPos) {
        return playerPos.x > buttonX && playerPos.x < buttonX + width &&
                playerPos.y > buttonY && playerPos.y < buttonY + height;
    }

    public enum ARENA_BUTTON_STATE {UP, READY, DOWN}
}