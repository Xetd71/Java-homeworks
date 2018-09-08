package edu.hse.chuev;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.util.function.ToLongFunction;

public class Task1Test {
    private final static int LENGTH_1 = 100;
    private final static int LENGTH_2 = 18;
    private final static int LENGTH_3 = 657;

    @Test
    public void getRandomSet() throws Exception {
        Set<Long> randomSet = Task1.createRandomSet(LENGTH_1);
        assertEquals(LENGTH_1, randomSet.size());

        randomSet = Task1.createRandomSet(LENGTH_2);
        assertEquals(LENGTH_2, randomSet.size());

        randomSet = Task1.createRandomSet(LENGTH_3);
        assertEquals(LENGTH_3, randomSet.size());
    }

    @Test
    public void findMaxInArray() throws Exception {
        ToLongFunction<long[]> findMax = (array) -> {
            long max = array[0];
            for(int i = 1; i < array.length; i++)
                if(array[i] > max)
                    max = array[i];
            return max;
        };

        long[] array = Arrays.stream(Task1.createRandomSet(LENGTH_1).toArray(new Long[LENGTH_1])).mapToLong(x->x).toArray();
        assertEquals(findMax.applyAsLong(array), Task1.findMax(array));

        array = Arrays.stream(Task1.createRandomSet(LENGTH_2).toArray(new Long[LENGTH_2])).mapToLong(x->x).toArray();
        assertEquals(findMax.applyAsLong(array), Task1.findMax(array));

        array = Arrays.stream(Task1.createRandomSet(LENGTH_3).toArray(new Long[LENGTH_3])).mapToLong(x->x).toArray();
        assertEquals(findMax.applyAsLong(array), Task1.findMax(array));
    }

    @Test
    public void findMaxInArrayList() throws Exception {
        ArrayList<Long> list = new ArrayList<>(Task1.createRandomSet(LENGTH_1));
        assertEquals((long)Collections.max(list), Task1.findMax(list));

        list = new ArrayList<>(Task1.createRandomSet(LENGTH_2));
        assertEquals((long)Collections.max(list), Task1.findMax(list));

        list = new ArrayList<>(Task1.createRandomSet(LENGTH_3));
        assertEquals((long)Collections.max(list), Task1.findMax(list));
    }

    @Test
    public void findMaxLinkedList() throws Exception {
        LinkedList<Long> list = new LinkedList<>(Task1.createRandomSet(LENGTH_1));
        assertEquals((long)Collections.max(list), Task1.findMax(list));

        list = new LinkedList<>(Task1.createRandomSet(LENGTH_2));
        assertEquals((long)Collections.max(list), Task1.findMax(list));

        list = new LinkedList<>(Task1.createRandomSet(LENGTH_3));
        assertEquals((long)Collections.max(list), Task1.findMax(list));
    }

    @Test
    public void findMaxVector() throws Exception {
        Vector<Long> list = new Vector<>(Task1.createRandomSet(LENGTH_1));
        assertEquals((long)Collections.max(list), Task1.findMax(list));

        list = new Vector<>(Task1.createRandomSet(LENGTH_2));
        assertEquals((long)Collections.max(list), Task1.findMax(list));

        list = new Vector<>(Task1.createRandomSet(LENGTH_3));
        assertEquals((long)Collections.max(list), Task1.findMax(list));
    }

    @Test
    public void findMaxHashSet() throws Exception {
        HashSet<Long> list = new HashSet<>(Task1.createRandomSet(LENGTH_1));
        assertEquals((long)Collections.max(list), Task1.findMax(list));

        list = new HashSet<>(Task1.createRandomSet(LENGTH_2));
        assertEquals((long)Collections.max(list), Task1.findMax(list));

        list = new HashSet<>(Task1.createRandomSet(LENGTH_3));
        assertEquals((long)Collections.max(list), Task1.findMax(list));
    }

}