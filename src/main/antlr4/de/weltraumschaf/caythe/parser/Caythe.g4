grammar Caythe;

unit        : statement? | ( statement NL )* ;
statement   : expression                                  # expressionStatement
            | id=IDENTIFIER OP_EQUAL val=expression       # assignStatement
            ;
expression  : left=expression operator=( OP_STAR | OP_SLASH ) right=expression  # mulDivExpression
            | left=expression operator=( OP_PLUS | OP_MINUS ) right=expression  # addSubExpression
            | val=value                                         # valueExpression
            | id=IDENTIFIER                                     # identiferExpression
            | OP_LPAREN inParens=expression OP_RPAREN           # parenExpression
            ;
value       : INTEGER | FLOAT | BOOLEAN ;

OP_ASSIGN   : '='  ;
OP_EQUAL    : '==' ;
OP_STAR     : '*'  ;
OP_SLASH    : '/'  ;
OP_MINUS    : '-'  ;
OP_PLUS     : '+'  ;
OP_INC      : '++' ;
OP_DEC      : '--' ;
OP_LPAREN   : '('  ;
OP_RPAREN   : ')'  ;
OP_LCURLY   : '{'  ;
OP_RCURLY   : '}'  ;
OP_LBRACK   : '['  ;
OP_RBRACK   : ']'  ;

NL          : ( '\n' | '\r' |'\r\n' ) -> skip ;
WS          : [ \t]+ -> skip ;

IDENTIFIER  : LETTER (LETTER | [0-9])* ;
fragment
LETTER      : [a-zA-Z] ;
INTEGER     : [0-9]+ ;
FLOAT       : ([0-9])+ '.' ([0-9])* EXPONENT?
            | '.' ([0-9])+ EXPONENT?
            | ([0-9])+ EXPONENT ;
fragment
EXPONENT    : ('e'|'E') ('+'|'-')? ([0-9])+ ;

COMMENT     : ( SL_COMMENT | ML_COMMENT ) -> skip ;
fragment
ML_COMMENT  : '/*' .*? '*/' ;
SL_COMMENT  : '//' ~[\r\n]* '\r'? '\n' ;