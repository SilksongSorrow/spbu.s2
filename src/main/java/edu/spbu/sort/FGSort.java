package edu.spbu.sort;

import java.util.*;

public class FGSort{
    //be slower than primitives, but more universal
    public static <T extends Comparable<? super T>> void sort(T[] array){
        LinkedList<Integer> ls=new LinkedList<>();
        ls.add(0);
        LinkedList<Integer> rs=new LinkedList<>();
        rs.add(array.length-1);
        while(ls.size()>0){
            int l=ls.removeLast();
            int r=rs.removeLast();
            if(r-l<=0)continue;
            if(r-l==1){
                if(array[l].compareTo(array[r])>0)swap(array,l,r);
                continue;
            }
            int n=(l+r)/2;
            T x=array[n];
            int li=l,ri=r;
            while(li<ri){
                while(array[li].compareTo(x)<0)li++;
                while(array[ri].compareTo(x)>0)ri--;

                if(li<ri){
                    swap(array,li,ri);
                    li++;
                    ri--;
                }
            }
            ls.addLast(l);rs.addLast(ri);
            ls.addLast(li);rs.addLast(r);
        }
    }

    private static <T> void swap(T[] array,int l,int r){
        T sav=array[r];
        array[r]=array[l];
        array[l]=sav;
    }

    //be slower than primitives, but more universal
    public static <T extends Comparable<? super T>> void sort(List<T> list){
        LinkedList<Integer> ls=new LinkedList<>();
        ls.add(0);
        LinkedList<Integer> rs=new LinkedList<>();
        rs.add(list.size()-1);
        while(ls.size()>0){
            int l=ls.removeLast();
            int r=rs.removeLast();
            if(r-l<=0)continue;
            if(r-l==1){
                if(list.get(l).compareTo(list.get(r))>0)swap(list,l,r);
                continue;
            }
            int n=(l+r)/2;
            T x=list.get(n);
            int li=l,ri=r;
            while(li<ri){
                while(list.get(li).compareTo(x)<0)li++;
                while(list.get(ri).compareTo(x)>0)ri--;

                if(li<ri){
                    swap(list,li,ri);
                    li++;
                    ri--;
                }
            }
            ls.addLast(l);rs.addLast(ri);
            ls.addLast(li);rs.addLast(r);
        }
    }
    private static <T> void swap(List<T> list,int l,int r){
        T sav=list.get(r);
        list.set(r,list.get(l));
        list.set(l,sav);
    }
}
