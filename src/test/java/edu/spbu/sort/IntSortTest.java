package edu.spbu.sort;

import edu.spbu.TestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static edu.spbu.TestUtils.*;
import static org.junit.Assert.*;

public class IntSortTest{
    public static final int ARRAY_SIZE=10_000;
    private static final Supplier<Long> testArray, testList;
    private static final Supplier<Long> testStArray, testStList;

    @Test
    public void testSortArray(){
        TestUtils.printTestN("testSortArray",testN(testArray,100));
    }

    @Test
    public void testSortList(){
        TestUtils.printTestN("testSortList",testN(testList,100));
    }

    @Test
    public void testSortStArray(){
        TestUtils.printTestN("testSortStArray",testN(testStArray,100));
    }

    @Test
    public void testSortStList(){
        TestUtils.printTestN("testSortStList",testN(testStList,100));
    }

    static{
        testStArray=()->{
            Integer[] array=generateRandomIntArray(ARRAY_SIZE);

            //сортируем массив и замеряем время работы
            long startTime=System.nanoTime();
            Arrays.sort(array);
            long estimatedTime=System.nanoTime()-startTime;

            // проверяем правильность сортировки
            for(int i=1;i<array.length;i++){
                if(array[i-1]>array[i]){
                    throw new IllegalArgumentException(Arrays.toString(array)+"\nElement "+array[i]+" at "+i+" position is not in the order");
                }
            }
            return estimatedTime;
        };
        testArray=()->{
            Integer[] array=generateRandomIntArray(ARRAY_SIZE);

            //сортируем массив и замеряем время работы
            long startTime=System.nanoTime();
            FGSort.sort(array);
            long estimatedTime=System.nanoTime()-startTime;

            // проверяем правильность сортировки
            for(int i=1;i<array.length;i++){
                if(array[i-1]>array[i]){
                    throw new IllegalArgumentException(Arrays.toString(array)+"\nElement "+array[i]+" at "+i+" position is not in the order");
                }
            }
            return estimatedTime;
        };
        testList=()->{
            Integer[] array=generateRandomIntArray(ARRAY_SIZE);
            List<Integer> list=Arrays.asList(array);

            //сортируем список и замеряем время работы
            long startTime=System.nanoTime();
            FGSort.sort(list);
            long estimatedTime=System.nanoTime()-startTime;

            // проверяем правильность сортировки
            for(int i=1;i<list.size();i++){
                assertTrue("Element "+list.get(i)+" at "+i+" position is not in the order",list.get(i-1)<=list.get(i));
            }
            return estimatedTime;
        };
        testStList=()->{
            Integer[] array=generateRandomIntArray(ARRAY_SIZE);
            List<Integer> list=Arrays.asList(array);

            //сортируем список и замеряем время работы
            long startTime=System.nanoTime();
            Collections.sort(list);
            long estimatedTime=System.nanoTime()-startTime;

            // проверяем правильность сортировки
            for(int i=1;i<list.size();i++){
                assertTrue("Element "+list.get(i)+" at "+i+" position is not in the order",list.get(i-1)<=list.get(i));
            }
            return estimatedTime;
        };
    }
}