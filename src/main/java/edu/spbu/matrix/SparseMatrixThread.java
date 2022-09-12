//Uzery Game Studio 2019-2022
//documentation = false

package edu.spbu.matrix;

import java.util.LinkedList;
import java.util.List;

public class SparseMatrixThread extends Thread{
    private final int i;
    private final List<SparseMatrixValue> res;
    private final SparseMatrix m1,m2;

    public SparseMatrixThread(SparseMatrix m1,SparseMatrix m2,int i){
        this.i=i;
        this.m1=m1;
        this.m2=m2;
        res=new LinkedList<>();
    }

    @Override
    public void run(){
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

    public List<SparseMatrixValue> get(){
        return res;
    }
}
