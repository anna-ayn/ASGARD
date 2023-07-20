import java.util.*;
import java.io.*;

abstract class ASTNode {
    private ASTNode child;
    protected Utils utils = new Utils();

    public ASTNode getChild() {
        return child;
    }

    public void setChild(ASTNode child) {
        this.child = child;
    }

    protected void Interpretar() {
        if (this.getChild() != null) {
            this.getChild().Interpretar();
        }
    }

    protected void printParseTree(int depth, boolean sub) {
        if (this.getChild() != null) {
            this.getChild().printParseTree(depth, sub);
        }
    }

    /**
     * Función que recibe un String s,
     * un entero que indica el número de tabulaciones,
     * y un booleano sub que indica si el string es subexpresion de otro nodo.
     * Retorna un String que contiene el string s no tabulado en caso de que tab ==
     * 0 o
     * si sub es true, entonces no se tabula ya que es subexpresion. Caso contrario,
     * se tabula.
     **/
}

class DefaultNode extends ASTNode {
    public DefaultNode(ASTNode child) {
        this.setChild(child);
    }
}

class Expression extends ASTNode {
    protected String valor;
    protected Tipo tipo;
    List<List<String>> lienzo = new ArrayList<List<String>>();

    public String getTipo() {
        return tipo.getTipo();
    }

    public String getValor() {
        return this.valor;
    }

    public List<List<String>> getLienzo() {
        return this.lienzo;
    }

    public void setTipo(String tipo) {
        this.tipo.setTipo(tipo);
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}

class Undefined extends Expression {
    public Undefined() {
        this.tipo = new Tipo("undefined");
    }
}

class Literal extends Expression {

    public Literal(String value, String tipo) {
        this.valor = value;
        this.tipo = new Tipo(tipo);

        if (this.tipo.getTipo() == "canvas") {
            this.lienzo.add(new ArrayList<String>() {
                {
                    add(value);
                }
            });
        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.println(this.valor);
    }
}

class Identificador extends Expression {
    private final String identificador;
    private final Boolean repeat_det;

    public Identificador(String identificador) {
        this.identificador = identificador;
        this.tipo = new Tipo();
        this.repeat_det = false;
    }

    public Identificador(String identificador, Boolean repeat) {
        this.identificador = identificador;
        this.tipo = new Tipo();
        this.repeat_det = true;
    }

    public Boolean getFlag() {
        return this.repeat_det;
    }

    public void Interpretar() {
        if ((getTipo() != "canvas" && getValor() == null) || (getLienzo() == null)) {
            System.out.println("La variable '" + this.identificador + "' aun no ha sido inicializada.");
            System.exit(0);
        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.println(this.identificador);
    }

    public void printIdent() {
        System.out.println("id: " + this.identificador + ", tipo: " + this.tipo.getTipo());
    }
}

class OperacionBinaria extends Expression {
    protected String operador;
    protected Expression left;
    protected Expression right;

    public OperacionBinaria(String operador, Expression left, Expression right) {
        this.operador = operador;
        this.left = left;
        this.right = right;
    }
}

class OperacionUnaria extends Expression {
    protected String operador;
    protected Expression operando;

    public OperacionUnaria(String operador, Expression operando) {
        this.operador = operador;
        this.operando = operando;
    }
}

class ExpresionAritmeticaBin extends OperacionBinaria {
    public ExpresionAritmeticaBin(String operador, Expression left, Expression right) {
        super(operador, left, right);
        this.tipo = (Objects.equals(left.getTipo(), "integer") && Objects.equals(right.getTipo(), "integer"))
                ? new Tipo("integer")
                : new ErrorType("Error de tipos");
    }

