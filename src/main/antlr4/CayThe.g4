grammar CayThe;

@header {
package de.weltraumschaf.caythe;
}

// Parser rules:
equation            : expression relop expression ;
expression          : multip_expression ( ( PLUS | MINUS ) multip_expression )* ;
multip_expression   : pow_expression ( ( TIMES | DIV ) pow_expression )* ;
pow_expression      : atom ( POW expression )? ;

atom                : scientific
                    | variable
                    | LPAREN expression RPAREN ;
scientific          : number ( E number )?;
relop               : EQ | GT | LT ;
number              : MINUS? DIGIT+ ( POINT DIGIT+ )? ;
variable            : MINUS? LETTER ( LETTER | DIGIT )* ;

// Lexer rules:
LPAREN  : '(' ;
RPAREN  : ')' ;
PLUS    : '+';
MINUS   : '-';
TIMES   : '*';
DIV     : '/' ;
GT      : '>'  ;
LT      : '<'  ;
EQ      : '='  ;
POINT   : '.';
E       : 'e'   | 'E';
POW     : '^' ;
LETTER  : ('a'..'z') | ('A'..'Z');
DIGIT   : ('0'..'9');

COMMENT     : ML_COMMENT | SL_COMMENT NL ;
ML_COMMENT  : '/*' .*? '*/' ;
SL_COMMENT  : '//' ~[\r\n]* '\r'? NL ;

NL      : [\n\r]+   -> channel(HIDDEN) ;
WS      : [ \t]+    -> channel(HIDDEN)  ;
