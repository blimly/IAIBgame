package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.Assets;

public class Enemy {

    public Vector2 position;

    public Enemy(Vector2 position) {
        this.position = position;
    }

    public TextureRegion getCurrentFrame(float stateTime) {
        return Assets.enemy1.getKeyFrame(stateTime, true);
    }

    public enum ENEMY_TYPE {ZOMBIE, BOB, HEALER}

}
