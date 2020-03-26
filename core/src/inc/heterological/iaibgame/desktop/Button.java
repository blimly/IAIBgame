package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Button {
    int buttonWidth;
    int buttonHeight;
    int cordX;
    int cordY;
    Sprite image;
    Rectangle rect;

    public Button(int buttonWidth, int buttonHeight, int cordX, int cordY, Sprite image) {
        rect = new Rectangle(cordX, cordY, buttonWidth, buttonHeight);
        this.buttonHeight = buttonHeight;
        this.buttonWidth = buttonWidth;
        this.cordY = cordY;
        this.cordX = cordX;
        this.image = image;
    }

    public void drawButton(SpriteBatch batch) {
        batch.draw(image, cordX, cordY, buttonWidth, buttonHeight);
    }

    public boolean clicked(Vector2 touch) {
        return rect.contains(touch);
    }
}
