package threading.week6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RopeWithMonitor implements Rope {

    private final int maxnbOfMonkeys;

    private final Lock lock = new ReentrantLock();
    private final Condition canUseRope = lock.newCondition();

    private Position comingFrom = null;
    private int nbOfMonkeysOnRope = 0;

    public RopeWithMonitor(int maxnbOfMonkeys) {
        this.maxnbOfMonkeys = maxnbOfMonkeys;
    }

    @Override
    public void acquire(Position positionToAcquire) throws InterruptedException {
        lock.lock();
        try {
            while (!canUseRope(positionToAcquire)) {
                canUseRope.await();
            }
            comingFrom = positionToAcquire;
            nbOfMonkeysOnRope++;
        } finally {
            lock.unlock();
        }
    }

    private boolean canUseRope(Position positionToAcquire) {
        return (comingFrom == positionToAcquire || comingFrom == null) && nbOfMonkeysOnRope < maxnbOfMonkeys;
    }

    @Override
    public void release() {
        lock.lock();
        try {
            nbOfMonkeysOnRope--;
            if (nbOfMonkeysOnRope == 0) comingFrom = null;
            canUseRope.signalAll();
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
