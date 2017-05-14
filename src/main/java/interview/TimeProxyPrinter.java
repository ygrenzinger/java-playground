package interview;

import interview.di.Position;
import interview.di.Printer;

import static java.lang.System.currentTimeMillis;

/**
 * Created by yannickgrenzinger on 20/12/2016.
 */
public class TimeProxyPrinter implements Printer {
    private final Printer printer;

    public TimeProxyPrinter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void print(Position position) {
        long startTime = currentTimeMillis();
        printer.print(position);
        System.out.println("Duration: " + (currentTimeMillis() - startTime));
    }
}
