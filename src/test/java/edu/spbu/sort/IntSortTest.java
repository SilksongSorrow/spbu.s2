package edu.spbu.sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import static edu.spbu.TestUtils.generateRandomIntArray;
import static edu.spbu.TestUtils.testN;
import static org.junit.Assert.*;

public class IntSortTest{
    public static final int SEED=1;
    public static final int ARRAY_SIZE=1000_000;
    private static final Supplier<Long> testArray,testList;

    @Test
    public void testSortArray(){
        System.out.println("Round execution time(ms) - testSortArray(): "+(testN(testArray,1)/1000000));
    }
    @Test
    public void testSortList(){
        System.out.println("Round execution time(ms) - testSortList(): "+(testN(testList,1)/1000000));
    }

    static{
        testArray=()->{
            int[] array=generateRandomIntArray(ARRAY_SIZE,SEED);

            //сортируем массив и замеряем время работы
            long startTime=System.nanoTime();
            IntSort.sort(array);
            long estimatedTime=System.nanoTime()-startTime;

            // проверяем правильность сортировки
            for(int i=1;i<array.length;i++){
                assertTrue("Element "+array[i]+" at "+i+" position is not in the order",
                        array[i-1]<=array[i]);
            }
            return estimatedTime;
        };
        testList=()->{
            int[] array=generateRandomIntArray(ARRAY_SIZE,SEED);
            List<Integer> list=new ArrayList<>(ARRAY_SIZE);
            for(int i: array)list.add(i);

            //сортируем список и замеряем время работы
            long startTime=System.nanoTime();
            IntSort.sort(list);
            long estimatedTime=System.nanoTime()-startTime;
            System.out.println("Execution time(ms) - testSortList(): "+(estimatedTime/1000000));

            // проверяем правильность сортировки
            for(int i=1;i<list.size();i++){
                assertTrue("Element "+list.get(i)+" at "+i+" position is not in the order",
                        list.get(i-1)<=list.get(i));
            }
            return estimatedTime;
        };
    }
}