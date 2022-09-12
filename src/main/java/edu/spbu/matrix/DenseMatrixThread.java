//Uzery Game Studio 2019-2022
//documentation = false

package edu.spbu.matrix;

public class DenseMatrixThread extends Thread{
    private final int M,N;
    private final int jj;
    private final int[] res;
    private final Matrix m1,m2;

    public DenseMatrixThread(Matrix m1,Matrix m2,int j,int M,int N){
        this.jj=j;
        this.m1=m1;
        this.m2=m2;
        res=new int[N];
        this.M=M;
        this.N=N;
    }

    @Override
    public void run(){
        for(int i=0;i<N;i++){
            for(int k=0;k<M;k++){
                res[i]+=m1.get(k,i)*m2.get(jj,k);
            }
        }
    }

    public int[] get(){
        return res;
    }
}
