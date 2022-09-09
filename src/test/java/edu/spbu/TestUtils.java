//Uzery Game Studio 2019-2022
//documentation = false

package edu.spbu;

import java.util.Random;
import java.util.function.Supplier;

public class TestUtils{
    /**
     * @param size array size
     * @param seed for the pseudo random generator
     * @return random generated int array. It will be the same for the same seed and size.
     */
    public static Integer[] generateRandomIntArray(int size,long seed){
        Integer[] array=new Integer[size];
        Random rnd=new Random(seed);
        for(int i=0;i<array.length;i++){
            array[i]=rnd.nextInt();
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
}
