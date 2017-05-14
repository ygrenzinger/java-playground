package threading.week3;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UnloaderTest {

    @Test
    public void shouldUnloadCartAndTerminateBecauseStockIsEmpty() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Cart cart = Mockito.spy(new Cart(2,20));
        cart.loadingFinished();
        when(cart.isLoading()).thenReturn(false);

        Unloader.unloadStock(executorService, cart);

        executorService.shutdown();
        boolean isTerminated = executorService.awaitTermination(1L, TimeUnit.SECONDS);
        assertThat(isTerminated).isTrue();
        assertThat(cart.isEmpty()).isTrue();
    }

}