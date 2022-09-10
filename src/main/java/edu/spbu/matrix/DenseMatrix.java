package edu.spbu.matrix;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix{
    private final int[][] input;
    private final int width,height;

    /**
     * загружает матрицу из файла
     *
     * @param filename имя файла
     */
    public DenseMatrix(String filename){
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
        input=new int[width][height];

        int jj=0;
        String s=in.remove(0);
        while(s!=null){
            StringTokenizer st=new StringTokenizer(s," ");
            int ii=0;
            while(st.hasMoreTokens()){
                String t=st.nextToken();
                if(t.equals("0"))continue;
                input[ii][jj]=Integer.parseInt(t);
                ii++;
            }
            s=in.remove(0);
            jj++;
        }
    }
    public DenseMatrix(int[][] input,int width,int height){
        this.input=input;
        this.width=width;
        this.height=height;
    }

    @Override
    public int get(int x,int y){ return input[x][y]; }

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
    private boolean equalsDense(DenseMatrix other){
        for(int i=0;i<this.width;i++){
            for(int j=0;j<this.height;j++){
                if(this.input[i][j]!=other.input[i][j])return false;
            }
        }
        return true;
    }
    private boolean equalsSparse(SparseMatrix other){
        //todo think: return toSparse(this).equals(other);
        return this.equalsDense(SparseMatrix.toDense(other));
    }

    public static SparseMatrix toSparse(DenseMatrix m){
        LinkedList<SparseMatrixValue> values=new LinkedList<>();
        for(int i=0;i<m.width;i++){
            for(int j=0;j<m.height;j++){
                if(m.input[i][j]!=0)values.add(new SparseMatrixValue(i,j,m.input[i][j]));
            }
        }
        return new SparseMatrix(values,m.width,m.height);
    }
}
