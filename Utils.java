import java.util.ArrayList;
import java.util.List;

public class Utils {
    /*Metodo que dado un Canvas (List<List<String>>) retorna la transpocision del mismo */
    public List<List<String>> returnTranspose(List<List<String>> table) {
        /*Inicializa el nuevo canvas transpuesto*/
        List<List<String>> transposedList = new ArrayList<List<String>>();

        /*Obtiene el numero de filas y columnas de la matriz*/
        final int n = table.size();
        final int m = table.get(0).size();

        /*Itera por cada una de las posiciones asignando el valor i,j de table al
        * valor j,i de transposedList*/
        for (int i = 0; i < m; i++) {
            List<String> tempList = new ArrayList<String>();
            for (int j = 0; j < n; j++) {
                tempList.add(table.get(j).get(i));
            }
            transposedList.add(tempList);
        }
        return transposedList;
    }

    /*Metodo que dado un Canvas (List<List<String>>) retorna la Rotacion del mismo */
    public List<List<String>> returnRotacion(List<List<String>> table) {
        /*Inicializa el nuevo canvas rotado*/
        List<List<String>> result = new ArrayList<List<String>>();

        /*Obtiene el numero de filas y columnas de la matriz*/
        final int n = table.size();
        final int m = table.get(0).size();

        /*Cambia cada elemento i,j por su equivalente rotado*/
        for (int i = 0; i < n; i++) {
            List<String> tempList = new ArrayList<String>();
            for (int j = 0; j < m; j++) {
                String cell = table.get(i).get(j);
                switch (cell) {
                    case "<_>":
                        cell = "<|>";
                        break;
                    case "<|>":
                        cell = "<_>";
                        break;
                    case "</>":
                        cell = "<\\>";
                        break;
                    case "<\\>":
                        cell = "</>";
                        break;
                    default:
                        break;
                }
                tempList.add(cell);
            }
            result.add(tempList);
        }
        List<List<String>> result2 = new ArrayList<List<String>>();
        for (int i = 0; i < m; i++) {
            List<String> tempList = new ArrayList<String>();
            for (int j = n - 1; j >= 0; j--) {
                tempList.add(result.get(j).get(i));
            }
            result2.add(tempList);
        }
        return result2;
    }

    /*Metodo que compara dos canvas (List<List<String>>) dados, retorna
    * true en caso de ser iguales, false en caso contrario*/
    public boolean compararCanvas(List<List<String>> table1, List<List<String>> table2) {
        /*Se verifica que el numero de filas y columnas sea el mismo, si no es asi se retorna false*/
        if (table1.size() == table2.size() && table1.get(0).size() == table2.get(0).size()) {
            boolean igual = true;
            /*Se recorre cada elemento de la matriz, si al comparar los elemenos i,j de la tablas
            * no son el mismo, se retorna false*/
            for (int i = 0; i < table1.size(); i++) {
                if (table1.get(i).equals(table2.get(i)) == false)
                    igual = false;
                if (!igual)
                    break;
            }

            return igual ? true : false;

        } else {
            return false;
        }
    }

    /*Metodo que dado dos canvas (List<List<String>>) retorna el resultado de concatenarlos
    * horizontalmente */
    public List<List<String>> concatHorizontal(List<List<String>> table1, List<List<String>> table2) {
        /*Se inicializa el resultado de concatenarlos*/
        List<List<String>> result = new ArrayList<List<String>>();

        /*Se agrega las filas de tabla2 debajo de las de la tabla 1*/
        final int size = table1.size();
        for (int i = 0; i < size; i++) {
            List<String> tempList = new ArrayList<String>();
            tempList.addAll(table1.get(i));
            tempList.addAll(table2.get(i));
            result.add(tempList);
        }
        return result;
    }

    /*Metodo que dado dos canvas (List<List<String>>) retorna el resultado de concatenarlos
     * verticalemente */
    public List<List<String>> concatVertical(List<List<String>> table1, List<List<String>> table2) {
        /*Se inicializa el resultado de concatenarlos*/
        List<List<String>> result = new ArrayList<List<String>>();

        final int size = table1.size();
        final int size2 = table2.size();
        /*Se agrega las columnas de tabla2 luego de las de la tabla 1*/
        for (int i = 0; i < size; i++) {
            result.add(table1.get(i));
        }

        for (int i = 0; i < size2; i++) {
            result.add(table2.get(i));
        }

        return result;
    }

    /*Metodo que imprime el valor del canvas table List<List<String>> en pantalla*/
    public void printCanvas(List<List<String>> table) {
        /*Obtenemos el numero de filas y columnas del canvas*/
        final int n = table.size();
        final int m = table.get(0).size();

        /*Se correre cada fila y columna para imprimir cada elemento*/
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                String cell = table.get(i).get(j);
                /*Se verifica que tipo de literal es cada posicion de la matriz*/
                switch (cell) {
                    case "<_>":
                        System.out.print("_");
                        break;
                    case "<|>":
                        System.out.print("|");
                        break;
                    case "</>":
                        System.out.print("/");
                        break;
                    case "<\\>":
                        System.out.print("\\");
                        break;
                    case "<.>":
                        System.out.print(" ");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
    }
}
