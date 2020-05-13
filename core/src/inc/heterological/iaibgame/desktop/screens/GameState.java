package inc.heterological.iaibgame.desktop.screens;


import inc.heterological.iaibgame.desktop.managers.GameStateManager;

public abstract class GameState {

    //Every gamestate needs a reference to the gamestatemanager(GSM)
    //We want to be able to switch between gamestates with the GSM

    protected GameStateManager stateManager;

    protected GameState(GameStateManager gsm) {
        this.stateManager = gsm;
    }

    public abstract void init();

    public abstract void update(float dt);

    public abstract void draw();

    public abstract void handleInput();

    public abstract void dispose();
}
