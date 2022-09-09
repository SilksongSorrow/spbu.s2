package edu.spbu.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FGSort{
    public static <T extends Comparable<? super T>> void sort(T[] array){
        Arrays.sort(array);
    }

    public static <T extends Comparable<? super T>> void sort(List<T> list){
        Collections.sort(list);
    }
}
