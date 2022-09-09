package edu.spbu;

import org.junit.After;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatrixGeneratorTest{
    public static final String filename="test.txt";

    @After
    public void cleanUp(){
        File f=new File(filename);
        if(f.exists()) f.delete();
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
