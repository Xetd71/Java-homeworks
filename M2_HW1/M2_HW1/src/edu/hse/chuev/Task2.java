package edu.hse.chuev;

import java.util.*;

/**
 * This program compares speed of merging to collections
 * in different programming languages in different collections
 *
 * Duplicate code has made especially.
 * The reason why it does is to find accurate time of execution of every collection
 */
public class Task2 {
    /**
     * The static constant in which methods writes their execution time
     */
    static long executionTime = 0;


    /**
     * Merges two sorted arrays and returns result collection
     * and writes execution time in executionTime variable
     * @param firstArray the sorted array to merge
     * @param secondArray the sorted array to merge
     * @return the result of merge to given arrays
     */
    static long[] mergeSorted(long[] firstArray, long[] secondArray) {
        long startTime = System.nanoTime();
        long[] output = new long[firstArray.length + secondArray.length];

        int i = 0, j = 0;
        while(i < firstArray.length && j < secondArray.length) {
            if(firstArray[i] > secondArray[j])
                output[i + j] = secondArray[j++];
            else
                output[i + j] = firstArray[i++];
        }
        for(; i < firstArray.length; i++)
            output[i + j] = firstArray[i];
        for(; j < secondArray.length; j++)
            output[i + j] = secondArray[j];

        executionTime = System.nanoTime() - startTime;
        return output;
    }

    /**
     * Merges two sorted ArrayList's and returns result collection
     * and writes execution time in executionTime variable
     * @param firstList the sorted ArrayList to merge
     * @param secondList the sorted ArrayList to merge
     * @return the result of merge to given ArrayList's
     */
    static ArrayList<Long> mergeSorted(ArrayList<Long> firstList, ArrayList<Long> secondList) {
        long startTime = System.nanoTime();
        ArrayList<Long> output = new ArrayList<>();
        long firstListSize = firstList.size(), secondListSize = secondList.size();

        int i = 0, j = 0;
        while(i < firstListSize && j < secondListSize) {
            if(firstList.get(i) > secondList.get(j))
                output.add(secondList.get(j++));
            else
                output.add(firstList.get(i++));
        }
        for(; i < firstListSize; i++)
            output.add(firstList.get(i));
        for(; j < secondListSize; j++)
            output.add(secondList.get(j));

        executionTime = System.nanoTime() - startTime;
        return output;
    }

    /**
     * Merges two sorted LinkedList's and returns result collection
     * and writes execution time in executionTime variable
     * @param firstList the sorted LinkedList to merge
     * @param secondList the sorted LinkedList to merge
     * @return the result of merge to given LinkedList's
     */
    static LinkedList<Long> mergeSorted(LinkedList<Long> firstList, LinkedList<Long> secondList) {
        long startTime = System.nanoTime();
        LinkedList<Long> output = new LinkedList<>();
        Iterator<Long> i = firstList.iterator(), j = secondList.iterator();
        if(!i.hasNext())
            return secondList;
        if(!j.hasNext())
            return firstList;

        long firstElement = i.next(), secondElement = j.next();
        boolean isFirstListEnds = false, isSecondListEnds = false;
        while(!(isFirstListEnds || isSecondListEnds)) {
            if(firstElement > secondElement) {
                output.add(secondElement);
                if(j.hasNext()) {
                    secondElement = j.next();
                } else {
                    isSecondListEnds = true;
                    output.add(firstElement);
                }
            } else {
                output.add(firstElement);
                if(i.hasNext()) {
                    firstElement = i.next();
                } else {
                    isFirstListEnds = true;
                    output.add(secondElement);
                }
            }
        }

        while(i.hasNext())
            output.add(i.next());
        while(j.hasNext())
            output.add(j.next());

        executionTime = System.nanoTime() - startTime;
        return output;
    }

