package borman.onenight.data;

import java.util.ArrayList;
import java.util.List;

public class PlayingList {

   public static List<String> allPlaying = new ArrayList<>();

    public static List<String> getAllPlaying() {
        return allPlaying;
    }

    public static void setAllPlaying(List<String> allPlaying) {
        PlayingList.allPlaying = allPlaying;
    }
}
