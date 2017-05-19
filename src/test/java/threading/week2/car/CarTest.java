package threading.week2.car;

import org.junit.Test;
import org.quicktheories.quicktheories.api.QuadConsumer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quicktheories.quicktheories.QuickTheory.qt;
import static org.quicktheories.quicktheories.generators.SourceDSL.booleans;

public class CarTest {

    @Test
    public void everythingShouldBeOpenedAfterOpeningConvertible() throws Exception {
        QuadConsumer<Boolean, Boolean, Boolean, Boolean> generateAndAssertFunction = (a, b, c, d) -> {
            Car car = generateCarState(a, b, c, d);
            car.openConvertible();
            assertThatEverythingIsInState(car, Car.State.OPENED);
        };
        qt().withExamples(20)
                .forAll(booleans().all(), booleans().all(), booleans().all(), booleans().all())
                .checkAssert(generateAndAssertFunction);
    }

    @Test
    public void everythingShouldBeClosednAfterClosingConvertible() throws Exception {
        QuadConsumer<Boolean, Boolean, Boolean, Boolean> generateAndAssertFunction = (a, b, c, d) -> {
            Car car = generateCarState(a, b, c, d);
            car.setConvertibleState(Car.State.OPENED);
            car.closeConvertible();
            assertThatEverythingIsInState(car, Car.State.CLOSED);
        };
        qt().withExamples(20)
                .forAll(booleans().all(), booleans().all(), booleans().all(), booleans().all())
                .checkAssert(generateAndAssertFunction);
    }

    private void assertThatEverythingIsInState(Car car, Car.State expected) {
        assertThat(car.convertibleState()).isEqualTo(expected);
        assertThat(car.glassState(0)).isEqualTo(expected);
        assertThat(car.glassState(1)).isEqualTo(expected);
        assertThat(car.glassState(2)).isEqualTo(expected);
        assertThat(car.glassState(3)).isEqualTo(expected);
    }

    private Car generateCarState(Boolean a, Boolean b, Boolean c, Boolean d) {
        Car car = new Car();
        List<Car.State> glassesState = Stream.of(a, b, c, d)
                .map(bool -> bool ? Car.State.OPENED : Car.State.CLOSED)
                .collect(Collectors.toList());
        IntStream.range(0, 4).forEach(i -> {
            car.applyStateOnGlass(i, glassesState.get(i));
        });
        return car;
    }

}