//Uzery Game Studio 2019-2022
//documentation = false

package edu.spbu.matrix;

public class MatrixThread extends Thread{
    private final int M;
    private final int ii, jj;
    private int res;
    private Matrix m1,m2;

    public MatrixThread(Matrix m1,Matrix m2,int i,int j,int M){
        this.ii=i;
        this.jj=j;
        this.m1=m1;
        this.m2=m2;
        res=0;
        this.M=M;
    }

    @Override
    public void run(){
        for(int k=0;k<M;k++){
            res+=m1.get(k,ii)*m2.get(jj,k);
        }
    }

    public int get(){
        return res;
    }
}
