package threading.week3;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;

public class Loader implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();


    private final Stock stock;
    private final Cart cart;

    private Loader(Stock stock, Cart cart) {
        this.stock = stock;
        this.cart = cart;
    }

    public static void loadStock(ExecutorService executorService, Stock stock, Cart cart) {
        executorService.execute(new Loader(stock, cart));
    }

    @Override
    public void run() {
        LOGGER.debug("Loader job starting.");
        while (!stock.isEmpty()) {
            RandomObject object = stock.poll();
            boolean loaded = cart.loadObject(object);
            if (loaded) {
                LOGGER.debug(object + " is loaded into " +cart);
            } else {
                stock.addObject(object);
            }
        }
        cart.loadingFinished();
        LOGGER.debug("Loader job done.");
    }
}
