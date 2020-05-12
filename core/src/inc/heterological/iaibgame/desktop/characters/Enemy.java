package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.Assets;

public class Enemy {

    public Vector2 position;

    public Enemy(Vector2 position) {
        this.position = position;
    }

    public TextureRegion getCurrentFrame(float stateTime, ENEMY_TYPE type) {
        switch (type) {

            case ZOMBIE:
                return Assets.zombie.getKeyFrame(stateTime, true);
            case BOB_RUNNING:
                return Assets.bob_run.getKeyFrame(stateTime, true);
            case BOB_FLEEING:
                return Assets.bob_flee.getKeyFrame(stateTime, true);
            case HEALER_WALKING:
                return Assets.healer_walking.getKeyFrame(stateTime, true);
            case HEALER_HEALING:
                return Assets.healer_healing.getKeyFrame(stateTime, true);
        }
        return null;
    }

    public TextureRegion getHealingParticles(float statetime) {
        return Assets.healing.getKeyFrame(statetime);
    }

    public enum ENEMY_TYPE {ZOMBIE, BOB_RUNNING, BOB_FLEEING, HEALER_WALKING, HEALER_HEALING }

}
