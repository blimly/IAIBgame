package inc.heterological.iaibgame.desktop.managers;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;

import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {

    public boolean keyDown(int key) {
        if (key == Input.Keys.W) {
            GameKeys.setKeys(GameKeys.UP, true);
        }
        if (key == Input.Keys.A) {
            GameKeys.setKeys(GameKeys.LEFT, true);
        }
        if (key == Input.Keys.S) {
            GameKeys.setKeys(GameKeys.DOWN, true);
        }
        if (key == Input.Keys.D) {
            GameKeys.setKeys(GameKeys.RIGHT, true);
        }
        if (key == Input.Keys.J) {
            GameKeys.setKeys(GameKeys.JAB, true);
        }
        if (key == Input.Keys.K) {
            GameKeys.setKeys(GameKeys.KICK, true);
        }
        if (key == Input.Keys.ENTER) {
            GameKeys.setKeys(GameKeys.ENTER, true);
        }
        if (key == Input.Keys.BACKSPACE) {
            GameKeys.setKeys(GameKeys.BACKSPACE, true);
        }
        return true;
    }

    public boolean keyUp(int key) {
        if (key == Input.Keys.W) {
            GameKeys.setKeys(GameKeys.UP, false);
        }
        if (key == Input.Keys.A) {
            GameKeys.setKeys(GameKeys.LEFT, false);
        }
        if (key == Input.Keys.S) {
            GameKeys.setKeys(GameKeys.DOWN, false);
        }
        if (key == Input.Keys.D) {
            GameKeys.setKeys(GameKeys.RIGHT, false);
        }
        if (key == Input.Keys.J) {
            GameKeys.setKeys(GameKeys.JAB, false);
        }
        if (key == Input.Keys.K) {
            GameKeys.setKeys(GameKeys.KICK, false);
        }
        if (key == Input.Keys.ENTER) {
            GameKeys.setKeys(GameKeys.ENTER, false);
        }
        if (key == Input.Keys.BACKSPACE) {
            GameKeys.setKeys(GameKeys.BACKSPACE, false);
        }
        return true;
    }
}
