package threading.week3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Cart {
    private static final Logger LOGGER = LogManager.getLogger();

    private static int cpt = 0;

    private final ReentrantLock l = new ReentrantLock();
    private final Condition loading = l.newCondition();
    private final Condition unloading = l.newCondition();

    private final int maxWeight;
    private final int maxSize;
    private boolean isLoading = true;
    private boolean loadingFinished = false;

    private Queue<RandomObject> objects = new ArrayDeque<>();

    private final int nb;

    public Cart(int maxSize, int maxWeight) {
        l.lock();
        this.maxWeight = maxWeight;
        this.maxSize = maxSize;
        this.nb = cpt++;
        l.unlock();
    }

    public Collection<RandomObject> getObjects() {
        return objects;
    }

    public boolean loadObject(RandomObject object) {
        l.lock();
        try {
            waitForLoadingPossible();

            if (objects.size() < maxSize && (currentWeight() + object.getWeight()) <= maxWeight) {
                objects.add(object);
                return true;
            } else {
                isLoading = false;
                unloading.signalAll();
                return false;
            }
        } catch (InterruptedException e) {
            System.out.println("Loading is interrupted.");
            return false;
        } finally {
            l.unlock();
        }
    }

    private int currentWeight() {
        return objects.stream().mapToInt(RandomObject::getWeight).sum();
    }

    private void waitForLoadingPossible() throws InterruptedException {
        while (!isLoading()) {
            System.out.println("Loading is waiting");
            loading.await();
        }
    }

    public RandomObject unloadObject() {
        l.lock();
        try {
            while (isLoading) {
                System.out.println("Unload is waiting");
                try {
                    unloading.await();
                } catch (InterruptedException e) {
                    System.out.println("Loader is interrupted.");
                }
            }
            RandomObject object = objects.poll();
            if (objects.isEmpty()) {
                isLoading = true;
                loading.signalAll();
            }
            return object;
        } finally {
            l.unlock();
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isEmpty() {
        return objects.isEmpty();
    }

    public void loadingFinished() {
        l.lock();
        try {
            loadingFinished = true;
            isLoading = false;
            unloading.signalAll();
        } finally {
            l.unlock();
        }
    }

    public boolean isLoadingFinished() {
        return loadingFinished;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "nb=" + nb +
                '}';
    }
}
