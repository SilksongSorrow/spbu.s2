package edu.spbu.matrix;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Разряженная матрица
 */
public class SparseMatrix implements Matrix{
    private final List<SparseMatrixValue> values;
    private final int width,height;

    /**
     * загружает матрицу из файла
     *
     * @param filename
     */
    public SparseMatrix(String filename){
        List<String> in;
        try{
            BufferedReader rd=new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            in=rd.lines().collect(Collectors.toList());
        }catch(IOException e){
            e.printStackTrace();
            throw new IllegalArgumentException("CANT RUN FROM: "+filename);
        }
        width=in.get(0).length();
        height=in.size();
        values=new LinkedList<>();

        int jj=0;
        String s=in.remove(0);
        while(s!=null){
            StringTokenizer st=new StringTokenizer(s," ");
            int ii=0;
            while(st.hasMoreTokens()){
                String t=st.nextToken();
                if(t.equals("0"))continue;
                values.add(new SparseMatrixValue(ii,jj,Integer.parseInt(t)));
                ii++;
            }
            s=in.remove(0);
            jj++;
        }
    }
    public SparseMatrix(List<SparseMatrixValue> values,int width,int height){
        this.values=values;
        this.width=width;
        this.height=height;
        //todo checking matrix out of range
    }

    @Override
    public int get(int x,int y){
        return values.stream().filter(v->v.x()==x && v.y()==y).
                findFirst().orElse(SparseMatrixValue.ZERO).value();
    }

    @Override
    public int width(){ return width; }

    @Override
    public int height(){ return height; }

    /**
     * однопоточное умножение матриц должно поддерживаться для всех 4-х вариантов
     *
     * @param m
     * @return
     */
    @Override
    public Matrix mul(Matrix m){
        return null;
    }

    /**
     * многопоточное умножение матриц
     *
     * @param m
     * @return
     */
    @Override
    public Matrix dMul(Matrix m){
        return null;
    }

    /**
     * сравнивает с обоими вариантами
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Matrix))return false;
        Matrix m=(Matrix)o;
        if(m.width()!=width() || m.height()!=height())return false;
        if(o instanceof SparseMatrix)return equalsSparse((SparseMatrix)o);
        if(o instanceof DenseMatrix)return equalsDense((DenseMatrix)o);
        throw new IllegalArgumentException("wrong type of matrix: "+o);
    }
    private boolean equalsSparse(SparseMatrix other){
        LinkedList<SparseMatrixValue> list1=new LinkedList<>(values);
        LinkedList<SparseMatrixValue> list2=new LinkedList<>(other.values);
        if(list1.size()!=list2.size())return false;
        while(list1.size()>0){
            if(!list2.remove(list1.removeFirst()))return false;
        }
        return true;
    }
    private boolean equalsDense(DenseMatrix other){
        return toDense(this).equals(other);
    }

    public static DenseMatrix toDense(SparseMatrix m){
        int[][] mx=new int[m.width][m.height];
        for(SparseMatrixValue v: m.values){
            mx[v.x()][v.y()]=v.value();
        }
        return new DenseMatrix(mx,m.width,m.height);
    }
}
