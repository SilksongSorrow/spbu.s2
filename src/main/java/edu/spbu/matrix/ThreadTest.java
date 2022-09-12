//Uzery Game Studio 2019-2022
//documentation = false

package edu.spbu.matrix;

public class ThreadTest{
    private static final Object lock=new Object();
    private static final Thread te=new Thread(()->{
        synchronized(lock){
            for(int i=0;i<5;i++){
                System.out.println(1);
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    });

    public static void main(String[] args){
        te.start();
        synchronized(lock){
            for(int i=0;i<5;i++){
                System.out.println(2);
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
