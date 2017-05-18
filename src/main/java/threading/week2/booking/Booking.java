package threading.week2.booking;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Booking {

    private static final Object LOCK = new Object();

    private static class BookSeats implements Runnable {

        private final ConcertHall concertHall;
        private final Group group;

        private BookSeats(ConcertHall concertHall, Group group) {
            this.concertHall = concertHall;
            this.group = group;
        }

        @Override
        public void run() {
            boolean success;
            if (group.mustBeContiguous()) {
                success = concertHall.reserveContiguousSeat(group.nbOfPersons());
            } else {
                success = concertHall.reserveSeat(group.nbOfPersons());
            }
            synchronized (LOCK) {
                StringBuilder sb = new StringBuilder();
                sb.append(group);
                if (success) {
                    sb.append(" has successfully reserved seats.");
                } else {
                    sb.append(" was unable to reserve seats.");
                }
                System.out.println(sb.toString());
                System.out.println("");
                System.out.println(concertHall);
                System.out.println("");
            }
        }
    }

    public static void main(String... args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int nbOfRows = 10;
        int nbOfSeatsByRow = 10;

        ConcertHall concertHall = new ConcertHall(nbOfRows, nbOfSeatsByRow);

        Random random = new Random();

        IntStream.range(0, 20).mapToObj(i -> {
            random.setSeed(System.nanoTime());
            int nbOfPersons = random.nextInt(9) + 1;
            boolean mustBeContiguous = i % 2 == 0;
            return new BookSeats(concertHall, new Group(i, nbOfPersons, mustBeContiguous));
        }).forEach(executorService::execute);

        executorService.shutdown();
    }

}
