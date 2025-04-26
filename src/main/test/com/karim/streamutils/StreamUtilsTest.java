package com.karim.streamutils;

import org.junit.Test;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class StreamUtilsTest {

    @Test
    public void testBatch() {
        List<Integer> input = List.of(1, 2, 3, 4, 5);
        List<List<Integer>> batches = StreamUtils.batch(input.stream(), 2);
        assertEquals(3, batches.size());
        assertEquals(List.of(1,2), batches.get(0));
        assertEquals(List.of(3,4), batches.get(1));
        assertEquals(List.of(5), batches.get(2));
    }

    @Test
    public void testGroupByAndSum() {
        List<Entity> entities = List.of(
                new Entity("A", 10),
                new Entity("B", 20),
                new Entity("A", 5)
        );
        Map<String, Integer> result = StreamUtils.groupByAndSum(entities, Entity::category, Entity::amount);

        assertEquals(15, (int) result.get("A")); // <-- ici
        assertEquals(20, (int) result.get("B")); // <-- ici
    }


    @Test
    public void testFilterNonEmptyStrings() {
        List<String> input = new ArrayList<>(Arrays.asList("", "abc", null, "   ", "xyz"));
        List<String> filtered = StreamUtils.filterNonEmptyStrings(input.stream()).toList();
        assertEquals(List.of("abc", "xyz"), filtered);
    }

    @Test
    public void testSplitStream() {
        List<Integer> input = List.of(1,2,3,4,5);
        StreamUtils.Pair<Stream<Integer>, Stream<Integer>> result = StreamUtils.splitStream(input.stream(), i -> i % 2 == 0);
        assertEquals(List.of(2,4), result.left().toList());
        assertEquals(List.of(1,3,5), result.right().toList());
    }

    @Test
    public void testMapOptional() {
        List<Optional<String>> input = List.of(Optional.of("a"), Optional.empty(), Optional.of("b"));
        List<String> output = StreamUtils.mapOptional(input.stream()).toList();
        assertEquals(List.of("a", "b"), output);
    }
}