package com.karim.streamutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamUtils {

    //  Private constructor preventing any instantiation of this utility class.
    private StreamUtils() {}

    /**
     * Splits a Stream<T> into small lists of size batchSize.
     * Example: [1,2,3,4,5] with batchSize 2 → [[1,2], [3,4], [5]].
     * Each time currentBatch reaches batchSize, it is added to batches and then cleared.
     * At the end, if there are remaining elements, they are also added.
     **/
    public static <T> List<List<T>> batch(Stream<T> stream, int batchSize) {
        if (batchSize <= 0) throw new IllegalArgumentException("batchSize must be > 0");
        List<List<T>> batches = new ArrayList<>();
        List<T> currentBatch = new ArrayList<>(batchSize);
        stream.forEach(element -> {
            currentBatch.add(element);
            if (currentBatch.size() == batchSize) {
                batches.add(new ArrayList<>(currentBatch));
                currentBatch.clear();
            }
        });
        if (!currentBatch.isEmpty()) {
            batches.add(currentBatch);
        }
        return batches;
    }

    /**
     * Groups elements in a list by a given key and sums their values.
     * Example: For list [{category: "A", amount: 10}, {category: "B", amount: 20}, {category: "A", amount: 5}],
     * Result would be {A=15, B=20}.
     * Uses groupingBy to group elements by the key, then sums their associated values.
     */
    public static Map<String, Integer> groupByAndSum(List<Entity> list, Function<Entity, String> keyMapper, ToIntFunction<Entity> valueMapper) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        keyMapper,
                        Collectors.summingInt(valueMapper)
                ));
    }

    /**
     * Filters out empty and blank strings from a stream.
     * Example: input ["", "abc", null, "   ", "xyz"] → output ["abc", "xyz"].
     * Filters out strings that are null, empty, or consist only of whitespace.
     */
    public static Stream<String> filterNonEmptyStrings(Stream<String> stream) {
        return stream.filter(s -> s != null && !s.isBlank());
    }

    /**
     * Splits a stream into two streams based on a given predicate.
     * Example: input [1,2,3,4,5] with predicate (i -> i % 2 == 0)  → left: [2, 4], right: [1, 3, 5].
     * Partitions the stream into two parts: one that satisfies the predicate and one that does not.
     */
    public static <T> Pair<Stream<T>, Stream<T>> splitStream(Stream<T> stream, Predicate<T> predicate) {
        Map<Boolean, List<T>> partitioned = stream.collect(Collectors.partitioningBy(predicate));
        return new Pair<>(partitioned.get(true).stream(), partitioned.get(false).stream());
    }

    /**
     * Maps a stream of Optional elements to a stream of their values, filtering out empty optionals.
     * Example: input [Optional.of("a"), Optional.empty(), Optional.of("b")] → output ["a", "b"].
     * Filters out empty Optionals and extracts the values from the non-empty ones.
     */
    public static <T> Stream<T> mapOptional(Stream<Optional<T>> stream) {
        return stream.filter(Optional::isPresent).map(Optional::get);
    }

    /**
     * Helper record to represent a pair of values.
     */
    public record Pair<L, R>(L left, R right) {}
}
