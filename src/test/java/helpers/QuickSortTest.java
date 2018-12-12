package helpers;

import helpers.QuickSort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuickSortTest {
    private Random random;
    private QuickSort<Integer> sorter;
    private List<Integer> values;

    @BeforeEach
    void beforeEach() {
        random = new Random();
        initValues();
    }

    @Test
    void testSort() {
        values.add(110);

        sorter = new QuickSort<>(values, new ConcurrentLinkedQueue<>());
        List<Integer> result = sorter.sort();

        assertEquals(110, result.get(result.size() - 1).intValue());
    }

    @Test
    void deepTestSort() {
        for (int i = 0; i < 10; i++) {
            sorter = new QuickSort<>(values, new ConcurrentLinkedQueue<>());
            List<Integer> result = sorter.sort();

            int cachedValue = result.remove(0);
            for (Integer value : result) {
                assertTrue(cachedValue <= value);
            }
            initValues();
        }
    }

    @Test
    void testAnimationQueue() {
        values.add(110);
        values.add(0);

        sorter = new QuickSort<>(values, new ConcurrentLinkedQueue<>());
        Queue<int[]> queue = sorter.getAnimationQueue();

        assertNotNull(queue);
        assertFalse(queue.isEmpty());
    }

    private void initValues() {
        values = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            values.add(random.nextInt(100));
        }
    }
}
