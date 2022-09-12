package edu.spbu;

import edu.spbu.matrix.DenseMatrix;
import edu.spbu.matrix.Matrix;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

import static edu.spbu.matrix.MatrixConst.*;

public class MatrixGenerator{
    private final int emptyRowFraction;
    private final int size;
    private final String emptyRow;
    private final Random rnd;
    private final String filename;

    private int maxN=5;
    public MatrixGenerator(int seed,String filename){
        this(seed,EMPTY_ROW_FRACTION,filename,SIZE,MAX_N);
    }

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
        gen(MATRIX1_NAME);
        gen(MATRIX2_NAME);
        print(new DenseMatrix(MATRIX1_NAME).mul(new DenseMatrix(MATRIX2_NAME)));
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


    public static void gen(String filename){
        try{
            new MatrixGenerator(new Random().nextInt(),filename).generate();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void print(Matrix matrix){
        try{
            PrintWriter wr=new PrintWriter(RESULT_NAME);
            wr.println(matrix.toString());
            wr.close();
        }catch(IOException e){
            System.out.println("Fail to generate matrix file: "+e);
        }
    }
}
