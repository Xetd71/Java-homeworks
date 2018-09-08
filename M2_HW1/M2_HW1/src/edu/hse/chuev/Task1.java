package edu.hse.chuev;

import java.util.*;

/**
 * This program compares speed of finding maximum
 * in different collections in Java
 *
 * Duplicate code has made especially.
 * The reason why it does is to find accurate time of execution of every collection
 */
public class Task1 {
    /**
     * The static constant in which methods writes their execution time
     */
    static long executionTime = 0;

    /**
     * Crates a random set of elements of type Long
     * @param size required size of set
     * @return set of different random values
     */
    static Set<Long> createRandomSet(int size) {
        if(size < 0)
            throw new IllegalArgumentException("Invalid size");

        Random rnd = new Random();
        Set<Long> generated = new LinkedHashSet<>();
        while (generated.size() < size) {
            Long next = rnd.nextLong();
            generated.add(next);
        }

        return generated;
    }

    /**
     * Finds maximum value of given long[]
     * and writes execution time in executionTime variable
     * @param array array where will be sought the maximum value
     * @return maximum value of array
     */
    static long findMax(long[] array) {
        long startTime = System.nanoTime();

        long max = array[0];
        for(int i = 1; i < array.length; i++)
            if(array[i] > max)
                max = array[i];

        executionTime = System.nanoTime() - startTime;
        return max;
    }

    /**
     * Finds maximum value of given ArrayList
     * and writes execution time in executionTime variable
     * @param list ArrayList where will be sought the maximum value
     * @return maximum value of ArrayList
     */
    static long findMax(ArrayList<Long> list) {
        long startTime = System.nanoTime();

        //Collections.max(list) - working a lot longer because of generics

        Iterator<Long> i = list.iterator();
        Long max = i.next();
        while (i.hasNext()) {
            Long next = i.next();
            if (next.compareTo(max) > 0)
                max = next;
        }

        executionTime = System.nanoTime() - startTime;
        return max;
    }

    /**
     * Finds maximum value of given LinkedList
     * and writes execution time in executionTime variable
     * @param list LinkedList where will be sought the maximum value
     * @return maximum value of LinkedList
     */
    static long findMax(LinkedList<Long> list) {
        long startTime = System.nanoTime();

        //Collections.max(list) - working a lot longer because of generics

        Iterator<Long> i = list.iterator();
        Long max = i.next();
        while (i.hasNext()) {
            Long next = i.next();
            if (next.compareTo(max) > 0)
                max = next;
        }

        executionTime = System.nanoTime() - startTime;
        return max;
    }

    /**
     * Finds maximum value of given Vector
     * and writes execution time in executionTime variable
     * @param vector Vector where will be sought the maximum value
     * @return maximum value of Vector
     */
    static long findMax(Vector<Long> vector) {
        long startTime = System.nanoTime();

        //Collections.max(list) - working a lot longer because of generics

        Iterator<Long> i = vector.iterator();
        Long max = i.next();
        while (i.hasNext()) {
            Long next = i.next();
            if (next.compareTo(max) > 0)
                max = next;
        }

        executionTime = System.nanoTime() - startTime;
        return max;
    }

    /**
     * Finds maximum value of given Set
     * and writes execution time in executionTime variable
     * @param set Set where will be sought the maximum value
     * @return maximum value of Set
     */
    static long findMax(HashSet<Long> set) {
        long startTime = System.nanoTime();

        //Collections.max(list) - working a lot longer because of generics

        Iterator<Long> i = set.iterator();
        Long max = i.next();
        while (i.hasNext()) {
            Long next = i.next();
            if (next.compareTo(max) > 0)
                max = next;
        }

        executionTime = System.nanoTime() - startTime;
        return max;
    }

    /**
     * The entry point in the program
     * @param args command arguments, where
     *             args[0] should be number of cycles of repeat of the maximum search
     *             args[1] should be length of collections
     */
    public static void main(String[] args) {
        if(args.length != 2 || !args[0].matches("\\d+") || !args[1].matches("\\d+")) {
            System.out.println("Invalid command line arguments");
            return;
        }

        int m = Integer.parseInt(args[0]), n = Integer.parseInt(args[1]);
        long arrayTime = 0, arrayListTime = 0, linkedListTime = 0, vectorTime = 0, hashSetTime = 0;
        System.out.println("Java 8:");
        System.out.println("Task 1:");
        System.out.printf("m = %d, n = %d%n", m, n);
        System.out.println("Search for the maximal element:");

        for(int i = 0; i < m; i++) {
            Set<Long> randomSet = createRandomSet(n);

            findMax(Arrays.stream(randomSet.toArray(new Long[n])).mapToLong(x->x).toArray());
            arrayTime += executionTime;

            findMax(new ArrayList<>(randomSet));
            arrayListTime += executionTime;

            findMax(new LinkedList<>(randomSet));
            linkedListTime += executionTime;

            findMax(new Vector<>(randomSet));
            vectorTime += executionTime;

            findMax(new HashSet<>(randomSet));
            hashSetTime += executionTime;
        }

        System.out.printf("Array:      %9d ns%9.2f arrayTime%n", arrayTime / m, (float)1);
        System.out.printf("ArrayList:  %9d ns%9.2f arrayTime%n", arrayListTime / m, arrayListTime / (float)arrayTime);
        System.out.printf("LinkedList: %9d ns%9.2f arrayTime%n", linkedListTime / m, linkedListTime / (float)arrayTime);
        System.out.printf("Vector:     %9d ns%9.2f arrayTime%n", vectorTime / m, vectorTime / (float)arrayTime);
        System.out.printf("HashSet:    %9d ns%9.2f arrayTime%n", hashSetTime / m, hashSetTime / (float)arrayTime);
    }
}
