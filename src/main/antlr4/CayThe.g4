grammar CayThe;

@header {
package de.weltraumschaf.caythe;
}

// Parser rules:
compilationUnit     : statement* EOF ;
statement           : expression NL
                    | assignment NL
                    | printStatement
                    | NL ;
expression          : compare ( relop compare )* ;
compare             : term ( ( ADD | SUB ) term )* ;
term                : factor ( ( MUL | DIV | MOD ) factor )* ;
factor              : atom ( CARET expression )? ;
atom                : value
                    | variable
                    | LPAREN expression RPAREN ;
value               : BOOL_VALUE | INTEGER_VALUE | FLOAT_VALUE | STRING_VALUE ;
relop               : EQUAL | NOTEQUAL | GT | LT | GE | LE ;
variable            : ID ;
assignment          : variable ASSIGN expression ;
printStatement      : 'print' LPAREN expression RPAREN ;

// Lexer rules:
// Operators:
ASSIGN      : '=' ;
GT          : '>' ;
LT          : '<' ;
BANG        : '!' ;
TILDE       : '~' ;
QUESTION    : '?' ;
COLON       : ':' ;
EQUAL       : '==' ;
LE          : '<=' ;
GE          : '>=' ;
NOTEQUAL    : '!=' ;
AND         : '&&' ;
OR          : '||' ;
INC         : '++' ;
DEC         : '--' ;
ADD         : '+' ;
SUB         : '-' ;
MUL         : '*' ;
DIV         : '/' ;
BITAND      : '&' ;
BITOR       : '|' ;
CARET       : '^' ;
MOD         : '%' ;
ARROW       : '->' ;
COLONCOLON  : '::' ;

// Structureing:
LPAREN  : '(' ;
RPAREN  : ')' ;
LBRACE  : '{' ;
RBRACE  : '}' ;
LBRACK  : '[' ;
RBRACK  : ']' ;
SEMI    : ';' ;
COMMA   : ',' ;
DOT     : '.' ;

// Types:
BOOL_VALUE      : TRUE | FALSE ;
TRUE            : 'true' ;
FALSE           : 'false' ;
INTEGER_VALUE   : SIGN? DIGIT+ ;
FLOAT_VALUE     : SIGN? DIGIT+ DOT DIGIT* EXPONENT?
                | SIGN? DIGIT+ EXPONENT? ;
EXPONENT        : E SIGN? DIGIT+ ;
STRING_VALUE    : '"' CHARACTER* '"' ;
ID              : LETTER CHARACTER* ;
TYPE            : 'integer' | 'float' | 'boolean' | 'string' ;
SIGN            : '+' | '-' ;
E               : 'e' | 'E' ;

// General:
CHARACTER   : DIGIT | LETTER ;
LETTER      : ('a' .. 'z') | ('A' .. 'Z') ;
DIGIT       : ('0' .. '9') ;
NL          : '\n' | '\r' | '\r\n' ;

// Ignored:
ML_COMMENT  : '/*' .*? '*/'         -> skip ;
SL_COMMENT  : '//' ~[\r\n]* NL      -> skip ;
WS          : [ \t\u000C]+          -> skip ;
