package edu.spbu.matrix;

import edu.spbu.MatrixGenerator;
import edu.spbu.TestUtils;
import org.junit.Test;

import java.util.LinkedList;
import java.util.function.Supplier;

import static edu.spbu.MatrixGenerator.*;
import static edu.spbu.TestUtils.testN;
import static org.junit.Assert.assertEquals;

public class MatrixTest{

    private static final int D=0;
    private static final int S=0;

    /**
     * ожидается 4 таких теста
     */
    @Test
    public void mulDD(){
        TestUtils.printTest("mulDD",testN(test.get(getID(D,D)),1));
    }

    @Test
    public void mulDS(){
        TestUtils.printTest("mulDS",testN(test.get(getID(D,S)),1));
    }

    @Test
    public void mulSD(){
        TestUtils.printTest("mulSD",testN(test.get(getID(S,D)),1));
    }

    @Test
    public void mulSS(){
        TestUtils.printTest("mulSS",testN(test.get(getID(S,S)),1));
    }

    private int getID(int a,int b){ return a*2+b; }

    private static final LinkedList<Supplier<Long>> test;

    private static int id_i;

    static{
        test=new LinkedList<>();
        for(id_i=0;id_i<4;id_i++){
            test.add(()->{
                Matrix m1=id_i/2==0 ? new DenseMatrix(MATRIX1_NAME):new SparseMatrix(MATRIX1_NAME);
                Matrix m2=id_i%2==0 ? new DenseMatrix(MATRIX2_NAME):new SparseMatrix(MATRIX2_NAME);
                Matrix expected=new DenseMatrix(RESULT_NAME);

                long startTime=System.nanoTime();
                assertEquals(expected,m1.mul(m2));
                //if(!expected.equals(m1.mul(m2))) throw new IllegalArgumentException("!!!");

                return System.nanoTime()-startTime;
            });
        }
    }
}
