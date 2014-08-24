lexer grammar CommonLexerRules;

IDENTIFIER  : LETTER ( LETTER | [0-9] )* ;
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

NL          : ( '\n' | '\r' | '\r\n' ) -> skip ;
WS          : [ \t]+ -> skip ; // Spaces and tabs.