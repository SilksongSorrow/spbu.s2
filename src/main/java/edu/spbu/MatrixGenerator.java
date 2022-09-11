package edu.spbu;

import edu.spbu.matrix.DenseMatrix;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

public class MatrixGenerator{
    public static final int SEED1=1;
    public static final int SEED2=2;
    public static final int EMPTY_ROW_FRACTION=10;

    public static final String MATRIX1_NAME="m1.txt";
    public static final String MATRIX2_NAME="m2.txt";
    public static final String RESULT_NAME="result.txt";
    public static final int SIZE=200;

    private final int emptyRowFraction;
    private final int size;
    private final String emptyRow;
    private final Random rnd;
    private final String filename;

    private int maxN=10_000;

    public MatrixGenerator(int seed,int emptyRowFraction,String filename,int size,int maxN){
        this.emptyRowFraction=emptyRowFraction;
        this.size=size;
        this.filename=filename;
        this.maxN=maxN;
        rnd=new Random(seed);
        emptyRow=String.join(" ",Collections.nCopies(size,"0"));
    }

    public MatrixGenerator(int seed,int emptyRowFraction,String filename,int size){
        this.emptyRowFraction=emptyRowFraction;
        this.size=size;
        this.filename=filename;
        rnd=new Random(seed);
        emptyRow=String.join(" ",Collections.nCopies(size,"0"));
    }

    public static void main(String[] args){
        try{
            new MatrixGenerator(SEED1,EMPTY_ROW_FRACTION,MATRIX1_NAME,SIZE).generate();
            new MatrixGenerator(SEED2,EMPTY_ROW_FRACTION,MATRIX2_NAME,SIZE).generate();
            PrintWriter wr=new PrintWriter(RESULT_NAME);
            wr.println(new DenseMatrix(MATRIX1_NAME).mul(new DenseMatrix(MATRIX2_NAME)).toString());
            wr.close();
        }catch(IOException e){
            System.out.println("Fail to generate matrix file: "+e);
        }
    }

    public void generate() throws IOException{
        PrintWriter out=new PrintWriter(new FileWriter(filename));
        for(int i=0;i<size;i++){
            // only 1/emptyRowFraction will have non 0 values
            if(rnd.nextInt(emptyRowFraction)==0) out.println(generateRow());
            else out.println(emptyRow);
        }
        out.close();
    }

    private String generateRow(){
        return rnd.ints(0,emptyRowFraction).limit(size).mapToObj(r->(r==0) ? ""+rnd.nextInt(maxN):"0").collect(Collectors.joining(" "));
    }

}
