//Uzery Game Studio 2019-2022
//documentation = false

package edu.spbu.matrix;

public class SparseMatrixValue{
    public static final SparseMatrixValue ZERO=new SparseMatrixValue(-1,-1,0);
    private int x, y;
    private int value;

    public SparseMatrixValue(int x,int y,int value){
        this.x=x;
        this.y=y;
        this.value=value;
    }

    public int x(){ return x; }

    public int y(){ return y; }

    public int value(){ return value; }

    @Override
    public String toString(){
        return "smv["+String.join(" ",x+"",y+"",value+"")+"]";
    }
}
