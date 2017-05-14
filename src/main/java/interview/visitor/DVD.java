package interview.visitor;

/**
 * Created by yannickgrenzinger on 12/12/2016.
 */
public class DVD implements Playable {
    @Override
    public String getSound() {
        return "DVD";
    }

    @Override
    public void play(Player player) {
        player.play(this);
    }
}
