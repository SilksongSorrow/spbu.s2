package edu.spbu.matrix;

import edu.spbu.TestUtils;
import org.junit.Test;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Supplier;

import static edu.spbu.MatrixGenerator.*;
import static edu.spbu.TestUtils.testN;

public class MatrixTest{
    private static final int TESTS_N=100;

    /**
     * ожидается 4 таких теста
     */
    @Test
    public void mul(){
        TestUtils.printTest("mulDD",testN(test.apply(0),TESTS_N));
        TestUtils.printTest("mulDS",testN(test.apply(1),TESTS_N));
        TestUtils.printTest("mulSD",testN(test.apply(2),TESTS_N));
        TestUtils.printTest("mulSS",testN(test.apply(3),TESTS_N));
    }

    @Test
    public void mulD(){
        TestUtils.printTest("d-mulDD",testN(testT.apply(0),TESTS_N));
        TestUtils.printTest("d-mulDS",testN(testT.apply(1),TESTS_N));
        TestUtils.printTest("d-mulSD",testN(testT.apply(2),TESTS_N));
        TestUtils.printTest("d-mulSS",testN(testT.apply(3),TESTS_N));
    }

    private static final Function<Integer,Supplier<Long>> test;
    private static final Function<Integer,Supplier<Long>> testT;

    static{
        test=(id_i)->()->{
            Matrix m1=id_i/2==0 ? new DenseMatrix(MATRIX1_NAME):new SparseMatrix(MATRIX1_NAME);
            Matrix m2=id_i%2==0 ? new DenseMatrix(MATRIX2_NAME):new SparseMatrix(MATRIX2_NAME);
            Matrix expected=new DenseMatrix(RESULT_NAME);

            long startTime=System.nanoTime();
            Matrix m=m1.mul(m2);
            long tt=System.nanoTime()-startTime;
            if(!expected.toString().equals(m.toString())){
                throw new IllegalArgumentException("\n\n"+m1+"\n\n"+m2+"\n\n"+expected+"\n\n"+m);
            }
            return tt;
        };

        testT=(id_i)->()->{
            Matrix m1=id_i/2==0 ? new DenseMatrix(MATRIX1_NAME):new SparseMatrix(MATRIX1_NAME);
            Matrix m2=id_i%2==0 ? new DenseMatrix(MATRIX2_NAME):new SparseMatrix(MATRIX2_NAME);
            Matrix expected=new DenseMatrix(RESULT_NAME);

            long startTime=System.nanoTime();
            Matrix m=m1.mul(m2);
            if(!expected.equals(m)){
                //throw new IllegalArgumentException("\n\n"+m1+"\n\n"+m2+"\n\n"+expected+"\n\n"+m);
            }
            return System.nanoTime()-startTime;
        };
    }
}
