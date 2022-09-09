package edu.spbu.sort;

import edu.spbu.TestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static edu.spbu.TestUtils.*;
import static org.junit.Assert.*;

public class IntSortTest{
    public static final int SEED=1;
    public static final int ARRAY_SIZE=100_000;
    private static final Supplier<Long> testArray,testList;

    @Test
    public void testSortArray(){
        TestUtils.printTest("testSortArray",testN(testArray,100));
    }
    @Test
    public void testSortList(){
        TestUtils.printTest("testSortList",testN(testList,100));
    }

    static{
        testArray=()->{
            Integer[] array=generateRandomIntArray(ARRAY_SIZE,SEED);

            //сортируем массив и замеряем время работы
            long startTime=System.nanoTime();
            FGSort.sort(array);
            long estimatedTime=System.nanoTime()-startTime;

            // проверяем правильность сортировки
            for(int i=1;i<array.length;i++){
                assertTrue("Element "+array[i]+" at "+i+" position is not in the order",
                        array[i-1]<=array[i]);
            }
            return estimatedTime;
        };
        testList=()->{
            Integer[] array=generateRandomIntArray(ARRAY_SIZE,SEED);
            List<Integer> list=Arrays.asList(array);

            //сортируем список и замеряем время работы
            long startTime=System.nanoTime();
            FGSort.sort(list);
            long estimatedTime=System.nanoTime()-startTime;

            // проверяем правильность сортировки
            for(int i=1;i<list.size();i++){
                assertTrue("Element "+list.get(i)+" at "+i+" position is not in the order",
                        list.get(i-1)<=list.get(i));
            }
            return estimatedTime;
        };
    }
}