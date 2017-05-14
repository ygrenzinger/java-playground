package interview.visitor;

import java.nio.ByteBuffer;

/**
 * Created by yannickgrenzinger on 12/12/2016.
 */
public class CD implements Playable {
    @Override
    public String getSound() {
        return "CD";
    }

    @Override
    public void play(Player player) {
          player.play(this);
    }
}
