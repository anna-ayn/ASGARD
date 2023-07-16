JFLAGS = -g
JFLEX  = jflex 
JC  = javac
JV = java

CLASSES = \
        Main.java \
        sym.java \
		parser.java \
        Lexico.java \
		ASTNode.java

Main.class SymbolTable.class parser.class sym.class Lexico.class: Main.java Pair.java SymbolTable.java Lexico.java parser.java sym.java ASTNode.java
	$(JC) -cp .:java-cup-11b.jar Pair.java SymbolTable.java ASTNode.java sym.java parser.java Lexico.java Main.java

parser.java sym.java: Sintactico.cup
	$(JV) -cp .:java-cup-11b.jar java_cup.Main Sintactico.cup

Lexico.java: Lexico.flex
	$(JFLEX) Lexico.flex
