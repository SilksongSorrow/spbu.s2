//Uzery Game Studio 2019-2022
//documentation = false

package edu.spbuX;

import edu.spbuX.utils.LongLong;

import java.util.Random;
import java.util.function.Supplier;

public final class TestUtils{
    private TestUtils(){ /* no instance */ }

    /**
     * @param size array size
     * @return random generated int array. It will be the same for the same seed and size.
     */
    public static Integer[] generateRandomIntArray(int size){
        Integer[] array=new Integer[size];
        Random rnd=new Random();
        for(int i=0;i<array.length;i++){
            array[i]=rnd.nextInt();
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

    public static LongLong testN(Supplier<Long> nano,int size){
        long sum=0;
        long[] ts=new long[size];
        for(int i=0;i<size;i++){
            ts[i]=nano.get();
            sum+=ts[i];
        }
        long t=sum/size;

        long d=0;
        for(int i=0;i<size;i++){
            d+=Math.abs(ts[i]-t);
        }
        return new LongLong(t,d/size);
    }

    public static void printTestN(String name,LongLong t){
        System.out.println("Execution time - "+name+"(): "+t.x()/1000*0.001+"+-"+t.y()/1000*0.001+" ms");
    }
    public static void printTest(String name,long t){
        System.out.println("Execution time - "+name+"(): "+t/1000*0.001+" ms");
    }
}
