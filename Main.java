import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        parser p = new parser(new Lexico(buffer));

        try {
            p.parse();
            
        } catch (Exception e) {
            System.out.println("Caught an exception");
        }
    }

}
