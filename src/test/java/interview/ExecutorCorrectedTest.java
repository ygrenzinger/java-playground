package interview;

import interview.di.Executor;
import interview.di.Position;
import interview.di.Printer;
import interview.di.Reader;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

/**
 * Created by yannickgrenzinger on 05/12/2016.
 */
public class ExecutorCorrectedTest {
    @Test
    public void execute() throws Exception {
        Reader readerMock = Mockito.mock(Reader.class);
        Printer printerMock = Mockito.mock(Printer.class);
        Position pos = new Position(0, 0);

        Mockito.when(readerMock.read()).thenReturn(Collections.singletonList(pos));
        Executor executor = null;//new Executor(printerMock, readerMock);
        executor.execute();
        Mockito.verify(printerMock).print(pos);
    }

}