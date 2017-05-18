package threading.week2.booking;

import org.junit.Test;
import threading.week2.booking.ConcertHall;
import threading.week2.booking.SeatPosition;

import static org.assertj.core.api.Assertions.assertThat;

public class ConcertHallTest {

    @Test
    public void shouldReserveContiguousIfPossible() {
        ConcertHall concertHall = new ConcertHall(1, 10);
        concertHall.reserveSeat(SeatPosition.of(0, 1));
        concertHall.reserveSeat(SeatPosition.of(0, 2));
        concertHall.reserveSeat(SeatPosition.of(0, 4));
        concertHall.reserveSeat(SeatPosition.of(0, 9));

        concertHall.reserveContiguousSeat(4);
        assertThat(concertHall.isFree(SeatPosition.of(0,0))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(0,1))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(0,2))).isTrue();
        assertThat(concertHall.isFree(SeatPosition.of(0,3))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(0,4))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(0,5))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(0,6))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(0,7))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(0,8))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(0,9))).isTrue();
    }

    @Test
    public void shouldDoNothingIfNotEnoughContiguousSeatSeats() {
        ConcertHall concertHall = buildConcertHallWithNotEnoughContiguousSeat();

        concertHall.reserveContiguousSeat(4);
        assertThat(concertHall).isEqualTo(buildConcertHallWithNotEnoughContiguousSeat());
    }

    private ConcertHall buildConcertHallWithNotEnoughContiguousSeat() {
        ConcertHall concertHall = new ConcertHall(1, 10);
        concertHall.reserveSeat(SeatPosition.of(0, 1));
        concertHall.reserveSeat(SeatPosition.of(0, 4));
        concertHall.reserveSeat(SeatPosition.of(0, 7));
        concertHall.reserveSeat(SeatPosition.of(0, 9));
        return concertHall;
    }

    @Test
    public void shouldReserveNonContiguousSeats() {
        ConcertHall concertHall = new ConcertHall(3, 3);
        concertHall.reserveSeat(5);

        assertThat(concertHall.isOccupied(SeatPosition.of(0,0))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(0,1))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(0,2))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(1,0))).isTrue();
        assertThat(concertHall.isOccupied(SeatPosition.of(1,1))).isTrue();
        assertThat(concertHall.isFree(SeatPosition.of(1,2))).isTrue();
        assertThat(concertHall.isFree(SeatPosition.of(2,0))).isTrue();
        assertThat(concertHall.isFree(SeatPosition.of(2,1))).isTrue();
        assertThat(concertHall.isFree(SeatPosition.of(2,2))).isTrue();
    }

    @Test
    public void shouldDoNothingIfNotEnoughSeat() {
        ConcertHall concertHall = buildConcertHallWithNotEnoughSeat();
        concertHall.reserveSeat(3);
        assertThat(concertHall).isEqualTo(buildConcertHallWithNotEnoughSeat());
    }

    private ConcertHall buildConcertHallWithNotEnoughSeat() {
        ConcertHall concertHall = new ConcertHall(2, 2);
        concertHall.reserveSeat(SeatPosition.of(0, 1));
        concertHall.reserveSeat(SeatPosition.of(1, 0));
        return concertHall;
    }

}