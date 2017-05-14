package interview.visitor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yannickgrenzinger on 12/12/2016.
 */
public class SoundStudio {

    public final Player player;

    public SoundStudio(Player player) {
        this.player = player;
    }

    public void playCollection(List<Playable> playables) {
        playables.forEach(p -> p.play(player));
    }

    public static void main(String... args) {
        CD cd = new CD();
        DVD dvd = new DVD();
        SoundStudio soundStudio = new SoundStudio(new THXPlayer());
        soundStudio.playCollection(Arrays.asList(cd, dvd));
    }
}
