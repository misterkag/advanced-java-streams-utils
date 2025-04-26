package com.karim.streamutils;

import org.junit.Test;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenericStreamsTest {

    @Test
    public void testStreamFromCollection() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        Stream<String> stream = list.stream();
        assertEquals(3, stream.count());
    }

    @Test
    public void testFilter() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        List<String> filtered = list.stream().filter(s -> s.startsWith("b")).collect(Collectors.toList());
        assertEquals(List.of("banana"), filtered);
    }

    @Test
    public void testMap() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        List<String> mapped = list.stream().map(String::toUpperCase).collect(Collectors.toList());
        assertEquals(List.of("APPLE", "BANANA", "CHERRY"), mapped);
    }

    @Test
    public void testDistinct() {
        List<String> list = Arrays.asList("apple", "banana", "apple", "cherry", "banana");
        List<String> distinct = list.stream().distinct().collect(Collectors.toList());
        assertEquals(List.of("apple", "banana", "cherry"), distinct);
    }

    @Test
    public void testReduce() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        int sum = list.stream().reduce(0, Integer::sum);
        assertEquals(10, sum);
    }

    @Test
    public void testCollectToList() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        List<String> collected = list.stream().collect(Collectors.toList());
        assertEquals(List.of("apple", "banana", "cherry"), collected);
    }

    @Test
    public void testGroupBy() {
        List<String> list = Arrays.asList("apple", "banana", "cherry", "apricot", "blueberry");
        Map<Character, List<String>> grouped = list.stream()
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
        assertEquals(3, grouped.size());
        assertTrue(grouped.containsKey('a'));
        assertTrue(grouped.containsKey('b'));
        assertTrue(grouped.containsKey('c'));
    }

    @Test
    public void testPartitioningBy() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Map<Boolean, List<Integer>> partitioned = list.stream()
                .collect(Collectors.partitioningBy(i -> i % 2 == 0));
        assertEquals(2, partitioned.size());
        assertTrue(partitioned.get(true).contains(2));
        assertTrue(partitioned.get(false).contains(1));
    }

    @Test
    public void testFlatMap() {
        List<List<String>> listOfLists = Arrays.asList(
                Arrays.asList("apple", "banana"),
                Arrays.asList("cherry", "date")
        );
        List<String> flatList = listOfLists.stream().flatMap(List::stream).collect(Collectors.toList());
        assertEquals(List.of("apple", "banana", "cherry", "date"), flatList);
    }

    @Test
    public void testSorted() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        List<String> sorted = list.stream().sorted().collect(Collectors.toList());
        assertEquals(List.of("apple", "banana", "cherry"), sorted);
    }

    @Test
    public void testFindFirst() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        Optional<String> first = list.stream().filter(s -> s.startsWith("b")).findFirst();
        assertTrue(first.isPresent());
        assertEquals("banana", first.get());
    }

    @Test
    public void testCount() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        long count = list.stream().filter(s -> s.length() > 5).count();
        assertEquals(2, count);
    }

    @Test
    public void testAnyMatchAllMatch() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        assertTrue(list.stream().anyMatch(s -> s.startsWith("b")));
        assertFalse(list.stream().allMatch(s -> s.length() > 6));
    }

    @Test
    public void testLimit() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        List<String> limited = list.stream().limit(2).collect(Collectors.toList());
        assertEquals(List.of("apple", "banana"), limited);
    }

    @Test
    public void testSkip() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        List<String> skipped = list.stream().skip(1).collect(Collectors.toList());
        assertEquals(List.of("banana", "cherry"), skipped);
    }

    @Test
    public void testParallelStream() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        long count = list.parallelStream().filter(s -> s.startsWith("b")).count();
        assertEquals(1, count);
    }

    @Test
    public void testMapOptional() {
        List<Optional<String>> list = Arrays.asList(Optional.of("apple"), Optional.empty(), Optional.of("banana"));
        List<String> mapped = list.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        assertEquals(List.of("apple", "banana"), mapped);
    }

    @Test
    public void testSplitStream() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Map<Boolean, List<Integer>> partitioned = list.stream()
                .collect(Collectors.partitioningBy(i -> i % 2 == 0));
        List<Integer> even = partitioned.get(true);
        List<Integer> odd = partitioned.get(false);

        assertEquals(List.of(2, 4), even);
        assertEquals(List.of(1, 3, 5), odd);
    }

    @Test
    public void testBatch() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<List<Integer>> batches = StreamUtils.batch(list.stream(), 3);
        assertEquals(3, batches.size());
        assertEquals(List.of(1, 2, 3), batches.get(0));
        assertEquals(List.of(4, 5, 6), batches.get(1));
        assertEquals(List.of(7, 8, 9), batches.get(2));
    }
}
