/* Sección de declaraciones de JFlex */
import java_cup.runtime.Symbol;

%%
%public
%class Lexico
%line 
%column
%char 
%unicode
%cup
%ignorecase
%type Symbol


%init{ 
    yyline = 1; 
    yycolumn = 1; 
%init} 

letter = [a-zA-Z]
digit = [0-9]
spaces=[ \t\r\n]+
comment = "{-" (\s | .) ~"-}"
identifiers = {letter}({letter} | {digit})*
canvaslit = "<empty>"|"</>"|"<\\>"|"<|>"|"<_>"|"<.>"
%%



/* Finaliza la sección de declaraciones de JFlex */
 
/* Inicia sección de reglas */
 
// Cada regla está formada por una {expresión} espacio {código}
// Los espacios y comentarios se ignoran
{spaces} {new Symbol(sym.TkEmpty, yyline, yycolumn);}
{comment} {/*Ignore*/}

/* Los identificadores de variables estan formados por una letra seguida de digitos o letras,
no obstante, no pueden ser antecedidos por literales numericos ni de tipo lienzo (ni seguidos en caso de los literales de tipo lienzo)*/
/* Se producira un error, en caso de que aparezca: */
/* {digit}+{identifiers}: precede al menos un numero al identificador */
/* {canvaslit}+{identifiers}: precede al menos un literal canvas al identificador */
/* {identifiers} {canvaslit}: seguido de un literal canvas luego del identificador*/
({digit}+{identifiers}) | ({canvaslit}+{identifiers}) | ({identifiers} {canvaslit}) {
   System.out.println("Error: Caracter inesperado '"+yytext()+
    "' en la fila "+yyline+", columna "+yycolumn);
    return new Symbol(sym.TkError, yyline, yycolumn);
}
/* Identificadores de las variables y palabas reservadas */
{identifiers} {
        /* Se analiza el String matcheado */
        String x = yytext();
        /* Se verifica si dicho string es exactamente identico a alguna palabra reservada */
        // Switch statement over above string
        switch (x) {
        case "using":
            return new Symbol(sym.TkUsing,yyline,yycolumn);
        case "begin":
            return new Symbol(sym.TkBegin,yyline,yycolumn);
        case "end":
            return new Symbol(sym.TkEnd,yyline,yycolumn);
        case "integer":
            return new Symbol(sym.TkInteger,yyline,yycolumn);
        case "boolean":
            return new Symbol(sym.TkBoolean,yyline,yycolumn);
        case "canvas":
            return new Symbol(sym.TkCanvas,yyline,yycolumn);
        case "if":
            return new Symbol(sym.TkIf,yyline,yycolumn);
        case "then":
            return new Symbol(sym.TkThen,yyline,yycolumn);
        case "otherwise":
            return new Symbol(sym.TkOtherwise,yyline,yycolumn);
        case "done":
            return new Symbol(sym.TkDone,yyline,yycolumn);
        case "while":
            return new Symbol(sym.TkWhile,yyline,yycolumn);
        case "repeat":
            return new Symbol(sym.TkRepeat,yyline,yycolumn);
        case "with":
            return new Symbol(sym.TkWith,yyline,yycolumn);
        case "from":
            return new Symbol(sym.TkFrom,yyline,yycolumn);
        case "to":
            return new Symbol(sym.TkTo,yyline,yycolumn);
        case "read":
            return new Symbol(sym.TkRead,yyline,yycolumn);
        case "print":
            return new Symbol(sym.TkPrint,yyline,yycolumn);
        case "true":
            return new Symbol(sym.TkTrue,yyline,yycolumn);
        case "false":
            return new Symbol(sym.TkFalse, yyline,yycolumn);
        default:
            return new Symbol(sym.TkIdent, yyline,yycolumn, yytext());
        }
}

/* En el caso de "of type", lo vamos a considerar como una palabra reservada y el Symbol que se genera es "TkOfType"  */
"of type" {return new Symbol (sym.TkOfType);}

/* Símbolos */
"," {return new Symbol(sym.TkComa,yyline,yycolumn);}
";" {return new Symbol(sym.TkPuntoYComa,yyline,yycolumn);}
"(" {return new Symbol(sym.TkParAbre,yyline,yycolumn);}
")" {return new Symbol(sym.TkParCierra,yyline,yycolumn);}
"+" {return new Symbol(sym.TkMas,yyline,yycolumn);}
"-" {return new Symbol(sym.TkMenos,yyline,yycolumn);}
"*" {return new Symbol(sym.TkMult,yyline,yycolumn);}
"/" {return new Symbol(sym.TkDiv,yyline,yycolumn);}
"%" {return new Symbol(sym.TkMod,yyline,yycolumn);}
"/\\" {return new Symbol(sym.TkConjuncion,yyline,yycolumn);}
"\\/" {return new Symbol(sym.TkDisyuncion,yyline,yycolumn);}
"^" {return new Symbol(sym.TkNegacion,yyline,yycolumn);}
"<" {return new Symbol(sym.TkMenor,yyline,yycolumn);}
"<=" {return new Symbol(sym.TkMenorIgual,yyline,yycolumn);}
">" {return new Symbol(sym.TkMayor,yyline,yycolumn);}
">=" {return new Symbol(sym.TkMayorIgual,yyline,yycolumn);}
"=" {return new Symbol(sym.TkIgual,yyline,yycolumn);}
"/=" {return new Symbol(sym.TkDesigual,yyline,yycolumn);}
":" {return new Symbol(sym.TkConcatHorizontal,yyline,yycolumn);}
"|" {return new Symbol(sym.TkConcatVertical,yyline,yycolumn);}
"$" {return new Symbol(sym.TkRotacion,yyline,yycolumn);}
"'" {return new Symbol(sym.TkTrasposicion,yyline,yycolumn);}
":=" {return new Symbol(sym.TkAsignacion,yyline,yycolumn);}

/* Literales numericos */
{digit}+ {return new Symbol(sym.TkNumLit, yyline,yycolumn, Integer.parseInt(yytext()));}

/* Literales de lienzo */
{canvaslit} {return new Symbol(sym.TkCanvasLit, yyline,yycolumn, yytext());}

/* en otro caso, se produce un error */
. {
    System.out.println("Error: Caracter inesperado '"+yytext()+
    "' en la fila "+yyline+", columna "+yycolumn);
    return new Symbol(sym.TkError, yyline, yycolumn);
}

/* Finaliza la sesion de reglas */
