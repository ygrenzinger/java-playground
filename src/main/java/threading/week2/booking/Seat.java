package threading.week2.booking;

import java.util.Objects;

public class Seat {

    private boolean isOccupied = false;

    public boolean isOccupied() {
        return isOccupied;
    }


    public void reserve() {
        isOccupied = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return isOccupied == seat.isOccupied;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isOccupied);
    }

    @Override
    public String toString() {
        if (isOccupied) {
            return " \u25A0 ";
        }   else {
            return " \u25A1 ";
        }
    }
}
