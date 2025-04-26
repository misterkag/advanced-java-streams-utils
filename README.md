# Advanced Java Streams Utils

Small utilities showing advanced usage of Java Streams:

## Features
- Grouping and summing
- Filtering empty Strings
- Splitting a Stream based on a Predicate
- Extracting values from Stream<Optional<T>>
- Batching Stream elements

## Requirements
- Java 17+
- Maven

## How to run tests
```bash
mvn clean test
```

## Example usage
```java
List<Entity> list = List.of(new Entity("Books", 50), new Entity("Books", 30));
Map<String, Integer> summed = StreamUtils.groupByAndSum(list, Entity::category, Entity::amount);
System.out.println(summed); // {Books=80}