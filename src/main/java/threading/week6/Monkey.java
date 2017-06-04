package threading.week6;

import java.util.Random;
import java.util.concurrent.Callable;

public class Monkey {
    private final static Random RANDOM = new Random();

    private final int i;
    private Position position;

    public Monkey(int i, Position position) {
        this.i = i;
        this.position = position;
    }

    private static void trace(String trace) {
        Thread.yield();
        System.out.println(trace);
    }

    public Callable<Monkey> goingThrough(Rope rope) {
        Monkey monkeyGoingThrough = this;
        return () -> {
            trace(monkeyGoingThrough + " waiting at " +monkeyGoingThrough.position + " for the rope.");
            rope.acquire(monkeyGoingThrough.position);
            trace(monkeyGoingThrough + " coming from " + monkeyGoingThrough.getPosition() + " take the rope.");
            RANDOM.setSeed(System.nanoTime());
            Position position = rope.goingThrough(monkeyGoingThrough, RANDOM.nextInt(1000));
            rope.release();
            trace(monkeyGoingThrough + "coming from " + monkeyGoingThrough.getPosition() + " has released the rope at " + position);
            return new Monkey(monkeyGoingThrough.i, position);
        };
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Monkey " + i;
    }
}
