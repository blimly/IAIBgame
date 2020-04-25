package inc.heterological.iaibgame.net.shared.packets;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Play implements Serializable {

    public static class Players implements Serializable {
        public Map<Integer, PlayerEntity> players = new HashMap<>();
    }

    public static class Enemies implements Serializable {
        public Map<Integer, EnemyEntity> enemies = new HashMap<>();
    }

    public static class EntitiesToBeRemoved implements Serializable {
        public Set<Integer> players = new HashSet<>();
        public Set<Integer> enemies = new HashSet<>();
    }
}
