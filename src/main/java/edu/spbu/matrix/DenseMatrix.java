package edu.spbu.matrix;

/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix{
    /**
     * загружает матрицу из файла
     *
     * @param filename имя файла
     */
    public DenseMatrix(String filename){

    }

    /**
     * однопоточное умножение матриц должно поддерживаться для всех 4-х вариантов
     *
     * @param m
     * @return
     */
    @Override
    public Matrix mul(Matrix m){
        return null;
    }

    /**
     * многопоточное умножение матриц
     *
     * @param m
     * @return
     */
    @Override
    public Matrix dMul(Matrix m){
        return null;
    }

    /**
     * сравнивает с обоими вариантами
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        return false;
    }

}
