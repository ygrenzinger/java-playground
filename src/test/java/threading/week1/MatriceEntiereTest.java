package threading.week1;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

public class MatriceEntiereTest {

    public File open(String name) {
        URL url = getClass().getClassLoader().getResource("file/"+name+".txt");
        return new File(url.getPath());
    }

    @Test
    public void productMatrix() throws Exception {
        MatriceEntiere m1 = new MatriceEntiere(open(("m1")));
        MatriceEntiere m2 = new MatriceEntiere(open(("m2")));
        MatriceEntiere result = MatriceEntiere.productMatrix(m1, m2);
        Assertions.assertThat(result.getElem(0,0)).isEqualTo(5);
        Assertions.assertThat(result.getElem(1,0)).isEqualTo(8);
        Assertions.assertThat(result.getElem(0,1)).isEqualTo(8);
        Assertions.assertThat(result.getElem(1,1)).isEqualTo(13);
    }

}