import java.util.Objects;

abstract class ASTNode {
    private ASTNode child;

    public ASTNode getChild() {
        return child;
    }

    public void setChild(ASTNode child) {
        this.child = child;
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
    protected Tipo tipo;

    public String getTipo() {
        return tipo.getTipo();
    }

    public void setTipo(String tipo) {
        this.tipo.setTipo(tipo);
    }
}

class Undefined extends Expression {
    public Undefined() {
        this.tipo = new Tipo("undefined");
    }
}

class Literal extends Expression {
    private final String value;

    public Literal(String value, String tipo) {
        this.value = value;
        this.tipo = new Tipo(tipo);
    }

    public void printParseTree(int depth, boolean sub) {
        System.out.println(this.value);
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

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("CONDICIONAL", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- guardia: ", depth + 1, false));
        guardia.printParseTree(depth + 2, true);
        System.out.print(StringUtils.stringTab("- exito: ", depth + 1, false));
        if (ifBody != null) {
            ifBody.printParseTree(depth + 2, true);
        } else {
            System.out.println("INSTRUCCION VACIA");
        }
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

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("ITERACION_INDET", depth, sub));
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

    public void printParseTree(int depth, boolean sub) {
        System.out.print(StringUtils.stringTab("PRINT", depth, sub) + "\n");
        System.out.print(StringUtils.stringTab("- expr: ", depth + 1, false));
        expression.printParseTree(depth + 2, true);
    }
}