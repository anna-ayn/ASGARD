# INTERPRETER -ASGARD

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
./Asgard <programa>
```

donde <programa> en la ruta de un programa escrito en Asgard, se puede probar con la ruta programa.asg. Ademas se cuenta con los programas pedido que estan en la carpeta programas. 

- Si te aparece un error de que el permiso fue denegado, escribe en la consola:
  ```
  sudo chmod +x Asgard
  ```
- Al elevar los permisos del archivos, introduce nuevamente en tu consola

  ```
  ./Asgard <programa>
  ```

3. **(Opcional)** Si quieres borrar todos los archivos que has compilado, puedes escribir en la consola

```
./clean
```

- **NOTA** : al ejecutar `./clean`, se va a borrar todos los archivos .class y .java que se han creado en el paso 1 y 2, por lo que el programa no se volverá a ejecutar luego de borrarlos.

Para volver a ejecutar el programa luego del borrado, necesitarás volver al paso 1 y 2.

## Implementación del interprete

Dado que el interpretador del lenguaje debe interpretar las instrucciones en el mismo orden que está en el árbol sintáctico abstracto se implementó un método
`void Interpretar()` en la clase `ASTNode`, la cual propaga el interprete en forma de cascada, de tal manera que si una instrucción es de la forma `x := 3*2 + 1`,
primero interprete 3*2 como la multiplicación de dos literales, dando como resultado 6, luego interpreta la suma entre ese valor y 1, dando como resultado 7. Y finalmente, se interpreta la asignación de la variable x a 7. A continuación se especificará como se interpreta cada una de las instrucciones de ASGARD.
### ENTEROS Y BOOLEANOS
Para el caso de los enteros y booleanos se accede al valor de la expresión mediante las clases `Integer` y `Boolean` para convertir los valores almacenados como `String` en enteros y booleanos, con lo metodos `parseInt` y `parseBoolean`, que puedan ser operados en Java. 
- `Expresiones binarias (enteras y booleanas)`: Se llama al método Interpretar para que interprete tanto el valor del operando izquierdo como el operando derecho de la expresión binaria, luego se verifica el tipo de operador y se operan los operandos convertido en `Integer` y `Boolean` respectivamente para calcular el valor de la expresión binaria.
- `Expresiones unarias (enteras y booleanas)`: Equivalentemente a las expresiones binarias, se interpreta el operando, se convierte dicho operando en un `Integer` o `Boolean` respectivamente para aplicar la negación respectiva (multiplicar por -1 o aplicar la negación unaria).

### CANVAS

Los canvas en JAVA son interpretados mediante una lista de listas de Strings (`List<List<String>>` - lista de Strings 2D, matriz dinámica) para simular las dos dimensiones de los Canvas, la impresión de una variable canvas consistirá en imprimir cada una de las listas de strings (las filas). Dado que no hay una librería que nos de un manejo tan adecuado para las operaciones como `Integer` o `Boolean` para los enteros y los booleanos, se implementó la clase `Utils` la cual almacena toda las posibles operaciones de los Canvas. 

- En lineas generales, si se quiere hacer una concatenación, ya sea vertical o horizontal, se verifica que el tamaño de las matrices (la lista de listas de String) concuerden, el número de filas tiene que ser el mismo en el caso de la concatenación horizontal y el número de columnas tiene que ser el mismo en el caso de la concatenación vertical.
- Para la rotación se intercambian cada uno de los literales de cada uno del elemento i,j por sus equivalentes rotados y se crea una nueva lista que contenga estos elementos. Luego, iteramos por cada columna (yendo de la primera columna a la última columna) de esta nueva lista y dentro de esta iteración, copiamos cada elemento de una fila en orden reverso (es decir, yendo primero a la última fila hasta llegar a la primera fila) en una nueva lista.
- En el caso de la transposición, se recorre cada elemento i,j de la matriz y se envia a la posición j,i de una nueva matriz, luego se retorna dicha matriz.
- Las comparaciones entre dos canvas 1 y 2 son determinadas por las matrices que los representan. Dos elementos de tipo canvas son iguales si y solo si para todo elemento i,j de la matriz 1 es igual al elemento i,j de la matriz 2. 

### ITERACION INDETERMINADA

Se interpreta como un ciclo while de Java, en donde en cada iteración se interpreta el cuerpo de instrucciones y la guardia. Observemos que se interpreta la guardia dentro del ciclo while ya que puede que el valor de la guardia cambie dentro de la iteración indeterminada.

### ITERACION DETERMINADA

Se interpreta como un ciclo for de Java, en donde se evaluan los límites inferior y superior para asignar el número de iteraciones del ciclo. Al igual que en el ciclo indeterminado se interpreta en cada iteración el cuerpo de instrucciones.

### CONDICIONAL

Se interpreta como una instruccion if, if-else respectivamente, se interpreta el valor de la guardia y se ejecutan el cuerpo de intrucciones interpretandolo.

### ASIGNACION

Intepreta el valor de la expresión a asignar para luego almacenar dicho valor en el atributo `value` de los idenficadores, en caso de ser un booleano o un entero, o en el atributo Lienzo en caso de ser una expresion de tipo Canvas.

### PRINT

Se interpreta como la instrucción `System.out.println` de Java, imprimiendo el valor de la expresión. En el caso de imprimir una expresión de tipo Canvas, se tiene en Utils una función para imprimirla, el cual recorre cada elemento por fila y por columna, y va imprimiendo una por una.

### READ

Lee el buffer `new BufferedReader(new InputStreamReader(System.in))` para luego obtener la linea pasada por el usuario, verifica el tipo de dato obtenido de concordar con la variable se asigna el valor.

## PROGRAMAS PEDIDOS

### fibonacci.asg

Programa que lee por la entrada estándar un número entero no–negativo n e imprime,
a la salida estándar, el n– ésimo elemento de la secuencia de Fibonacci.

### square.asg

Programa que lee un entero n ≥ 3 de la entrada estandar e imprime, a la salida estandar,
un cuadrado donde cada lado tenga n caracteres de largo.

### arte-ascii.asg

Programa que lee un entero no–negativo n de la entrada estandar e imprima, a la salida
estandar, una representación en arte ascii de ese número, donde cada dígito tiene
3 caracteres de ancho y 5 de alto (con una columna de 1 caracter de ancho entre cada
dígito).
