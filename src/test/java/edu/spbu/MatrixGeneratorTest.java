package edu.spbu;

import edu.spbu.matrix.DenseMatrix;
import edu.spbu.matrix.Matrix;
import edu.spbu.matrix.SparseMatrix;
import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.util.Random;
import java.util.function.Supplier;

import static edu.spbu.MatrixGenerator.MATRIX1_NAME;
import static edu.spbu.MatrixGenerator.MATRIX2_NAME;
import static edu.spbu.TestUtils.testN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatrixGeneratorTest{
    public static final String filename="test.txt";

    private static final Supplier<Long> testPerformanceD, testPerformanceS, testPerformanceDeqS;

    private static final int size=2;
    private static final int max_N=2;
    private static final int fr=1;
    private static final int TESTS_N=10;

    private static void gen(){
        /*gen(MATRIX1_NAME);
        gen(MATRIX2_NAME);*/
    }

    private static void gen(String filename){
        try{
            new MatrixGenerator(new Random().nextInt(),fr,filename,size,max_N).generate();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    static{
        testPerformanceD=()->{
            gen();
            // Uncomment the code to Test your library
            //System.out.println("Starting loading dense matrices");
            Matrix m1=new DenseMatrix(MATRIX1_NAME);
            //System.out.println("1 loaded");
            Matrix m2=new DenseMatrix(MATRIX2_NAME);
            //System.out.println("2 loaded");
            long start=System.nanoTime();
            m1.mul(m2);
            return (System.nanoTime()-start);
        };

        testPerformanceDeqS=()->{
            gen();
            Matrix m1=new DenseMatrix(MATRIX1_NAME);
            Matrix m2=new DenseMatrix(MATRIX2_NAME);

            Matrix m3=new SparseMatrix(MATRIX1_NAME);
            Matrix m4=new SparseMatrix(MATRIX2_NAME);

            long start=System.nanoTime();
            Matrix r1=m1.mul(m2);
            Matrix r2=m3.mul(m4);
            if(r1.equals(r2)) System.out.println("!!!");
            return (System.nanoTime()-start);
        };

        testPerformanceS=()->{
            gen();
            //System.out.println("Starting loading sparse matrices");
            Matrix m1=new SparseMatrix(MATRIX1_NAME);
            //System.out.println("1 loaded");
            Matrix m2=new SparseMatrix(MATRIX2_NAME);
            //System.out.println("2 loaded");
            long start=System.nanoTime();
            m1.mul(m2);
            return (System.nanoTime()-start);
        };
    }

    @After
    public void cleanUp(){
        File f=new File(filename);
        boolean b=true;
        if(f.exists()) b=f.delete();
        if(!b) System.out.println("!");
    }

    @Test
    public void testPerformanceD(){
        TestUtils.printTest("testPerformanceD",testN(testPerformanceD,TESTS_N));
    }

    @Test
    public void testPerformanceS(){
        TestUtils.printTest("testPerformanceS",testN(testPerformanceS,TESTS_N));
    }

    @Test
    public void testPerformanceDeqS(){
        TestUtils.printTest("testPerformanceDeqS",testN(testPerformanceDeqS,TESTS_N));
    }


    @Test
    public void testGenerate() throws IOException{
        new MatrixGenerator(1,3,filename,10).generate();
        BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        int lineCount=0;
        int emptyLineCount=0;
        for(String line=reader.readLine();line!=null;line=reader.readLine()){
            if(line.equals("0 0 0 0 0 0 0 0 0 0")){
                emptyLineCount++;
            }else{
                assertEquals(10,line.split(" ").length);
            }
            lineCount++;
        }
        assertTrue(emptyLineCount>1);
        assertEquals(10,lineCount);
    }
}
