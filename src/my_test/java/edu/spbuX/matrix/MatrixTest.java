package edu.spbuX.matrix;

import edu.spbuX.MatrixGenerator;
import edu.spbuX.TestUtils;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;

import static edu.spbuX.MatrixGenerator.*;
import static edu.spbuX.TestUtils.testN;

import static edu.spbuX.matrix.MatrixConst.*;

public class MatrixTest{
    /**
     * ожидается 4 таких теста
     */
    @Test
    public void mul(){
        TestUtils.printTestN("mulDD",testN(test.apply(0),TESTS_N));
        TestUtils.printTestN("mulDS",testN(test.apply(1),TESTS_N));
        TestUtils.printTestN("mulSD",testN(test.apply(2),TESTS_N));
        TestUtils.printTestN("mulSS",testN(test.apply(3),TESTS_N));
    }

    @Test
    public void mulD(){
        TestUtils.printTestN("d-mulDD",testN(testT.apply(0),TESTS_N));
        TestUtils.printTestN("d-mulDS",testN(testT.apply(1),TESTS_N));
        TestUtils.printTestN("d-mulSD",testN(testT.apply(2),TESTS_N));
        TestUtils.printTestN("d-mulSS",testN(testT.apply(3),TESTS_N));
    }

    private static final Function<Integer,Supplier<Long>> test;
    private static final Function<Integer,Supplier<Long>> testT;

    static{
        test=(id_i)->()->{
            gen();
            Matrix m1=id_i/2==0 ? new DenseMatrix(MATRIX1_NAME):new SparseMatrix(MATRIX1_NAME);
            Matrix m2=id_i%2==0 ? new DenseMatrix(MATRIX2_NAME):new SparseMatrix(MATRIX2_NAME);
            Matrix expected=new DenseMatrix(RESULT_NAME);

            long startTime=System.nanoTime();
            Matrix m=m1.mul(m2);
            long tt=System.nanoTime()-startTime;
            if(!expected.equals(m)){
                throw new IllegalArgumentException("\n\n"+m1+"\n\n"+m2+"\n\n"+expected+"\n\n"+m);
            }
            return tt;
        };

        testT=(id_i)->()->{
            gen();
            Matrix m1=id_i/2==0 ? new DenseMatrix(MATRIX1_NAME):new SparseMatrix(MATRIX1_NAME);
            Matrix m2=id_i%2==0 ? new DenseMatrix(MATRIX2_NAME):new SparseMatrix(MATRIX2_NAME);
            Matrix expected=new DenseMatrix(RESULT_NAME);

            long startTime=System.nanoTime();
            Matrix m=m1.dMul(m2);
            long tt=System.nanoTime()-startTime;
            if(!expected.equals(m)){
                throw new IllegalArgumentException("\n\n"+m1+"\n\n"+m2+"\n\n"+expected+"\n\n"+m);
            }
            return tt;
        };
    }

    private static void gen(){
        MatrixGenerator.gen(MATRIX1_NAME);
        MatrixGenerator.gen(MATRIX2_NAME);
        print(new SparseMatrix(MATRIX1_NAME).mul(new SparseMatrix(MATRIX2_NAME)));
    }
}
