import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestingClass{
    public static void main(String[] args) throws IOException{
        System.out.println("Start process");
        run1();
        System.out.println("End process");
    }

    private static void run1() throws IOException{
        BufferedReader rd=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("reverse: "+reverse(rd.readLine()));
    }

    private static String reverse(String in){
        StringBuilder b=new StringBuilder();
        for(int i=in.length()-1;i>=0;i--){
            b.append(in.charAt(i));
        }
        return b.toString();
    }
}
