package interview.di;

/**
 * Created by yannickgrenzinger on 02/12/2016.
 */
public class CorrectedExecutor {

    private final Printer p;
    private final Reader r;

    public CorrectedExecutor(Printer p, Reader r) {
        this.p = p;
        this.r = r;
    }

    public void execute() {
        r.read().forEach(p::print);
    }

}
