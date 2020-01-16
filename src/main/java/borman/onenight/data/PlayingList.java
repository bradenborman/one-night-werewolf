package borman.onenight.data;

import borman.onenight.models.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayingList {

   public static List<Player> allPlaying = new ArrayList<>();

    public static List<Player> getAllPlaying() {
        return allPlaying;
    }

    public static void setAllPlaying(List<Player> allPlaying) {
        PlayingList.allPlaying = allPlaying;
    }
}
