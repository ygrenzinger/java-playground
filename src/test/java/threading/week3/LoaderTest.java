package threading.week3;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class LoaderTest {

    @Test
    public void shouldTerminateWhenStockIsEmpty() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<RandomObject> objects = Arrays.asList(new RandomObject(1,10), new RandomObject(1,10), new RandomObject(1,10));
        Stock stock = new Stock(objects);

        Cart cart = new Cart(10,100);
        Loader.loadStock(executorService, stock, cart);

        executorService.shutdown();
        boolean terminated = executorService.awaitTermination(1L, TimeUnit.SECONDS);
        assertThat(terminated).isTrue();
        assertThat(stock.isEmpty()).isTrue();
        assertThat(cart.getObjects()).containsOnlyElementsOf(objects);
    }

    @Test
    public void shouldWaitWhenStockIsNotEmptyButCartIsFull() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<RandomObject> objects = Arrays.asList(new RandomObject(1,10), new RandomObject(1,10), new RandomObject(1,10));
        Stock stock = new Stock(objects);

        Cart cart = new Cart(1,10);
        Loader.loadStock(executorService, stock, cart);

        executorService.shutdown();
        boolean terminated = executorService.awaitTermination(1L, TimeUnit.SECONDS);
        assertThat(terminated).isFalse();
        assertThat(stock.isEmpty()).isFalse();
        assertThat(cart.isLoading()).isFalse();
        assertThat(cart.getObjects()).hasSize(1);
    }

}