package edu.spbu.matrix;

import org.junit.Test;

import java.util.function.Supplier;

import static edu.spbu.TestUtils.testN;
import static org.junit.Assert.assertEquals;

public class MatrixTest{
    /**
     * ожидается 4 таких теста
     */
    @Test
    public void mulDD(){
        System.out.println("Round execution time(ms) - mulDD(): "+(testN(testDD,1)/1000000));
    }

    private static final Supplier<Long> testDD;

    static{
        testDD=()->{
            Matrix m1=new DenseMatrix("m1.txt");
            Matrix m2=new DenseMatrix("m2.txt");
            Matrix expected=new DenseMatrix("result.txt");

            long startTime=System.nanoTime();
            assertEquals(expected,m1.mul(m2));
            return System.nanoTime()-startTime;
        };
    }
}
