import java.io.*;
import java.util.Scanner;

import java_cup.runtime.Symbol;

public class Main {

    public static void main(String[] args) {

        try {

            // Hago una copia del archivo .asg
            String filepath = args[0];
            /* Paso el archivo por el analizador lexico */
            BufferedReader buffer = new BufferedReader(new FileReader(filepath));
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
                    buffer = new BufferedReader(new FileReader(filepath));
                    parser p = new parser(new Lexico(buffer));
                    p.parse();

                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
