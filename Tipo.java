class Tipo {
    protected String tipo;

    public Tipo() {
        this.tipo = null;
    }
    public Tipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        if (this.tipo == null) {
            this.tipo = tipo;
        }
    }

    // Otros métodos y atributos comunes a todas las subclases
}

class ErrorType extends Tipo {
    private String message;

    public ErrorType(String message) {
        super("error");
        this.message = message;
    }

    // Otros métodos y atributos específicos para la clase ErrorType
}

