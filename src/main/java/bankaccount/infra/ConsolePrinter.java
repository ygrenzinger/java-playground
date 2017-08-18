package bankaccount.infra;

import bankaccount.domain.Printer;

public class ConsolePrinter implements Printer {

    @Override
    public void printLn(String line) {
        System.out.println(line);
    }
}
