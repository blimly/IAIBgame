package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CharacterSelect {
    int x;
    int y;

    public CharacterSelect(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawCharSel(SpriteBatch batch) {
        batch.setColor(Color.BLACK);
        batch.draw(Assets.blank, 320, 240, 100, 100);
        batch.setColor(0.12f, 0.11f, 0.22f, 1f);
        batch.draw(Assets.blank, 321, 241, 98, 98);
        batch.setColor(Color.WHITE);
    }
}
