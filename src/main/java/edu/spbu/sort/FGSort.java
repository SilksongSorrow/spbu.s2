package edu.spbu.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FGSort{
    //be more slow than primitives, but universal
    public static <T extends Comparable<? super T>> void sort(T[] array){
        Arrays.sort(array);
    }

    //be more slow than primitives, but universal
    public static <T extends Comparable<? super T>> void sort(List<T> list){
        Collections.sort(list);
    }
}
