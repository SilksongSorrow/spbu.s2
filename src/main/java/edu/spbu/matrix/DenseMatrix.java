package edu.spbu.matrix;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import static edu.spbu.matrix.DSMatrixUtils.*;

/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix{
    private final int[][] input;
    private final int width, height;

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
        width=new StringTokenizer(in.get(0)).countTokens();
        height=in.size();
        input=new int[width][height];

        int jj=0;
        while(in.size()>0){
            String s=in.remove(0);
            StringTokenizer st=new StringTokenizer(s);
            int ii=0;
            while(st.hasMoreTokens()){
                String t=st.nextToken();
                input[ii][jj]=Integer.parseInt(t);
                ii++;
            }
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
        if(m instanceof DenseMatrix) return mulDense(this,(DenseMatrix)m);
        if(m instanceof SparseMatrix) return mulDenseSparse(this,(SparseMatrix)m);
        throw new IllegalArgumentException("wrong type: "+m);
    }

    /**
     * многопоточное умножение матриц
     *
     * @param m
     * @return
     */
    @Override
    public Matrix dMul(Matrix m){
        if(m instanceof DenseMatrix) return dMulDense(this,(DenseMatrix)m);
        if(m instanceof SparseMatrix) return dMulDenseSparse(this,(SparseMatrix)m);
        throw new IllegalArgumentException("wrong type: "+m);
    }

    /**
     * сравнивает с обоими вариантами
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Matrix)) return false;
        if(!equalsWH((Matrix)o)) return false;
        if(o instanceof SparseMatrix) return equalsSparse((SparseMatrix)o);
        if(o instanceof DenseMatrix) return equalsDense((DenseMatrix)o);
        throw new IllegalArgumentException("wrong type of matrix: "+o);
    }

    private boolean equalsDense(DenseMatrix other){
        for(int i=0;i<this.width;i++){
            for(int j=0;j<this.height;j++){
                if(this.input[i][j]!=other.input[i][j]) return false;
            }
        }
        return true;
    }

    private boolean equalsSparse(SparseMatrix other){
        //todo think: return toSparse(this).equals(other);
        return this.equalsDense(toDense(other));
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                sb.append(input[j][i]);
                sb.append(" ");
            }
            sb.delete(sb.length()-1,sb.length());
            sb.append("\n");
        }
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }
}
