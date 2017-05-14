package interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FindSingleTest {

    @Test
    public void should_return_nothing_if_empty_list() {
        assertThat(findSingle(new ArrayList<>())).isEmpty();
    }

    @Test
    public void should_return_nothing_if_no_single_element() {
        assertThat(findSingle(Arrays.asList(1,1))).isEmpty();
    }

    @Test
    public void should_return_elmt_if_only_this_element() {
        assertThat(findSingle(Arrays.asList(1))).contains(1);
    }

    @Test
    public void should_return_single_elmt_in_list_of_element() {
        assertThat(findSingle(Arrays.asList(1,3,3))).contains(1);
        assertThat(findSingle(Arrays.asList(1,1,3))).contains(3);

        assertThat(findSingle(Arrays.asList(1,2,2,3,3))).contains(1);
        assertThat(findSingle(Arrays.asList(1,1,2,2,3))).contains(3);
    }

    private Optional<Integer> findSingle(List<Integer> numbers) {
        if (numbers.isEmpty() || numbers.size() == 2) {
            return Optional.empty();
        }
        if (numbers.size() == 1) {
            return Optional.of(numbers.get(0));
        }

        int pivot = (numbers.size() / 2) + 1;
        int a = numbers.get(pivot);
        int b = numbers.get(pivot + 1);

        if (a == b) {
            return findSingle(numbers.subList(pivot+2, numbers.size()));
        } else {
            return findSingle(numbers.subList(0, pivot-1));
        }
    }

}
