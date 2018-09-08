package edu.hse.chuev;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class Task2Test {
    private final static int LENGTH_1 = 100;
    private final static int LENGTH_2 = 3216;
    private final static int LENGTH_3 = 44;

    @Test
    public void mergeSortedArrays() throws Exception {
        long[] array = Task2.mergeSorted(Task2.createSortedArray(LENGTH_3), Task2.createSortedArray(LENGTH_2));
        assertEquals(LENGTH_2 + LENGTH_3, array.length);
        for(int i = 0; i < array.length - 1; i++)
            assertTrue(array[i] < array[i + 1]);

        array = Task2.mergeSorted(Task2.createSortedArray(LENGTH_1), Task2.createSortedArray(LENGTH_3));
        assertEquals(LENGTH_1 + LENGTH_3, array.length);
        for(int i = 0; i < array.length - 1; i++)
            assertTrue(array[i] < array[i + 1]);
    }

    @Test
    public void mergeSortedArrayLists() throws Exception {
        ArrayList<Long> arrayList = Task2.mergeSorted(
                Task2.arrayToArrayList(Task2.createSortedArray(LENGTH_3)),
                Task2.arrayToArrayList(Task2.createSortedArray(LENGTH_2)));
        assertEquals(LENGTH_3 + LENGTH_2, arrayList.size());
        Iterator<Long> i = arrayList.iterator();
        long first = i.next(), second;
        while(i.hasNext()) {
            second = first;
            first = i.next();
            assertTrue(second <= first);
        }

        arrayList = Task2.mergeSorted(
                Task2.arrayToArrayList(Task2.createSortedArray(LENGTH_1)),
                Task2.arrayToArrayList(Task2.createSortedArray(LENGTH_3)));
        assertEquals(LENGTH_1 + LENGTH_3, arrayList.size());
        i = arrayList.iterator();
        first = i.next();
        while(i.hasNext()) {
            second = first;
            first = i.next();
            assertTrue(second <= first);
        }
    }

    @Test
    public void mergeSortedLinkedLists() throws Exception {
        LinkedList<Long> arrayList = Task2.mergeSorted(
                new LinkedList<>(Task2.arrayToArrayList(Task2.createSortedArray(LENGTH_3))),
                new LinkedList<>(Task2.arrayToArrayList(Task2.createSortedArray(LENGTH_2))));
        assertEquals(LENGTH_3 + LENGTH_2, arrayList.size());
        Iterator<Long> i = arrayList.iterator();
        long first = i.next(), second;
        while(i.hasNext()) {
            second = first;
            first = i.next();
            assertTrue(second <= first);
        }

        arrayList = Task2.mergeSorted(
                new LinkedList<>(Task2.arrayToArrayList(Task2.createSortedArray(LENGTH_1))),
                new LinkedList<>(Task2.arrayToArrayList(Task2.createSortedArray(LENGTH_3))));
        assertEquals(LENGTH_1 + LENGTH_3, arrayList.size());
        i = arrayList.iterator();
        first = i.next();
        while(i.hasNext()) {
            second = first;
            first = i.next();
            assertTrue(second <= first);
        }
    }

    @Test
    public void mergeSortedVectors() throws Exception {
        Vector<Long> arrayList = Task2.mergeSorted(
                Task2.arrayToVector(Task2.createSortedArray(LENGTH_3)),
                Task2.arrayToVector(Task2.createSortedArray(LENGTH_2)));
        assertEquals(LENGTH_3 + LENGTH_2, arrayList.size());
        Iterator<Long> i = arrayList.iterator();
        long first = i.next(), second;
        while(i.hasNext()) {
            second = first;
            first = i.next();
            assertTrue(second <= first);
        }

        arrayList = Task2.mergeSorted(
                Task2.arrayToVector(Task2.createSortedArray(LENGTH_1)),
                Task2.arrayToVector(Task2.createSortedArray(LENGTH_3)));
        assertEquals(LENGTH_1 + LENGTH_3, arrayList.size());
        i = arrayList.iterator();
        first = i.next();
        while(i.hasNext()) {
            second = first;
            first = i.next();
            assertTrue(second <= first);
        }
    }

    @Test
    public void createRandomArray() throws Exception {
        long[] array = Task2.createRandomArray(LENGTH_1);
        assertEquals(LENGTH_1, array.length);

        array = Task2.createRandomArray(LENGTH_2);
        assertEquals(LENGTH_2, array.length);

        array = Task2.createRandomArray(LENGTH_3);
        assertEquals(LENGTH_3, array.length);
    }

    @Test
    public void createSortedArray() throws Exception {
        long[] array = Task2.createSortedArray(LENGTH_1);
        assertEquals(LENGTH_1, array.length);
        for(int i = 0; i < array.length - 1; i++)
            assertTrue(array[i] < array[i + 1]);

        array = Task2.createSortedArray(LENGTH_2);
        assertEquals(LENGTH_2, array.length);
        for(int i = 0; i < array.length - 1; i++)
            assertTrue(array[i] < array[i + 1]);

        array = Task2.createSortedArray(LENGTH_3);
        assertEquals(LENGTH_3, array.length);
        for(int i = 0; i < array.length - 1; i++)
            assertTrue(array[i] < array[i + 1]);
    }

    @Test
    public void arrayToArrayList() throws Exception {
        long[] array = Task2.createRandomArray(LENGTH_3);
        ArrayList<Long> arrayList = Task2.arrayToArrayList(array);
        assertEquals(array.length, arrayList.size());
        Iterator<Long> j = arrayList.iterator();
        int i = 0;
        while(j.hasNext())
            assertEquals(array[i++], (long)j.next());

        array = Task2.createSortedArray(LENGTH_2);
        arrayList = Task2.arrayToArrayList(array);
        assertEquals(array.length, arrayList.size());
        j = arrayList.iterator();
        i = 0;
        while(j.hasNext())
            assertEquals(array[i++], (long)j.next());
    }

    @Test
    public void arrayToVector() throws Exception {
        long[] array = Task2.createRandomArray(LENGTH_3);
        Vector<Long> vector = Task2.arrayToVector(array);
        assertEquals(array.length, vector.size());
        Iterator<Long> j = vector.iterator();
        int i = 0;
        while(j.hasNext())
            assertEquals(array[i++], (long)j.next());

        array = Task2.createSortedArray(LENGTH_2);
        vector = Task2.arrayToVector(array);
        assertEquals(array.length, vector.size());
        j = vector.iterator();
        i = 0;
        while(j.hasNext())
            assertEquals(array[i++], (long)j.next());
    }

}