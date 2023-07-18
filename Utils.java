import java.util.ArrayList;
import java.util.List;

public class Utils {
    public List<List<String>> returnTranspose(List<List<String>> table) {
        List<List<String>> transposedList = new ArrayList<List<String>>();

        final int n = table.size();
        final int m = table.get(0).size();

        for (int i = 0; i < m; i++) {
            List<String> tempList = new ArrayList<String>();
            for (int j = 0; j < n; j++) {
                tempList.add(table.get(j).get(i));
            }
            transposedList.add(tempList);
        }
        return transposedList;
    }

    public List<List<String>> returnRotacion(List<List<String>> table) {
        List<List<String>> result = new ArrayList<List<String>>();

        final int n = table.size();
        final int m = table.get(0).size();
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

    public boolean compararCanvas(List<List<String>> table1, List<List<String>> table2) {
        if (table1.size() == table2.size() && table1.get(0).size() == table2.get(0).size()) {
            boolean igual = true;
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

    public List<List<String>> concatHorizontal(List<List<String>> table1, List<List<String>> table2) {
        final int size = table1.size();
        for (int i = 0; i < size; i++) {
            table1.get(i).addAll(table2.get(i));
        }
        return table1;
    }

    public List<List<String>> concatVertical(List<List<String>> table1, List<List<String>> table2) {

        for (List<String> row : table2) {
            table1.add(row);
        }
        return table1;
    }

    public void printCanvas(List<List<String>> table) {
        final int n = table.size();
        final int m = table.get(0).size();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                String cell = table.get(i).get(j);
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
