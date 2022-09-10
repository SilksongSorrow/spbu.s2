//Uzery Game Studio 2019-2022
//documentation = false

package edu.spbu.matrix;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public final class DSMatrixUtils{
    private DSMatrixUtils(){ /* no instance */ }

    public static Matrix mulDense(DenseMatrix m1,DenseMatrix m2){
        int N=m1.height();
        int M=m1.width();
        int M2=m2.height();
        int K=m2.width();

        if(M!=M2) throw new IllegalArgumentException("can't: "+N+" "+M+" "+M2+" "+K);

        int[][] mx=new int[N][K];
        for(int i=0;i<N;i++){
            for(int j=0;j<K;j++){
                for(int k=0;k<M;k++){
                    mx[i][j]+=m1.get(i,k)*m2.get(k,j);
                }
            }
        }
        return new DenseMatrix(mx,N,K);
    }

    public static Matrix mulDenseSparse(DenseMatrix m1,SparseMatrix m2){
        return mulSparse(toSparse(m1),m2);
    }

    public static Matrix mulSparse(SparseMatrix m1,SparseMatrix m2){
        int N=m1.height();
        int M=m1.width();
        int M2=m2.height();
        int K=m2.width();

        if(M!=M2) throw new IllegalArgumentException("can't: "+N+" "+M+" "+M2+" "+K);

        List<SparseMatrixValue> values1=m1.values();
        List<SparseMatrixValue> values2=m2.values();
        LinkedList<List<SparseMatrixValue>> strokes1=new LinkedList<>();
        LinkedList<List<SparseMatrixValue>> rows2=new LinkedList<>();
        boolean[] clear1=new boolean[N];
        boolean[] clear2=new boolean[K];

        for(int i=0;i<N;i++) strokes1.add(new LinkedList<>());
        for(int i=0;i<K;i++) rows2.add(new LinkedList<>());

        for(SparseMatrixValue v: values1){
            strokes1.get(v.y()).add(v);
            clear1[v.y()]=true;
        }
        for(SparseMatrixValue v: values2){
            rows2.get(v.x()).add(v);
            clear2[v.x()]=true;
        }
        for(int i=0;i<N;i++) strokes1.get(i).sort(Comparator.comparingInt(SparseMatrixValue::x));
        for(int i=0;i<K;i++) rows2.get(i).sort(Comparator.comparingInt(SparseMatrixValue::y));

        List<SparseMatrixValue> res=new LinkedList<>();
        for(int i=0;i<N;i++){
            if(clear1[i]) continue;
            for(int j=0;j<K;j++){
                if(clear2[i]) continue;

                List<SparseMatrixValue> stroke=strokes1.get(i);
                List<SparseMatrixValue> row=rows2.get(j);

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
                if(v!=0) res.add(new SparseMatrixValue(i,j,v));
            }
        }
        return new SparseMatrix(res,N,K);
    }

    public static Matrix dMulDense(DenseMatrix m1,DenseMatrix m2){
        return null;
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
        for(SparseMatrixValue v: m.values()){
            mx[v.x()][v.y()]=v.value();
        }
        return new DenseMatrix(mx,m.width(),m.height());
    }
}
