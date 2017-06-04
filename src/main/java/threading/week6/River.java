package threading.week6;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class River {

    private static final Random RANDOM = new Random();

    private static final Position randomPosition() {
        RANDOM.setSeed(System.nanoTime());
        if (RANDOM.nextInt(2) == 0) return Position.LEFT;
        return Position.RIGHT;
    }

    public static void main(String... args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //Rope rope = new RopeWithMonitor(1);
        Rope rope = new RopeWithSemaphore(4);
        List<Callable<Monkey>> monkeysGoingThrough = IntStream.range(0, 10)
                .mapToObj(i -> {
                    Monkey monkey = new Monkey(i + 1, randomPosition());
                    System.out.println(monkey + " is at " + monkey.getPosition());
                    return monkey;
                })
                .map(monkey -> monkey.goingThrough(rope))
                .collect(Collectors.toList());
        List<Future<Monkey>> monkeyFutures = executor.invokeAll(monkeysGoingThrough);
        executor.shutdown();
        System.out.println("All monkeys has arrived");
        monkeyFutures.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).forEach(monkey -> System.out.println(monkey + " arrived at " + monkey.getPosition()));

    }
}
