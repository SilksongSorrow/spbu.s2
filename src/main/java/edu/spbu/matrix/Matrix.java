package edu.spbu.matrix;

/**
 *
 */
public interface Matrix{
    /**
     * однопоточное умножение матриц должно поддерживаться для всех 4-х вариантов
     *
     * @param m
     * @return
     */
    Matrix mul(Matrix m);

    /**
     * многопоточное умножение матриц
     *
     * @param m
     * @return
     */
    Matrix dMul(Matrix m);

}
