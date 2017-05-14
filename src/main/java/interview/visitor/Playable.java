package interview.visitor;

import java.nio.ByteBuffer;

/**
 * Created by yannickgrenzinger on 12/12/2016.
 */
public interface Playable {
    String getSound();
    void play(Player player);
}
