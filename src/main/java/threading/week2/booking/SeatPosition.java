package threading.week2.booking;

import java.util.Objects;

public class SeatPosition {

    private final int row;
    private final int positionInRow;

    private SeatPosition(int row, int positionInRow) {
        this.row = row;
        this.positionInRow = positionInRow;
    }

    public static SeatPosition of(int row, int positionInRow) {
        return new SeatPosition(row, positionInRow);
    }

    public int getRow() {
        return row;
    }

    public int getPositionInRow() {
        return positionInRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatPosition that = (SeatPosition) o;
        return row == that.row &&
                positionInRow == that.positionInRow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, positionInRow);
    }
}