    /**
     * Merges two sorted Vector's and returns result collection
     * and writes execution time in executionTime variable
     * @param firstVector the sorted Vector to merge
     * @param secondVector the sorted Vector to merge
     * @return the result of merge to given Vector's
     */
    static Vector<Long> mergeSorted(Vector<Long> firstVector, Vector<Long> secondVector) {
        long startTime = System.nanoTime();
        Vector<Long> output = new Vector<>();
        Iterator<Long> i = firstVector.iterator(), j = secondVector.iterator();
        if(!i.hasNext())
            return secondVector;
        if(!j.hasNext())
            return firstVector;

        long firstElement = i.next(), secondElement = j.next();
        boolean isFirstVectorEnds = false, isSecondVectorEnds = false;
        while(!(isFirstVectorEnds || isSecondVectorEnds)) {
            if(firstElement > secondElement) {
                output.add(secondElement);
                if(j.hasNext()) {
                    secondElement = j.next();
                } else {
                    isSecondVectorEnds = true;
                    output.add(secondElement);
                }
            } else {
                output.add(firstElement);
                if(i.hasNext()) {
                    firstElement = i.next();
                } else {
                    isFirstVectorEnds = true;
                    output.add(firstElement);
                }
            }
        }

        while(i.hasNext())
            output.add(i.next());
        while(j.hasNext())
            output.add(j.next());

        executionTime = System.nanoTime() - startTime;
        return output;
    }

    /**
     * Creates and returns long[] array with passed size filled by random long values
     * @param size required size of set
     * @return long[] array with passed size filled by random long values
     */
    static long[] createRandomArray(int size) {
        if(size < 0)
            throw new IllegalArgumentException("Invalid size");

        Random rnd = new Random();
        long[] generated = new long[size];
        for(int i = 0; i < size; i++)
            generated[i] = rnd.nextLong();

        return generated;
    }

    /**
     * Creates and returns sorted long[] array with passed size filled by random long values
     * @param size required size of set
     * @return sorted long[] array with passed size filled by random long values
     */
    static long[] createSortedArray(int size) {
        long[] array = createRandomArray(size);
        Arrays.sort(array);
        return array;
    }

    /**
     * Converts long[] array to ArrayList<Long>
     * @param array array to convert
     * @return ArrayList<Long> from array
     */
    static ArrayList<Long> arrayToArrayList(long[] array) {
        ArrayList<Long> list = new ArrayList<>();
        for(long element : array)
            list.add(element);
        return list;
    }

    /**
     * Converts long[] array to Vector<Long>
     * @param array array to convert
     * @return Vector<Long> from array
     */
    static Vector<Long> arrayToVector(long[] array) {
        Vector<Long> vector = new Vector<>();
        for(long element : array)
            vector.add(element);
        return vector;
    }

    /**
     * The entry point in the program
     * @param args command arguments, where
     *             args[0] should be number of cycles of repeat of the maximum search
     *             args[1] should be length of first collection
     *             args[2] should be length of second collection
     */
    public static void main(String[] args) {
        if(args.length != 3 || !args[0].matches("\\d+") || !args[1].matches("\\d+")|| !args[2].matches("\\d+")) {
            System.out.println("Invalid command line arguments");
            return;
        }

        int m = Integer.parseInt(args[0]), n1 = Integer.parseInt(args[1]), n2 = Integer.parseInt(args[2]);
        long arrayTime = 0, arrayListTime = 0, linkedListTime = 0, vectorTime = 0;
        System.out.println("Java 8:");
        System.out.println("Task 2:");
        System.out.printf("m = %d, n1 = %d, n2 = %d%n", m, n1, n2);
        System.out.println("Merge two sorted arrays:");

        for(int i = 0; i < m; i++) {
            long[] array1 = createSortedArray(n1);
            long[] array2 = createSortedArray(n2);

            mergeSorted(array1, array2);
            arrayTime += executionTime;

            mergeSorted(arrayToArrayList(array1), arrayToArrayList(array2));
            arrayListTime += executionTime;

            mergeSorted(new LinkedList<>(arrayToArrayList(array1)), new LinkedList<>(arrayToArrayList(array2)));
            linkedListTime += executionTime;

            mergeSorted(arrayToVector(array1), arrayToVector(array2));
            vectorTime += executionTime;
        }

        System.out.printf("Array:      %9d ns%9.2f arrayTime%n", arrayTime / m, (float)1);
        System.out.printf("ArrayList:  %9d ns%9.2f arrayTime%n", arrayListTime / m, arrayListTime / (float)arrayTime);
        System.out.printf("LinkedList: %9d ns%9.2f arrayTime%n", linkedListTime / m, linkedListTime / (float)arrayTime);
        System.out.printf("Vector:     %9d ns%9.2f arrayTime%n", vectorTime / m, vectorTime / (float)arrayTime);
    }
}
