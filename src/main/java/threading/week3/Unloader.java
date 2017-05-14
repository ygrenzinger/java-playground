package threading.week3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;

public class Unloader implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Cart cart;

    private Unloader(Cart cart) {
        this.cart = cart;
    }

    public static void unloadStock(ExecutorService executorService, Cart cart) {
        executorService.execute(new Unloader(cart));
    }

    @Override
    public void run() {
        LOGGER.debug("Unloader job starting.");
        while (!(cart.isLoadingFinished() && cart.isEmpty())) {
            RandomObject object = cart.unloadObject();
            LOGGER.debug("{} is unloaded from {}", object, cart);
        }
        LOGGER.debug("Unloader job done.");
    }
}
