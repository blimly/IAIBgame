package inc.heterological.iaibgame.desktop.arena_objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inc.heterological.iaibgame.desktop.Assets;

import java.util.Set;

public class ArenaButton {

    public boolean isActivated = false;
    public int buttonX, buttonY;
    public int width = 480, height = 480;

    public ArenaButton(int x, int y) {
        buttonX = x;
        buttonY = y;
    }

    public void draw(SpriteBatch batch, int x, int y, Set<Boolean> players) {
        if (isActivated || all(players)) {
            isActivated = true;
            batch.draw(Assets.arenaButton[2], x, y, 128, 84);
            Assets.font.draw(batch, "ButtonState: " + 2, 5, 60);
        } else if (any(players)) {
            batch.draw(Assets.arenaButton[1], x, y, 128, 84);
            Assets.font.draw(batch, "ButtonState: " + 1, 5, 60);
        } else {
            batch.draw(Assets.arenaButton[0], x, y, 128, 84);
            Assets.font.draw(batch, "ButtonState: " + 0, 5, 60);
        }
    }

    public boolean playerOnButton(int playerX, int playerY) {
        return playerX > 190 && playerX < 270 &&
                playerY > 280 && playerY < 340;
    }

    private boolean all(Set<Boolean> boolset) {
        for (Boolean b : boolset) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private boolean any(Set<Boolean> boolset) {
        for (Boolean b : boolset) {
            if (b) {
                return true;
            }
        }
        return false;
    }

    public int getButtonX() {
        return buttonX;
    }

    public int getButtonY() {
        return buttonY;
    }

}