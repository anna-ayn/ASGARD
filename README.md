# CONTEXT ANALIZER -ASGARD

### Ana Shek - 1910096

### Jeamhowards Montiel - 1910234

## Lenguaje y Herramientas Utilizados

Para nuestro analizador de contexto fue utilizado el lenguaje Java junto con las herramientas generadora de analizadores lexicográficos y analizador sintáctico. Para el proyecto se usó el `JDK 20`, `JFlex 1.9.1` y `CUP 0.11b
` por lo que se recomienda utilizar dichas versiones para garantizar el debido funcionamiento del analizador de contexto.

## Como ejecutar el programa

1. Escribe en tu consola la palabra

```
make
```

2. Seguidamente

```
./ContAsgard < programa.asg
```

donde programa.asg contiene el codigo Asgard. También puedes probar con el archivo programaConErrores.asg que contiene otro código de Asgard pero con varios errores de contexto.

- Si te aparece un error de que el permiso fue denegado, escribe en la consola:
  ```
  sudo chmod +x ContAsgard
  ```
- Al elevar los permisos del archivos, introduce nuevamente en tu consola

  ```
  ./ContAsgard < programa.asg
  ```

3. **(Opcional)** Si quieres borrar todos los archivos que has compilado, puedes escribir en la consola

```
./clean
```

- **NOTA** : al ejecutar `./clean`, se va a borrar todos los archivos .class y .java que se han creado en el paso 1 y 2, por lo que el programa no se volverá a ejecutar luego de borrarlos.

Para volver a ejecutar el programa luego del borrado, necesitarás volver al paso 1 y 2.

## Tabla de Simbolos

La tabla de simbolos es una estructura de datos que se utiliza para almacenar los simbolos que se van encontrando en el codigo. En nuestro caso, la tabla de simbolos está implementada con la clase `SymbolTable` la cual contiene los siguientes atributos y métodos que serán explicados a continuación:

### Atributos

- `private HashMap<String, Identificador> table;` : Es un HashMap que contiene como llave el nombre del identificador y como valor un objeto de tipo `Identificador` que contiene la información de la variable.
- `private SymbolTable parent;` : Es un objeto de tipo `SymbolTable` que representa la tabla de simbolos padre de la tabla de simbolos actual.

### Métodos

- `public SymbolTable(SymbolTable parent)` : Constructor de la clase `SymbolTable` que recibe como parámetro la tabla de simbolos padre.
- `public void addParent(SymbolTable parent)` : Método que agrega la tabla de simbolos padre a la tabla de simbolos actual.
- ` public void put(String key, Identificador value)` : Método que agrega un identificador a la tabla de simbolos actual.
- `public Identificador get(String key)` : Método que retorna un identificador de la tabla de simbolos actual en caso de existir, si no revisa sus ancestros hasta encortrarlo, en caso de no existir en la tabla actual ni en sus ancestros retorna null.
- `public boolean contains(String key)` : Método que retorna true si la tabla de simbolos actual contiene el identificador con el nombre key, en caso contrario retorna false.
- `public void setType(String type)` : Método que asigna el tipo de dato a todos los identificadores de la tabla de simbolos actual que tengan tipo null.

## Errores

A continuacion se muestran los errores de contexto que se pueden encontrar en el codigo Asgard y como fueron manejados cada uno de ellos.

### Identificador no declarado

Este error se presenta cuando se intenta utilizar un identificador que no ha sido declarado previamente. Para manejar este error se utilizó la clase `SymbolTable` la cual contiene un HashMap que almacena los identificadores que se van encontrando en el codigo. Cuando se encuentra un identificador, se verifica si este se encuentra en la tabla de simbolos actual, si no se encuentra se verifica en la tabla de simbolos padre, si no se encuentra en la tabla de simbolos padre se verifica en la tabla de simbolos padre de la tabla de simbolos padre y asi sucesivamente hasta encontrarlo o llegar a la tabla de simbolos global. Si no se encuentra en la tabla de simbolos global, se muestra el error de identificador no declarado.

### Identificador ya declarado

Este error se presenta cuando se intenta declarar un identificador que ya ha sido declarado previamente. Para manejar este error se utilizó la clase `SymbolTable` la cual contiene un HashMap que almacena los identificadores que se van encontrando en el codigo. Cuando se encuentra un identificador, se verifica si este se encuentra en la tabla de simbolos actual, si se encuentra se muestra el error de identificador ya declarado.

### Errores de tipo

Este error se presenta cuando no hay concordancia en las operaciones que se desean realizar, como por ejemplo sumar un entero con un booleano. Para manejar este error se consideró agregar un nuevo atributo a la clase `Expression`, de la cual heredan todos los tipos de expresiones, que es el tipo de dato de la expresión. Luego, si una expresion es binaria o unaria se usan reglas de inferencia para inferir el tipo de dato de la expresión, teniendo como caso base el tipo de los literales y de los identificadores. Entonces, cuando se realiza una operación, se verifica si los tipos de datos de las expresiones involucradas en la operación son compatibles, si no son compatibles el tipo de la expresión se vuelve `errorType` para luego mostrarse el error de tipo.

### Asignación de tipo incorrecto

Este error se presenta cuando se intenta asignar un valor a un identificador que no es del mismo tipo. Para manejar este error se verifica si el identificador se encuentra en la tabla de simbolos actual, si se encuentra se verifica si el tipo de dato del identificador es igual al tipo de dato de la expresión que se le quiere asignar, si no son iguales se muestra el error de asignación de tipo incorrecto.

### Modificación del valor de una variable que haya sido asociada a una repetición determinada

Para manejar este error se incluyó un nuevo atributo a la clase `Identificador` y es un valor booleano `repeat_det` que indica si el identificador ha sido asociado a una repetición determinada. Luego, cuando se declara un identificador en una repetición deterterminada se setea este valor a true, de forma que cuando se intente modificar el valor de un identificador que haya sido asociado a una repetición determinada se muestra el error.

## Otras consideraciones

- Los errores de contexto incluyen el número de fila y columna en el que se encuentran y el tipo de error.
- En caso de no haber errores de contexto, se imprime el AST del programa.
- Para la impresión de los errores se utilizó una PriorityQueue que ordena los errores por el número de línea en el que se encuentran. En caso de existir más de un error en una misma línea, no se mantiene el orden por columna, esto debido a que
  puede ocurrir el siguiente caso: `x := true / 10`, acá se muestra primero el error de tipo y debido al error de tipo es que ocurre un error de asignación en la variable x y se muestra dicho error.
- Se incluyeron dos archivos de prueba, `programa.asg` y `programaConErrores.asg`, el primero es un programa correcto y el segundo es un programa con errores de contexto.
