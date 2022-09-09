import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Пример программы на Java
 */
// название главного класса, обычно совпадает с названием программы
public class HelloWorld{
    public static void main(String[] args) throws IOException{
        System.out.println("Start process");
        BufferedReader rd=new BufferedReader(new InputStreamReader(System.in));
        String s=rd.readLine();
        System.out.println("reverse: "+reverse(s));
        System.out.println("End process");
    }
    private static String reverse(String in){
        StringBuilder b=new StringBuilder();
        for(int i=in.length()-1;i>=0;i--){
            b.append(in.charAt(i));
        }
        return b.toString();
    }
}
