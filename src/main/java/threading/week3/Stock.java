package threading.week3;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Stock {

    private final Queue<RandomObject> queue;

    public Stock() {
        this.queue = new ConcurrentLinkedDeque<>();
    }

    public Stock(Collection<RandomObject> objects) {
        this.queue = new ConcurrentLinkedDeque<>(objects);
    }

    public void addObject(RandomObject object) {
        queue.add(object);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public RandomObject poll() {
        return queue.poll();
    }

    public void remove(RandomObject object) {
        boolean remove = queue.remove(object);
        if (!remove) throw new RuntimeException(object + " is already removed.");
    }
}
