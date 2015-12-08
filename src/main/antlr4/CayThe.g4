grammar CayThe;

@header {
package de.weltraumschaf.caythe.frontend;
}

// Parser rules:
compilationUnit     : statements EOF ;
statements          : ( statement NL )* | statement ;
statement           : expression
                    | assignment
                    | printStatement
                    | whileLoop
                    | ifBranch 
                    | NL* ;
whileLoop           : KW_WHILE expression block ;
ifBranch            : KW_IF ifBlock=expression block 
                      ( KW_ELSE KW_IF elseIfBlock=expression block )* 
                      ( KW_ELSE elseBlock=block )? ;
block               : LBRACE blockStatements=statements RBRACE ;
expression          : left=compare ( operator=REL_OPS right=compare )* ;
compare             : left=term ( operator=ADD_OPS right=term )* ;
term                : left=factor ( operator=MUL_OPS right=factor )* ;
factor              : base=atom ( CARET exponent=expression )? ;
atom                : literal
                    | variable
                    | LPAREN value=expression RPAREN ;
literal             : value=( BOOL_VALUE | INTEGER_VALUE | FLOAT_VALUE | STRING_VALUE ) ;
variable            : ID ;
assignment          : id=variable ASSIGN value=expression ;
printStatement      : 'print' LPAREN value=expression RPAREN ;

// Lexer rules:
// Keywords:
KW_WHILE    : 'while' ;
KW_IF       : 'if' ;
KW_ELSE     : 'else' ;

// Operators:
REL_OPS     : EQUAL | NOTEQUAL | GT | LT | GE | LE ;
ADD_OPS     : ADD | SUB ;
MUL_OPS     : MUL | DIV | MOD ;
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
LPAREN          : '(' ;
RPAREN          : ')' ;
LBRACE          : '{' ;
RBRACE          : '}' ;
LBRACK          : '[' ;
RBRACK          : ']' ;
SEMI            : ';' ;
COMMA           : ',' ;
DOT             : '.' ;
DOUBLE_QUOTE    : '"' ;

// Types:
BOOL_VALUE      : TRUE | FALSE ;
TRUE            : 'true' ;
FALSE           : 'false' ;
INTEGER_VALUE   : SIGN? DIGIT+ ;
FLOAT_VALUE     : SIGN? (DIGIT)+ DOT (DIGIT)* EXPONENT?
                | SIGN? DOT (DIGIT)+ EXPONENT?
                | SIGN? (DIGIT)+ EXPONENT ;
fragment
EXPONENT        : ('e'|'E') SIGN? ? DIGIT+ ;
STRING_VALUE    : DOUBLE_QUOTE ( ESCAPED_QUOTE | ~('\n'|'\r') )*? DOUBLE_QUOTE ;
fragment 
ESCAPED_QUOTE   : '\\"';
ID              : LETTER CHARACTER* ;
SIGN            : '+' | '-' ;

// General:
fragment
CHARACTER   : DIGIT | LETTER ;
fragment
LETTER      : [a-zA-Z] ;
fragment
DIGIT       : [0-9] ;
NL          : '\r'? '\n' ;

// Ignored:
ML_COMMENT  : '/*' .*? '*/'         -> skip ;
SL_COMMENT  : '//' ~[\r\n]* NL      -> skip ;
WS          : [ \t\u000C]+          -> skip ;
