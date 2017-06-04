package threading.week6;

import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RopeWithSemaphore implements Rope {

    private final int maxNbOfMonkeys;
    private final Map<Position, Semaphore> monkeysOnRope;

    private Position comingFrom;

    private Lock lock = new ReentrantLock();

    public RopeWithSemaphore(int maxNbOfMonkeys) {
        this.maxNbOfMonkeys = maxNbOfMonkeys;
        this.monkeysOnRope = Stream.of(Position.values()).collect(Collectors.toMap(
            Function.identity(), p -> new Semaphore(maxNbOfMonkeys)
        ));
    }

    @Override
    public void acquire(Position positionToAcquire) throws InterruptedException {
        lock.lock();
        try {
            if (comingFrom == null) {
                monkeysOnRope.get(positionToAcquire.opposite()).acquire(maxNbOfMonkeys);
                comingFrom = positionToAcquire;
            }
        } finally {
            lock.unlock();
        }
        monkeysOnRope.get(positionToAcquire).acquire();
    }

    @Override
    public void release() throws InterruptedException {
        lock.lock();
        try {
            monkeysOnRope.get(comingFrom).release();
            if (monkeysOnRope.get(comingFrom).availablePermits() == 4 && monkeysOnRope.get(comingFrom.opposite()).hasQueuedThreads()) {
                monkeysOnRope.get(comingFrom.opposite()).release(maxNbOfMonkeys);
                monkeysOnRope.get(comingFrom).acquire(maxNbOfMonkeys);
                comingFrom = comingFrom.opposite();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Position goingThrough(Monkey monkey, int delay) throws InterruptedException {
        Thread.sleep(delay);
        return monkey.getPosition().opposite();
    }
}
