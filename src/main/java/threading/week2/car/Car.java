package threading.week2.car;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Car {

    private final static Random RANDOM = new Random();

    private enum Command {
        OPEN, CLOSE
    }

    public enum State {
        OPENED, CLOSED
    }

    private State nextState(State state, Command command) {
        RANDOM.setSeed(System.nanoTime());
        try {
            int timeout = RANDOM.nextInt(100);
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return command == Command.OPEN ? State.OPENED : State.CLOSED;
    }

    private boolean willChangeState(State state, Command command) {
        return (state == State.OPENED && command == Command.CLOSE)
                || (state == State.CLOSED && command == Command.OPEN);
    }

    private class Motor implements Runnable {
        private final Car car;
        private final int i;
        private final Command command;

        private Motor(Car car, int i, Command command) {
            this.car = car;
            this.i = i;
            this.command = command;
        }


        @Override
        public void run() {
            car.glasses[i] = nextState(car.glasses[i], command);
        }
    }

    private final State[] glasses;
    private State convertible;

    public Car() {
        this.glasses = new State[4];
        for (int i = 0; i < 4; i++) {
            glasses[i] = State.CLOSED;
        }
        this.convertible = State.CLOSED;
    }

    private void reverseConvertibleState() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Command todo = convertible == State.CLOSED ? Command.OPEN : Command.CLOSE;
        IntStream.range(0, 4).forEach(i -> executorService.execute(new Motor(this, i, todo)));
        executorService.shutdown();
        try {
            executorService.awaitTermination(1L, TimeUnit.SECONDS);
            CompletableFuture.runAsync(() -> convertible = nextState(convertible, todo)).join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void openConvertible() {
        if (convertible == State.CLOSED) reverseConvertibleState();
    }

    public void closeConvertible() {
        if (convertible == State.OPENED) reverseConvertibleState();
    }

    public void applyStateOnGlass(int i, State state) {
        if (state == glasses[i]) return;
        Motor motor;
        if (state == State.OPENED) {
            motor = new Motor(this, i, Command.OPEN);
        } else {
            motor = new Motor(this, i, Command.CLOSE);
        }
        CompletableFuture.runAsync(motor).join();
    }

    public State glassState(int i) {
        return glasses[i];
    }

    public State convertibleState() {
        return convertible;
    }


}
