package threading.week2.booking;

public class Group {

    private final int i;
    private final int nbOfPersons;
    private final boolean mustBeContiguous;

    public Group(int i, int nbOfPersons, boolean mustBeContiguous) {
        this.i = i;
        this.nbOfPersons = nbOfPersons;
        this.mustBeContiguous = mustBeContiguous;
    }

    public int nbOfPersons() {
        return nbOfPersons;
    }

    public boolean mustBeContiguous() {
        return mustBeContiguous;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group ");
        sb.append(i);
        sb.append(" with ");
        sb.append(nbOfPersons);
        if (!mustBeContiguous) {
            sb.append(" non ");
        }
        sb.append(" contiguous persons.");
        return sb.toString();
    }
}
