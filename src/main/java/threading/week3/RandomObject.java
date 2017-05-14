package threading.week3;

import java.util.Objects;
import java.util.Random;

public class RandomObject {
    private static final Random RANDOM = new Random();

    private final int weight;

    public RandomObject(int min, int max) {
        if (max <= min) throw new RuntimeException("Max should be greater than min.");

        RANDOM.setSeed(System.nanoTime());
        weight = RANDOM.nextInt(max - min) + min;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomObject object = (RandomObject) o;
        return weight == object.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight);
    }

    @Override
    public String toString() {
        return "RandomObject{" +
                "weight=" + weight +
                '}';
    }
}
