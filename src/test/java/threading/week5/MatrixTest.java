package threading.week5;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.net.URL;

public class MatrixTest {

    public File open(String name) {
        URL url = getClass().getClassLoader().getResource("file/"+name+".txt");
        return new File(url.getPath());
    }

    @Test
    public void productMatrix() throws Exception {
        Matrix m1 = Matrix.of(open(("m1")));
        Matrix m2 = Matrix.of(open(("m2")));
        Matrix result = Matrix.product(m1, m2);
        Assertions.assertThat(result.at(0,0)).isEqualTo(5);
        Assertions.assertThat(result.at(1,0)).isEqualTo(8);
        Assertions.assertThat(result.at(0,1)).isEqualTo(8);
        Assertions.assertThat(result.at(1,1)).isEqualTo(13);
    }

}