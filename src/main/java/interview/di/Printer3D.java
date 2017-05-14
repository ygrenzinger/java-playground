package interview.di;

/**
 * Created by yannickgrenzinger on 02/12/2016.
 */
public class Printer3D implements Printer {

    @Override
    public void print(Position p) {
        System.out.println(p);
    }
}
