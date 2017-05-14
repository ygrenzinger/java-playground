package threading.week3;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quicktheories.quicktheories.QuickTheory.qt;
import static org.quicktheories.quicktheories.generators.SourceDSL.integers;

public class CartTest {

    @Test
    public void shouldFullyLoadAndUnload() {
        testLoadAndUnloadStock(2, 1, 10);
    }


    @Test
    public void shouldFullyLoadAndUnloadProperty() {
        qt().withShrinkCycles(10).forAll(
                integers().between(2, 100),
                integers().between(1, 10),
                integers().between(1, 100)
        ).checkAssert((maxWeight, maxCartSize, maxNbOfObjects) -> {
            System.out.println(String.format("Testing with maxWeight %s, maxCartSize %s, maxNbOfObjects %s ", maxWeight, maxCartSize, maxNbOfObjects));
            testLoadAndUnloadStock(maxWeight, maxCartSize, maxNbOfObjects);
        });
    }

    private void testLoadAndUnloadStock(Integer maxWeight, Integer maxCartSize, Integer maxNbOfObjects) {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Stock stock = generateStock(maxWeight, maxNbOfObjects);
        Cart cart = new Cart(maxCartSize, maxWeight);
        //when
        Loader.loadStock(executorService, stock, cart);
        Unloader.unloadStock(executorService, cart);
        //then
        executorService.shutdown();
        try {
            boolean isTerminated = executorService.awaitTermination(2L, TimeUnit.SECONDS);
            System.out.println(String.format("isTerminated %b, empty cart %b, empty stock %b",
                    isTerminated, cart.isEmpty(), stock.isEmpty()));
            assertThat(isTerminated).isTrue();
            assertThat(cart.isEmpty()).isTrue();
            assertThat(stock.isEmpty()).isTrue();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Stock generateStock(Integer maxWeight, Integer maxNbOfObjects) {
        List<RandomObject> randomObjects = new ArrayList<>();
        for (int i = 0; i < maxNbOfObjects; i++) {
            randomObjects.add(new RandomObject(1, maxWeight));
        }
        return new Stock(randomObjects);
    }


}
