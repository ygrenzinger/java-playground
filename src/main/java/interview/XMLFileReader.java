package interview;

import interview.di.Position;
import interview.di.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannickgrenzinger on 02/12/2016.
 */
public class XMLFileReader implements Reader {
    @Override
    public List<Position> read() {
        return new ArrayList<>();
    }
}
