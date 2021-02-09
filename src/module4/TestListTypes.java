package module4;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class TestListTypes {

    /*
     * Define the lists now to prevent any creating from affecting time
     */
    private static ArrayList<Integer> arrayList = new ArrayList<>();
    private static LinkedList<Integer> linkedList = new LinkedList<>();
    private static Hashtable<Integer, Integer> hashtable = new Hashtable<>();

    /*
     * Create an array to keep track of timing for each List Type
     */
    private static ArrayList<Long> totalTime = new ArrayList<>();

    /*
     * Set the Maximum Iterations of Integers to be added
     */
    private static final int INTEGER_ITERATIONS = 2000000;

    public static void main(String[] args) {
        long startTime = 0;
        long endTime = 0;

        //Test the time to operate on the Array List
        System.out.println("Testing Array List");
        startTime = System.currentTimeMillis();
        addRandomInts(ListIndex.ARRAY_LIST);
        endTime = System.currentTimeMillis();
        //Add the total time to the list for comparison
        totalTime.add(getDifferenceInTimes(startTime, endTime));

        //Test the time to operate on the LinkedList
        System.out.println("Testing Linked List");
        startTime = System.currentTimeMillis();
        addRandomInts(ListIndex.LINKED_LIST);
        endTime = System.currentTimeMillis();
        //Add the total time to the list for comparison
        totalTime.add(getDifferenceInTimes(startTime, endTime));

        //Test the time to operate on the Hashtable
        System.out.println("Testing Hashtable");
        startTime = System.currentTimeMillis();
        addRandomInts(ListIndex.HASH_TABLE);
        endTime = System.currentTimeMillis();
        //Add the total time to the list for comparison
        totalTime.add(getDifferenceInTimes(startTime, endTime));

        System.out.println(String.format(
                "%nArray List: %s%n" +
                        "Linked List: %s%n" +
                        "Hashtable: %s", totalTime.get(0), totalTime.get(1), totalTime.get(2)
        ));



    }

    /**
     * Adds INTEGER_ITERATIONS to the list specified then removes them to determine time efficiency
     * @param listIndex (ListIndex) - Enum to specify which list type to test
     */
    public static void addRandomInts(ListIndex listIndex) {
        Random randomInteger = new Random();
        switch(listIndex) {
            case ARRAY_LIST:
                //Add the integers
                IntStream.range(0, INTEGER_ITERATIONS)
                        .forEach(arrayList::add);
                //Remove the integers
                arrayList.clear();
                break;
            case LINKED_LIST:
                //Add the integers
                IntStream.range(0, INTEGER_ITERATIONS)
                        .forEach(linkedList::add);
                //Remove the integers
                linkedList.clear();
                break;
            case HASH_TABLE:
                //Add the integers
                AtomicInteger counter = new AtomicInteger(0);
                IntStream.range(0, INTEGER_ITERATIONS)
                        .forEach(i -> {
                            hashtable.put(counter.getAndIncrement(), i);
                        });
                //Remove the integers
                hashtable.clear();
                break;
        }
    }

    /**
     * Returns the difference of time (in milliseconds) based on start and end time
     * @param startTime (long) - Start time in milliseconds
     * @param endTime (long) - End time in milliseconds
     * @return (long) - Difference between times in milliseconds
     */
    public static long getDifferenceInTimes(long startTime, long endTime) {
        return endTime - startTime;
    }

    /**
     * Enum to differentiate which list is being tested
     */
    private enum ListIndex {
        ARRAY_LIST, LINKED_LIST, HASH_TABLE;
    }

}
