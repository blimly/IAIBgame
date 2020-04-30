package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inc.heterological.iaibgame.desktop.managers.GameKeys;

public class SelectArrow {
    int x;
    public int y;
    int frstButtonY;
    int lstButtonY;
    int btnSeparation;
    public SelectArrow(int x, int y,int frstButtonY, int lstButtonY,int btnSeparation) {
        this.x = x;
        this.y = y;
        this.frstButtonY = frstButtonY;
        this.lstButtonY = lstButtonY;
        this.btnSeparation = btnSeparation;
    }

    public void moveArrow(int key) {
        if(key == GameKeys.UP && this.y + btnSeparation <= frstButtonY) {
            this.y += btnSeparation;
        }
        if(key == GameKeys.DOWN && this.y - btnSeparation >= lstButtonY) {
            this.y -= btnSeparation;
        }
    }

    public void drawArrow(SpriteBatch batch) {
        batch.draw(Assets.selectArrow, x, y + 18, 30, 30);
    }
}