    public void Interpretar() {
        left.Interpretar();
        right.Interpretar();
        int op1 = Integer.parseInt(left.getValor());
        int op2 = Integer.parseInt(right.getValor());
        switch (this.operador) {
            case "Suma":
                this.valor = Integer.toString(op1 + op2);
                break;
            case "Resta":
                this.valor = Integer.toString(op1 - op2);
                break;
            case "Multiplicacion":
                this.valor = Integer.toString(op1 * op2);
                break;
            case "Division":
                if (op2 == 0) {
                    System.out.println("No se puede dividir entre 0.");
                    System.exit(0);
                }
                this.valor = Integer.toString(op1 / op2);
                break;
            case "Modulo":
                this.valor = Integer.toString(op1 % op2);
                break;
            default:
                break;
        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("ARITMETICO_BIN", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- operacion: ", depth + 1, false));
        System.out.print(this.operador + "\n");
        System.out.print(StringUtils.stringTab("- operador izquierdo: ", depth + 1, false));
        left.printParseTree(depth + 2, true);
        System.out.print(StringUtils.stringTab("- operador derecho: ", depth + 1, false));
        right.printParseTree(depth + 2, true);
    }
}

class ExpresionAritmeticaUna extends OperacionUnaria {
    public ExpresionAritmeticaUna(String operador, Expression operando) {
        super(operador, operando);
        this.tipo = (Objects.equals(operando.getTipo(), "integer")) ? new Tipo("integer")
                : new ErrorType("Error de tipos");
    }

    public void Interpretar() {
        operando.Interpretar();
        this.valor = Integer.toString(-1 * Integer.parseInt(operando.getValor()));
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("ARITMETICO_UNA", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- operacion: ", depth + 1, false));
        System.out.print(this.operador + "\n");
        System.out.print(StringUtils.stringTab("- operador: ", depth + 1, false));
        operando.printParseTree(depth + 2, true);
    }
}

class ExpresionBooleanaBin extends OperacionBinaria {
    public ExpresionBooleanaBin(String operador, Expression left, Expression right) {
        super(operador, left, right);
        this.tipo = (Objects.equals(left.getTipo(), "boolean") && Objects.equals(right.getTipo(), "boolean"))
                ? new Tipo("boolean")
                : new ErrorType("Error de tipos");
    }

    public void Interpretar() {
        left.Interpretar();
        right.Interpretar();
        boolean op1 = Boolean.parseBoolean(left.getValor());
        boolean op2 = Boolean.parseBoolean(right.getValor());
        switch (this.operador) {
            case "Conjuncion":
                this.valor = String.valueOf(op1 && op2);
                break;
            case "Disyuncion":
                this.valor = String.valueOf(op1 || op2);
                break;
            default:
                break;
        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("BOOLEANO_BIN", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- operacion: ", depth + 1, false));
        System.out.print(this.operador + "\n");
        System.out.print(StringUtils.stringTab("- operador izquierdo: ", depth + 1, false));
        left.printParseTree(depth + 2, true);
        System.out.print(StringUtils.stringTab("- operador derecho: ", depth + 1, false));
        right.printParseTree(depth + 2, true);
    }
}

class ExpresionBooleanaUna extends OperacionUnaria {
    public ExpresionBooleanaUna(String operador, Expression operando) {
        super(operador, operando);
        this.tipo = (Objects.equals(operando.getTipo(), "boolean")) ? new Tipo("boolean")
                : new ErrorType("Error de tipos");
    }

    public void Interpretar() {
        operando.Interpretar();
        this.valor = String.valueOf(!Boolean.parseBoolean(operando.getValor()));
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("BOOLEANO_UNA", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- operacion: ", depth + 1, false));
        System.out.print(this.operador + "\n");
        System.out.print(StringUtils.stringTab("- operador: ", depth + 1, false));
        operando.printParseTree(depth + 2, true);
    }
}

class ExpresionRelacional extends OperacionBinaria {
    public ExpresionRelacional(String operador, Expression left, Expression right) {
        super(operador, left, right);
        this.tipo = new Tipo("boolean");
        /*
         * if (!Objects.equals(operador, "igual") && !Objects.equals(operador,
         * "desigual")) {
         * this.tipo = (Objects.equals(left.getTipo(), "integer") &&
         * Objects.equals(right.getTipo(), "integer"))
         * ? new Tipo("boolean")
         * : new ErrorType("Error de tipos");
         * } else {
         * this.tipo = (Objects.equals(left.getTipo(), right.getTipo()) &&
         * !Objects.equals(left.getTipo(), "error"))
         * ? new Tipo("boolean")
         * : new ErrorType("Error de tipos");
         * }
         */
    }

    public void Interpretar() {
        left.Interpretar();
        right.Interpretar();
        if (left.getTipo() == "integer") {
            int op1 = Integer.parseInt(left.getValor());
            int op2 = Integer.parseInt(right.getValor());
            switch (this.operador) {
                case "Igual":
                    this.valor = String.valueOf(op1 == op2);
                    break;
                case "Desigual":
                    this.valor = String.valueOf(op1 != op2);
                    break;
                case "Menor":
                    this.valor = String.valueOf(op1 < op2);
                    break;
                case "Menor o igual":
                    this.valor = String.valueOf(op1 <= op2);
                    break;
                case "Mayor":
                    this.valor = String.valueOf(op1 > op2);
                    break;
                case "Mayor o igual":
                    this.valor = String.valueOf(op1 >= op2);
                    break;
                default:
                    break;
            }
        } else if (left.getTipo() == "boolean") {
            boolean op1 = Boolean.parseBoolean(left.getValor());
            boolean op2 = Boolean.parseBoolean(right.getValor());
            switch (this.operador) {
                case "Igual":
                    this.valor = String.valueOf(op1 == op2);
                    break;
                case "Desigual":
                    this.valor = String.valueOf(op1 != op2);
                    break;
                default:
                    break;
            }
        } else {
            // canvas
            switch (this.operador) {
                case "Igual":
                    this.valor = utils.compararCanvas(this.left.getLienzo(), this.right.getLienzo()) ? "true"
                            : "false";
                    break;
                case "Desigual":
                    this.valor = utils.compararCanvas(this.left.getLienzo(), this.right.getLienzo()) ? "false"
                            : "true";
                    break;
                default:
                    break;
            }
        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("RELACIONAL", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- operacion: ", depth + 1, false));
        System.out.print(this.operador + "\n");
        System.out.print(StringUtils.stringTab("- operador izquierdo: ", depth + 1, false));
        left.printParseTree(depth + 2, true);
        System.out.print(StringUtils.stringTab("- operador derecho: ", depth + 1, false));
        right.printParseTree(depth + 2, true);
    }
}

class ExpresionCanvasBin extends OperacionBinaria {
    public ExpresionCanvasBin(String operador, Expression left, Expression right) {
        super(operador, left, right);
        this.tipo = (Objects.equals(left.getTipo(), "canvas") && Objects.equals(right.getTipo(), "canvas"))
                ? new Tipo("canvas")
                : new ErrorType("Error de tipos");
    }

    public void Interpretar() {
        left.Interpretar();
        right.Interpretar();

        if (left.getLienzo().get(0).get(0).equals("<empty>")) {
            this.lienzo = right.getLienzo();
        } else if (right.getLienzo().get(0).get(0).equals("<empty>")) {
            this.lienzo = left.getLienzo();
        } else {
            switch (this.operador) {
                case "Concatenacion Horizontal":
                    if (left.getLienzo().size() == right.getLienzo().size()) {
                        this.lienzo = utils.concatHorizontal(left.getLienzo(), right.getLienzo());
                    } else {
                        System.out.println(
                                "No se puede realizar la concatenacion horizontal, debido a que las dimensiones verticales de ambos lienzos no coinciden.");
                        System.exit(0);
                    }
                    break;
                case "Concatenacion Vertical":
                    if (left.getLienzo().get(0).size() == right.getLienzo().get(0).size()) {
                        this.lienzo = utils.concatVertical(left.getLienzo(), right.getLienzo());
                    } else {
                        System.out.println(
                                "No se puede realizar la concatenacion vertical, debido a que las dimensiones horizontales de ambos lienzos no coinciden.");
                        System.exit(0);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("CANVAS_BIN", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- operacion: ", depth + 1, false));
        System.out.print(this.operador + "\n");
        System.out.print(StringUtils.stringTab("- operador izquierdo: ", depth + 1, false));
        left.printParseTree(depth + 2, true);
        System.out.print(StringUtils.stringTab("- operador derecho: ", depth + 1, false));
        right.printParseTree(depth + 2, true);
    }
}

class ExpresionCanvasUna extends OperacionUnaria {
    public ExpresionCanvasUna(String operador, Expression operando) {
        super(operador, operando);
        this.tipo = (Objects.equals(operando.getTipo(), "canvas")) ? new Tipo("canvas")
                : new ErrorType("Error de tipos");
    }

    public void Interpretar() {
        operando.Interpretar();
        switch (this.operador) {
            case "Trasposicion":
                this.lienzo = utils.returnTranspose(operando.getLienzo());
                break;
            case "Rotacion":
                this.lienzo = utils.returnRotacion(operando.getLienzo());
                break;
            default:
                break;
        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("CANVAS_UNA", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- operacion: ", depth + 1, false));
        System.out.print(this.operador + "\n");
        System.out.print(StringUtils.stringTab("- operador: ", depth + 1, false));
        operando.printParseTree(depth + 2, true);
    }
}

class Instruccion extends ASTNode {
    public ASTNode next;
    public ASTNode instruccion;

    public Instruccion(ASTNode instruccion, ASTNode next) {
        this.instruccion = instruccion;
        this.next = next;
    }

    public Instruccion(ASTNode instruccion) {
        this.instruccion = instruccion;
        this.next = null;
    }

    public void Interpretar() {
        if (instruccion != null) {
            instruccion.Interpretar();
            if (next != null) {
                next.Interpretar();
            }
        }
    }

    public void printParseTree(int depth, boolean sub) {
        if (instruccion != null) {
            instruccion.printParseTree(depth, sub);
            if (next != null) {
                next.printParseTree(depth, sub);
            }
        }
    }
}

class Secuenciacion extends ASTNode {
    public Instruccion child;

    public Secuenciacion(Instruccion child) {
        this.child = child;
    }

    public void Interpretar() {
        child.Interpretar();
    }

    public void printParseTree(int depth, boolean sub) {
        if (child.next != null) {
            System.out.print(StringUtils.stringTab("SECUENCIACION", depth, sub) + "\n");
            child.printParseTree(depth + 1, false);
        } else {
            child.printParseTree(depth, sub);
        }
    }
}

class Asignacion extends ASTNode {
    private Identificador identificador;
    private Expression expression;

    public Asignacion(Identificador identificador, Expression expression) {
        this.identificador = identificador;
        this.expression = expression;
    }

    public void Interpretar() {
        expression.Interpretar();
        this.identificador.setValor(expression.getValor());
        if (identificador.getTipo() == "canvas") {
            this.identificador.lienzo = expression.getLienzo();
        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("ASIGNACION", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- var: ", depth + 1, false));
        identificador.printParseTree(depth + 2, true);
        System.out.print(StringUtils.stringTab("- val: ", depth + 1, false));
        expression.printParseTree(depth + 2, true);
    }
}

class Condicional extends ASTNode {
    private Expression guardia;
    private ASTNode ifBody;
    private ASTNode elseBody;

    public Condicional(Expression guardia) {
        this.guardia = guardia;
        this.ifBody = null;
        this.elseBody = null;
    }

    public Condicional(Expression guardia, ASTNode ifBody) {
        this.guardia = guardia;
        this.ifBody = ifBody;
        this.elseBody = null;
    }

    public Condicional(Expression guardia, ASTNode ifBody, ASTNode elseBody) {
        this.guardia = guardia;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    public void Interpretar() {
        guardia.Interpretar();
        if (guardia.getValor().equals("true")) {
            ifBody.Interpretar();
        } else if (elseBody != null) {
            elseBody.Interpretar();
        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("CONDICIONAL", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- guardia: ", depth + 1, false));
        guardia.printParseTree(depth + 2, true);
        System.out.print(StringUtils.stringTab("- exito: ", depth + 1, false));
        ifBody.printParseTree(depth + 2, true);
        if (elseBody != null) {
            System.out.print(StringUtils.stringTab("- fallo: ", depth + 1, false));
            elseBody.printParseTree(depth + 2, true);
        }
    }
}

class IteracionIndet extends ASTNode {
    private Expression guardia;
    private ASTNode body;

    public IteracionIndet(Expression guardia, ASTNode body) {
        this.guardia = guardia;
        this.body = body;
    }

    public void Interpretar() {
        guardia.Interpretar();

        while (guardia.getValor() == "true") {
            body.Interpretar();
            guardia.Interpretar();
        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("ITERACION_INDET", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- guardia: ", depth + 1, false));
        guardia.printParseTree(depth + 2, true);
        System.out.print(StringUtils.stringTab("- cuerpo: ", depth + 1, false));
        body.printParseTree(depth + 2, true);
    }
}

class IteracionDet extends ASTNode {
    private Identificador identificador;
    private Expression inicio;
    private Expression fin;
    private ASTNode body;

    public IteracionDet(Expression inicio, Expression fin, ASTNode body) {
        this.identificador = null;
        this.inicio = inicio;
        this.fin = fin;
        this.body = body;
    }

    public IteracionDet(Identificador identificador, Expression inicio, Expression fin, ASTNode body) {
        this.identificador = identificador;
        this.inicio = inicio;
        this.fin = fin;
        this.body = body;
    }

    public void Interpretar() {
        inicio.Interpretar();
        fin.Interpretar();

        String start = inicio.getValor();
        String finish = fin.getValor();

        if (identificador != null) {
            for (int i = Integer.parseInt(start); i < Integer.parseInt(finish); i++) {
                identificador.valor = Integer.toString(i);
                body.Interpretar();
            }
        } else {
            int cantidad = Math.max(Integer.parseInt(finish) - Integer.parseInt(start) + 1, 0);
            while (cantidad > 0) {
                body.Interpretar();
                cantidad--;
            }

        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("ITERACION_DET", depth, sub) + "\n");
        if (identificador != null) {
            System.out.print(StringUtils.stringTab("- var: ", depth + 1, false));
            identificador.printParseTree(depth + 2, true);
        }
        System.out.print(StringUtils.stringTab("- limite inferior: ", depth + 1, false));
        inicio.printParseTree(depth + 2, true);
        System.out.print(StringUtils.stringTab("- limite superior: ", depth + 1, false));
        fin.printParseTree(depth + 2, true);
        System.out.print(StringUtils.stringTab("- cuerpo: ", depth + 1, false));
        body.printParseTree(depth + 2, true);

    }
}

class Read extends ASTNode {
    private Identificador identificador;

    public Read(Identificador identificador) {
        this.identificador = identificador;
    }

    public void Interpretar() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str;
        try {
            str = reader.readLine();
            if (identificador.getTipo() == "boolean" && (str == "true" || str == "false")) {
                this.identificador.setValor(str);
                reader.close();
            } else if (identificador.getTipo() == "integer") {
                try {
                    Integer.parseInt(str);
                    this.identificador.setValor(str);
                    reader.close();
                } catch (NumberFormatException e) {
                    System.out.println(str + " no es un entero.");
                    System.exit(0);
                }
            } else {
                System.out.println(str + " no es un booleano.");
                System.exit(0);
            }
        } catch (IOException e) {
        }

    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("READ", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- var: ", depth + 1, false));
        identificador.printParseTree(depth + 2, true);
    }
}

class Print extends ASTNode {
    private Expression expression;

    public Print(Expression expression) {
        this.expression = expression;
    }

    public void Interpretar() {
        if (expression.getValor() == null) {
            expression.Interpretar();
        }
        if (expression.getTipo() != "canvas")
            System.out.println(expression.getValor());
        else {
            utils.printCanvas(expression.getLienzo());
        }
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("PRINT", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- expr: ", depth + 1, false));
        expression.printParseTree(depth + 2, true);
    }
}