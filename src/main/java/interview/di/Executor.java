package interview.di;

import interview.XMLFileReader;

public class Executor {

    private final Printer3D printer = new Printer3D();
    private final XMLFileReader reader = new XMLFileReader();

    public void execute() {
        for (Position p : reader.read()) {
            printer.print(p);
        }
    }

}
