package threading.week2.booking;

import org.paumard.streams.StreamsUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConcertHall {

    private final Map<SeatPosition, Seat> seats;
    private final int nbOfRows;
    private final int nbSeatsByRow;

    public ConcertHall(int nbOfRows, int nbSeatsByRow) {
        this.nbOfRows = nbOfRows;
        this.nbSeatsByRow = nbSeatsByRow;
        seats = new HashMap<>(nbOfRows * nbSeatsByRow);
        for (int i = 0; i < nbOfRows; i++) {
            for (int j = 0; j < nbSeatsByRow; j++) {
                seats.put(SeatPosition.of(i, j), new Seat());
            }
        }
    }

    private int numberOfFreeSeat() {
        return seats.values().stream().mapToInt(seat -> seat.isOccupied() ? 0 : 1).sum();
    }

    private boolean hasEnoughCapacity(int n) {
        return n < numberOfFreeSeat();
    }

    private Optional<SeatPosition> hasEnoughContiguousSeatsAtRow(int nbOfPersons, int row) {
        Stream<SeatPosition> rowSeats = IntStream.range(0, nbSeatsByRow)
                .mapToObj(i -> SeatPosition.of(row, i));
        return StreamsUtils.group(rowSeats, this::isFree, this::isOccupied)
                .map(s -> s.filter(this::isFree))
                .map(s -> s.collect(Collectors.toList()))
                .filter(s -> s.size() >= nbOfPersons)
                .findFirst()
                .map(seatPositions -> seatPositions.get(0));
    }

    private boolean reserveContiguousSeat(int nbOfPersons, int row) {
        Optional<SeatPosition> option = hasEnoughContiguousSeatsAtRow(nbOfPersons, row);
        if (!option.isPresent()) return false;

        SeatPosition seatPosition = option.get();
        int inRow = seatPosition.getPositionInRow();
        IntStream.range(inRow, inRow + nbOfPersons).forEach(p -> reserveSeat(SeatPosition.of(row, p)));
        return true;
    }

    public synchronized boolean reserveSeat(SeatPosition seatPosition) {
        if (seatPosition.getRow() < 0 || seatPosition.getRow() >= nbOfRows || seatPosition.getPositionInRow() < 0 || seatPosition.getPositionInRow() >= nbSeatsByRow)
            throw new RuntimeException("Seat position doesn't exist ");
        seats.get(SeatPosition.of(seatPosition.getRow(), seatPosition.getPositionInRow())).reserve();
        return true;
    }

    public boolean isOccupied(SeatPosition seatPosition) {
        return seats.get(seatPosition).isOccupied();
    }

    public boolean isFree(SeatPosition seatPosition) {
        return !seats.get(seatPosition).isOccupied();
    }

    private boolean reserveSeat(int nbPersons, int row) {
         if (nbPersons > 0) {
             int nbOfReservedSeats = (int)IntStream.range(0, nbSeatsByRow)
                     .mapToObj(i -> SeatPosition.of(row, i))
                     .filter(this::isFree)
                     .limit(nbPersons)
                     .map(this::reserveSeat)
                     .count();
             return reserveSeat(nbPersons - nbOfReservedSeats, row+1);
         }
         return true;
    }

    public synchronized boolean reserveSeat(int nbOfPersons) {
        boolean enoughCapacity = hasEnoughCapacity(nbOfPersons);
        return enoughCapacity && reserveSeat(nbOfPersons, 0);
    }

    public synchronized boolean reserveContiguousSeat(int nbOfPersons) {
        return IntStream.range(0, nbOfRows).anyMatch(row -> reserveContiguousSeat(nbOfPersons, row));
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nbOfRows; i++) {
            for (int j = 0; j < nbSeatsByRow; j++) {
                sb.append(seats.get(SeatPosition.of(i,j)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConcertHall that = (ConcertHall) o;
        return nbOfRows == that.nbOfRows &&
                nbSeatsByRow == that.nbSeatsByRow &&
                Objects.equals(seats, that.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seats, nbOfRows, nbSeatsByRow);
    }
}
