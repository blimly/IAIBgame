package inc.heterological.iaibgame.net.shared.packets;

import inc.heterological.iaibgame.desktop.ArenaButton;

import java.io.Serializable;

public class ArenaButtonChange implements Serializable {
    public ArenaButton.ARENA_BUTTON_STATE state;
}
