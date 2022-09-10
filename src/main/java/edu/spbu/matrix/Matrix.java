package edu.spbu.matrix;

/**
 *
 */
public interface Matrix{
    int get(int x,int y);
    int width();
    int height();

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
