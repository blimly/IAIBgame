package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inc.heterological.iaibgame.desktop.managers.GameKeys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterSelect {

    int x;
    int y;
    public int currentIndex = 0;
    boolean up = true;
    boolean locked = false;
    List<Sprite> characterList = new ArrayList<>(Arrays.asList(Assets.player1_select, Assets.player2_select, Assets.player3_select, Assets.player4_select));

    public CharacterSelect(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void ChangeChar(int key) {
        if(key == GameKeys.ENTER) {
            locked = true;
        }
        if(!locked) {
            if (key == GameKeys.UP && currentIndex - 1 >= 0) {
                up = true;
                currentIndex -= 1;
            }
            if (key == GameKeys.DOWN && currentIndex + 1 <= 3) {
                up = false;
                currentIndex += 1;
            }
        }
    }

    public void drawCharSel(SpriteBatch batch) {
        batch.setColor(Color.GRAY);
        batch.draw(Assets.blank, x - 64, y - 48, 130, 98);
        batch.setColor(0.12f, 0.11f, 0.22f, 1f);
        batch.draw(Assets.blank, x  + 2 - 64, y+2 - 48, 126, 94);
        batch.setColor(Color.WHITE);
        if(up) {
            batch.draw(Assets.triangleUp, x - 50, y + 4, 36, 36);
        } else {
            batch.draw(Assets.triangleDown, x - 50, y - 39, 36, 36);
        }
        batch.setColor(Color.GRAY);
        batch.draw(Assets.triangleDown, x - 48, y - 37, 32, 32);
        batch.draw(Assets.triangleUp, x - 48, y + 5, 32, 32);
        batch.setColor(Color.WHITE);
        batch.draw(characterList.get(currentIndex), x - 40, y - 45, 128, 64);
        if (locked) {
            batch.draw(Assets.lock,x - 32, y - 32, 64, 64);
        }

    }
}
