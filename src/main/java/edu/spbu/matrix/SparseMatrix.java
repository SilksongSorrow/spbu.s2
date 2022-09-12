package edu.spbu.matrix;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static edu.spbu.matrix.DSMatrixUtils.*;

/**
 * Разряженная матрица
 */
public class SparseMatrix implements Matrix{
    private final List<SparseMatrixValue> values;
    private Map<Integer,List<SparseMatrixValue>> strokes;
    private Map<Integer,List<SparseMatrixValue>> rows;
    private final int width, height;

    public List<SparseMatrixValue> row(int key){ return rows.get(key); }
    public List<SparseMatrixValue> stroke(int key){ return strokes.get(key); }

    public Set<Integer> rowsKeys(){ return rows.keySet(); }
    public Set<Integer> strokesKeys(){ return strokes.keySet(); }

    public Set<List<SparseMatrixValue>> rows(){
        HashSet<List<SparseMatrixValue>> set=new HashSet<>();
        for(int key: rowsKeys())set.add(rows.get(key));
        return set;
    }
    public Set<List<SparseMatrixValue>> strokes(){
        HashSet<List<SparseMatrixValue>> set=new HashSet<>();
        for(int key: strokesKeys())set.add(strokes.get(key));
        return set;
    }

    /**
     * загружает матрицу из файла
     *
     * @param filename
     */
    public SparseMatrix(String filename){
        List<String> in;
        try{
            BufferedReader rd=new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            in=rd.lines().collect(Collectors.toList());
        }catch(IOException e){
            e.printStackTrace();
            throw new IllegalArgumentException("CANT RUN FROM: "+filename);
        }
        width=new StringTokenizer(in.get(0)).countTokens();
        height=in.size();
        values=new LinkedList<>();

        int jj=0;
        while(in.size()>0){
            String s=in.remove(0);
            StringTokenizer st=new StringTokenizer(s," ");
            int ii=0;
            while(st.hasMoreTokens()){
                String t=st.nextToken();
                if(t.equals("0")){
                    ii++;
                    continue;
                }
                values.add(new SparseMatrixValue(ii,jj,Integer.parseInt(t)));
                ii++;
            }
            jj++;
        }
        init();
    }

    public SparseMatrix(List<SparseMatrixValue> values,int width,int height){
        this.values=values;
        this.width=width;
        this.height=height;
        init();
        //System.out.println(strokes);
        //todo checking matrix out of range
    }

    private void init(){
        strokes=new HashMap<>();
        rows=new HashMap<>();
        for(SparseMatrixValue v: values){
            if(!strokes.containsKey(v.y()))strokes.put(v.y(),new LinkedList<>());
            strokes.get(v.y()).add(v);

            if(!rows.containsKey(v.x()))rows.put(v.x(),new LinkedList<>());
            rows.get(v.x()).add(v);
        }
        for(List<SparseMatrixValue> l: strokes.values()){
            l.sort(Comparator.comparingInt(SparseMatrixValue::y));
        }
        for(List<SparseMatrixValue> l: rows.values()){
            l.sort(Comparator.comparingInt(SparseMatrixValue::x));
        }
    }

    @Override
    public int get(int x,int y){
        if(!strokes.containsKey(y))return 0;
        return strokes.get(y).stream().filter(o->o.x()==x).findFirst().orElse(SparseMatrixValue.ZERO).value();
    }

    @Override
    public int width(){ return width; }

    @Override
    public int height(){ return height; }

    /**
     * однопоточное умножение матриц должно поддерживаться для всех 4-х вариантов
     *
     * @param m
     * @return
     */
    @Override
    public Matrix mul(Matrix m){
        if(m instanceof SparseMatrix) return mulSparse(this,(SparseMatrix)m);
        if(m instanceof DenseMatrix) return mulSparseDense(this,(DenseMatrix)m);
        throw new IllegalArgumentException("wrong type: "+m);
    }

    /**
     * многопоточное умножение матриц
     *
     * @param m
     * @return
     */
    @Override
    public Matrix dMul(Matrix m){
        if(m instanceof SparseMatrix) return dMulSparse(this,(SparseMatrix)m);
        if(m instanceof DenseMatrix) return dMulSparseDense(this,(DenseMatrix)m);
        throw new IllegalArgumentException("wrong type: "+m);
    }

    /**
     * сравнивает с обоими вариантами
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Matrix)) return false;
        if(!equalsWH((Matrix)o)) return false;
        if(o instanceof SparseMatrix) return equalsSparse((SparseMatrix)o);
        if(o instanceof DenseMatrix) return equalsDense((DenseMatrix)o);
        throw new IllegalArgumentException("wrong type of matrix: "+o);
    }

    private boolean equalsSparse(SparseMatrix other){
        LinkedList<SparseMatrixValue> list1=new LinkedList<>(values);
        LinkedList<SparseMatrixValue> list2=new LinkedList<>(other.values);
        if(list1.size()!=list2.size()) return false;
        while(list1.size()>0){
            if(!list2.remove(list1.removeFirst())) return false;
        }
        return true;
    }

    private boolean equalsDense(DenseMatrix other){
        return toDense(this).equals(other);
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                sb.append(get(j,i));
                sb.append(" ");
            }
            sb.delete(sb.length()-1,sb.length());
            sb.append("\n");
        }
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }
}
