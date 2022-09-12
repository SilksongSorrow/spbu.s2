package edu.spbu;

import edu.spbu.matrix.DenseMatrix;
import edu.spbu.matrix.Matrix;
import edu.spbu.matrix.SparseMatrix;
import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.util.function.Supplier;

import static edu.spbu.TestUtils.testN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static edu.spbu.matrix.MatrixConst.*;

public class MatrixGeneratorTest{
    public static final String filename="test.txt";

    private static final Supplier<Long> testPerformanceD, testPerformanceS, testPerformanceDeqS;

    private static void gen(){
        MatrixGenerator.gen(MATRIX1_NAME);
        MatrixGenerator.gen(MATRIX2_NAME);
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
        TestUtils.printTestN("testPerformanceD",testN(testPerformanceD,TESTS_N));
    }

    @Test
    public void testPerformanceS(){
        TestUtils.printTestN("testPerformanceS",testN(testPerformanceS,TESTS_N));
    }

    @Test
    public void testPerformanceDeqS(){
        TestUtils.printTestN("testPerformanceDeqS",testN(testPerformanceDeqS,TESTS_N));
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
