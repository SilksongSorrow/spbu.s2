//Uzery Game Studio 2019-2022
//documentation = false

package edu.spbu.matrix;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public final class DSMatrixUtils{
    private DSMatrixUtils(){ /* no instance */ }

    public static Matrix mulDense(DenseMatrix m1,DenseMatrix m2){
        //System.out.println("dn");
        int N=m1.height();
        int M=m1.width();
        int M2=m2.height();
        int K=m2.width();

        if(M!=M2) throw new IllegalArgumentException("can't: "+N+" "+M+" "+M2+" "+K);

        int[][] mx=new int[K][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<K;j++){
                for(int k=0;k<M;k++){
                    mx[j][i]+=m1.get(k,i)*m2.get(j,k);
                }
            }
        }
        return new DenseMatrix(mx,N,K);
    }

    public static Matrix mulDenseSparse(DenseMatrix m1,SparseMatrix m2){
        return mulSparse(toSparse(m1),m2);
    }
    public static Matrix mulSparseDense(SparseMatrix m1,DenseMatrix m2){
        return mulSparse(m1,toSparse(m2));
    }

    public static Matrix mulSparse(SparseMatrix m1,SparseMatrix m2){
        //System.out.println("sp");
        int N=m1.height();
        int M=m1.width();
        int M2=m2.height();
        int K=m2.width();

        if(M!=M2) throw new IllegalArgumentException("can't: "+N+" "+M+" "+M2+" "+K);

        LinkedList<SparseMatrixValue> res=new LinkedList<>();
        for(int i: m1.strokesKeys()){
            for(int j: m2.rowsKeys()){
                List<SparseMatrixValue> stroke=m1.stroke(i);
                List<SparseMatrixValue> row=m2.row(j);
                int l1=0;
                int l2=0;
                int v=0;
                while(l1<stroke.size() && l2<row.size()){
                    int n1=stroke.get(l1).x();
                    int n2=row.get(l2).y();
                    if(n1<n2){
                        l1++;
                        continue;
                    }
                    if(n1>n2){
                        l2++;
                        continue;
                    }
                    v+=stroke.get(l1).value()*row.get(l2).value();
                    l1++;
                    l2++;
                }
                if(v!=0) res.add(new SparseMatrixValue(j,i,v));
            }
        }
        return new SparseMatrix(res,N,K);
    }

    public static Matrix dMulDense(DenseMatrix m1,DenseMatrix m2){
        int N=m1.height();
        int M=m1.width();
        int M2=m2.height();
        int K=m2.width();

        if(M!=M2) throw new IllegalArgumentException("can't: "+N+" "+M+" "+M2+" "+K);

        int[][] mx=new int[N][K];
        LinkedList<MatrixThread> threads=new LinkedList<>();
        for(int i=0;i<N;i++){
            for(int j=0;j<K;j++){
                threads.add(new MatrixThread(m1,m2,i,j,M));
                threads.getLast().start();
            }
        }
        LinkedList<MatrixThread> sav=new LinkedList<>(threads);
        while(threads.size()>0){
            MatrixThread t=threads.removeFirst();
            if(t.isAlive())threads.addLast(t);
        }
        for(int i=0;i<N;i++){
            for(int j=0;j<K;j++){
                mx[i][j]=sav.get(i*N+j).get();
            }
        }
        return new DenseMatrix(mx,N,K);
    }

    public static Matrix dMulDenseSparse(DenseMatrix m1,SparseMatrix m2){
        return null;
    }

    public static Matrix dMulSparse(SparseMatrix m1,SparseMatrix m2){
        return null;
    }

    public static SparseMatrix toSparse(DenseMatrix m){
        LinkedList<SparseMatrixValue> values=new LinkedList<>();
        for(int i=0;i<m.width();i++){
            for(int j=0;j<m.height();j++){
                if(m.get(i,j)!=0) values.add(new SparseMatrixValue(i,j,m.get(i,j)));
            }
        }
        return new SparseMatrix(values,m.width(),m.height());
    }

    public static DenseMatrix toDense(SparseMatrix m){
        int[][] mx=new int[m.width()][m.height()];
        for(List<SparseMatrixValue> vs: m.strokes()){
            for(SparseMatrixValue v: vs){
                mx[v.x()][v.y()]=v.value();
            }
        }
        return new DenseMatrix(mx,m.width(),m.height());
    }
}