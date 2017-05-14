package interview.visitor;

/**
 * Created by yannickgrenzinger on 12/12/2016.
 */
public class THXPlayer implements Player {
    @Override
    public void play(CD cd) {
        cd.getSound();   //Do something with this
    }

    @Override
    public void play(DVD dvd) {
        dvd.getSound();   //Do something with this
    }

    @Override
    public void play(BluRay bluRay) {
        
    }

}
