import java.io.*;
import java.util.Scanner;

import java_cup.runtime.Symbol;

public class Main {

    public static void main(String[] args) {

        try {

            // Hago una copia del archivo .asg
            Scanner scanner = new Scanner(System.in);
            File file = new File("copy.asg");
            FileOutputStream outputStream = new FileOutputStream(file);
            PrintWriter writer = new PrintWriter(outputStream);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                writer.println(line);
            }

            writer.close();
            outputStream.close();

            /* Paso el archivo output.asg por el analizador lexico */
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            Lexico analizadorJFlex = new Lexico(buffer);

            boolean lexical_errors = false;
            while (true) {
                // Obtener el token analizado
                Symbol token = analizadorJFlex.next_token();

                // no hay mas tokens que leer
                if (token.sym == 0) {
                    break;
                } else if (token.sym == 48) {
                    // si es un TkError
                    if (!lexical_errors)
                        lexical_errors = true;
                }
            }

            // si no ocurre ningun error lexico, paso el archivo al parser
            if (!lexical_errors) {
                try {
                    buffer = new BufferedReader(new FileReader(file));
                    parser p = new parser(new Lexico(buffer));
                    p.parse();

                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }

            file.deleteOnExit();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
