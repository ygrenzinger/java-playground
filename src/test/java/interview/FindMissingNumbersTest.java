package interview;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


public class FindMissingNumbersTest {

    @Test
    public void should_find_missing_number() {
        Integer[] numbers = {3, 1, 5, 4, 6, 10, 8};
        int sum = Arrays.stream(numbers).mapToInt( Integer::intValue).sum();

        //List<Integer> missingNumbers = findMissingNumbers1(numbers, 10);
        List<Integer> missingNumbers = findMissingNumbers2(numbers, sum, 10);
        assertThat(missingNumbers).contains(2, 7, 9);
    }

    private List<Integer> findMissingNumbers1(Integer[] ints, int limitMax) {
        Set<Integer> mySet = Sets.newHashSet(ints);
        List<Integer> missingNumbers = new ArrayList<>();
        for (int i = 0; i < limitMax; i++) {
            if (!mySet.contains(i)) {
                missingNumbers.add(i);
            }
        }

        return missingNumbers;
    }

    private List<Integer> findMissingNumbers2(Integer[] ints, int sum, int limitMax) {
        return new ArrayList<>();
    }
}
