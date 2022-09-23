package edu.spbuX.sort;

import edu.spbuX.utils.IntInt;

import java.util.*;

public class FGSort{
    //be slower than primitives, but more universal
    public static <T extends Comparable<? super T>> void sort(T[] array){
        LinkedList<IntInt> lrs=new LinkedList<>();
        lrs.add(new IntInt(0,array.length-1));
        while(lrs.size()>0){
            IntInt lr=lrs.removeLast();
            int l=lr.x();
            int r=lr.y();
            if(r-l<=0) continue;
            if(r-l==1){
                if(array[l].compareTo(array[r])>0) swap(array,l,r);
                continue;
            }
            int n=(l+r)/2;
            T x=array[n];
            int li=l, ri=r;
            while(li<ri){
                while(array[li].compareTo(x)<0) li++;
                while(array[ri].compareTo(x)>0) ri--;

                if(li<ri){
                    swap(array,li,ri);
                    li++;
                    ri--;
                }
            }
            lrs.addLast(new IntInt(l,ri));
            lrs.addLast(new IntInt(li,r));
        }
    }

    private static <T> void swap(T[] array,int l,int r){
        T sav=array[r];
        array[r]=array[l];
        array[l]=sav;
    }

    //be slower than primitives, but more universal
    public static <T extends Comparable<? super T>> void sort(List<T> list){
        LinkedList<IntInt> lrs=new LinkedList<>();
        lrs.add(new IntInt(0,list.size()-1));
        while(lrs.size()>0){
            IntInt lr=lrs.removeLast();
            int l=lr.x();
            int r=lr.y();
            if(r-l<=0) continue;
            if(r-l==1){
                if(list.get(l).compareTo(list.get(r))>0) swap(list,l,r);
                continue;
            }
            int n=(l+r)/2;
            T x=list.get(n);
            int li=l, ri=r;
            while(li<ri){
                while(list.get(li).compareTo(x)<0) li++;
                while(list.get(ri).compareTo(x)>0) ri--;

                if(li<ri){
                    swap(list,li,ri);
                    li++;
                    ri--;
                }
            }
            lrs.addLast(new IntInt(l,ri));
            lrs.addLast(new IntInt(li,r));
        }
    }

    private static <T> void swap(List<T> list,int l,int r){
        T sav=list.get(r);
        list.set(r,list.get(l));
        list.set(l,sav);
    }
}
