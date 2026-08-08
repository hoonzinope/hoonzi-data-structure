package structure.skiplist;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

class SkipListTest {

    @Test
    void randomTest() {
        var skipList = new SkipList<Integer>(Comparator.naturalOrder());
        var expected = new TreeMap<Integer, Integer>();

        var random = RandomGenerator.getDefault();

        for (int i = 0; i < 10_000; i++) {
            int value = random.nextInt(100);

            if (random.nextBoolean()) {
                skipList.insert(value);

                expected.merge(value, 1, Integer::sum);
            } else {
                skipList.delete(value);

                expected.computeIfPresent(value, (key, count) ->
                        count == 1 ? null : count - 1
                );
            }

            // 여기서 둘의 상태 비교
            // 1. 전체 원소 수 비교
            int expectedSize = expected.values()
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            assertEquals(expectedSize, skipList.size());

            // 2. 전체 값 범위에 대해 contains/count 비교
            for (int v = 0; v < 100; v++) {
                assertEquals(
                        expected.containsKey(v),
                        skipList.contains(v)
                );

                assertEquals(
                        expected.getOrDefault(v, 0),
                        skipList.count(v)
                );
            }
        }
    }
}