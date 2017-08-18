package fp;

import io.vavr.collection.Iterator;
import io.vavr.control.Try;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PocTry {

    private static Random random = new Random();

    private static String buildString(int i) {
        if (random.nextBoolean()) {
            return String.valueOf(i);
        } else {
            throw new RuntimeException("error for " + i);
        }
    }

    private static List<String> build() {
        return Iterator.range(0, 20)
                .map(i -> Try.of(() -> buildString(i)))
                .flatMap(t -> t.onFailure(x -> System.out.println(x.getMessage())))
                .toJavaList();
    }

    public static void main(String... args) {
        build().forEach(System.out::println);
    }

}
