//Uzery Game Studio 2019-2022
//documentation = false

package edu.spbu;

import java.util.Random;
import java.util.function.Supplier;

public final class TestUtils{
    private TestUtils(){ /* no instance */ }

    /**
     * @param size array size
     * @return random generated int array. It will be the same for the same seed and size.
     */
    public static Integer[] generateRandomIntArray(int size){
        Integer[] array=new Integer[size*2];
        Random rnd=new Random();
        for(int i=0;i<array.length;i+=2){
            array[i]=rnd.nextInt();
            array[i+1]=array[i];
        }
        return array;
    }

    public static Integer[] generateRandomIntArray2(int size){
        Integer[] array=new Integer[size*2];
        Random rnd=new Random();
        for(int i=0;i<array.length;i+=2){
            array[i]=rnd.nextInt();
            array[i+1]=array[i];
        }
        return array;
    }

    public static long testN(Supplier<Long> s,int size){
        long estimatedTime=0;
        for(int i=0;i<size;i++){
            estimatedTime+=s.get();
        }
        return estimatedTime/size;
    }

    public static void printTest(String name,long t){
        System.out.println("Execution time - "+name+"(): "+t/1000*0.001+" ms");
    }
}
