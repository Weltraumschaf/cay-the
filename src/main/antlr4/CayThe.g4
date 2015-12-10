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
ifBranch            : KW_IF ifCondition=expression ifBlock=block 
                      ( KW_ELSE KW_IF elseIfCondition=expression elseIfBlock=block )* 
                      ( KW_ELSE elseBlock=block )? ;
block               : LBRACE blockStatements=statements RBRACE ;
expression          : left=compare ( operator=(EQUAL | NOT_EQUAL | GREATER_THAN | LESS_THAN | GREATER_EQUAL | LESS_EQUAL) right=compare )* ;
compare             : left=term ( operator=(ADD | SUB) right=term )* ;
term                : left=factor ( operator=(MUL | DIV | MOD) right=factor )* ;
factor              : base=atom ( CARET exponent=expression )? ;
atom                : literal
                    | variable
                    | LPAREN value=expression RPAREN ;
literal             : value=( BOOL_VALUE | INTEGER_VALUE | FLOAT_VALUE | STRING_VALUE ) ;
variable            : id=ID ;
assignment          : id=variable ASSIGN value=expression ;
printStatement      : 'print' LPAREN value=expression RPAREN ;

// Lexer rules:
// Keywords:
KW_WHILE    : 'while' ;
KW_IF       : 'if' ;
KW_ELSE     : 'else' ;

// Operators:
EQUAL           : '==' ;
LESS_EQUAL      : '<=' ;
GREATER_EQUAL   : '>=' ;
NOT_EQUAL       : '!=' ;
GREATER_THAN    : '>' ;
LESS_THAN       : '<' ;
ADD             : '+' ;
SUB             : '-' ;
MUL             : '*' ;
DIV             : '/' ;
MOD             : '%' ;
ASSIGN      : '=' ;
BANG        : '!' ;
TILDE       : '~' ;
QUESTION    : '?' ;
COLON       : ':' ;
AND         : '&&' ;
OR          : '||' ;
INC         : '++' ;
DEC         : '--' ;
BITAND      : '&' ;
BITOR       : '|' ;
CARET       : '^' ;
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
