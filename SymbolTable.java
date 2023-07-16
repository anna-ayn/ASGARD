import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class SymbolTable {
    /*
     * El Identificador tiene el nombre de la variable, su tipo, su valor
     * y el flag si pertenece a una iteracion determinada
     */
    public HashMap<String, Identificador> table;
    public SymbolTable parent;

    public SymbolTable(SymbolTable parent) {
        this.table = new HashMap<String, Identificador>();
        this.parent = parent;
    }

    /*
     * Funcion que agrega al padre de la tabla
     * Args: Symboltable parent (el padre de la tabla)
     * Retorna: nada
     */
    public void addParent(SymbolTable parent) {
        this.parent = parent;
    }

    /*
     * Funcion para colocar un nuevo dato en la tabla
     * Args: String key (contiene el nombre del identificador)
     * Identificador value (Los demas datos del identificador que si nombre, tipo,
     * valor y el flag de si es o no una repeticion determinada)
     * Retorna: nada
     */
    public void put(String key, Identificador value) {
        this.table.put(key, value);
    }

    /*
     * Funcion para buscar un identificador en una tabla
     * Args: String key (contiene el nombre del identificador
     * Retorna: True si esta presente en la tabla, false en caso contrario
     */
    public boolean contains(String key) {
        return table.containsKey(key);
    }

    /*
     * Funcion que retorna un Identificador del Hashmap
     * Args: String key (contiene el nombre del identificador)
     * Retorna: El Identificador asociado al key
     */
    public Identificador get(String key) {
        if (this.table.containsKey(key)) {
            return this.table.get(key);
        } else if (this.parent != null) {
            return this.parent.get(key);
        } else {
            return null;
        }
    }

    /*
     * Funcion que setea cada identificador con tipo null a un nuevo tipo de dato
     * Args: String value (contiene en cadena de texto el nuevo tipo de dato)
     * Retorna: nada
     */
    public void setType(String value) {
        Iterator<Map.Entry<String, Identificador>> iterator = table.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Identificador> entry = iterator.next();
            Identificador identificador = entry.getValue();
            if (identificador.getTipo() == null) {
                identificador.setTipo(value);
            }
        }
    }

}
