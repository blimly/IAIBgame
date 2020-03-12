package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Button {
    private Skin skin;
    TextButton tempButton;

    public Button(Stage stage, int buttonWidth, int buttonHeight, int cordX, int cordY, String text, final Screen screen, final Game game, Color color) {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        final TextButton button = new TextButton(text, skin, "default");
        button.setWidth(buttonWidth);
        button.setHeight(buttonHeight);
        button.setColor(color);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(screen);
            }
        });
        stage.addActor(button);
        button.setPosition(cordX, cordY);
        this.tempButton = button;
    }

    public void setButtonPosition(int x, int y) {
        tempButton.setPosition(x, y);
    }
}
