lexer grammar CommonLexerRules;

IDENTIFIER  : LETTER ( LETTER | DIGIT )* ;

INTEGER     : DIGIT+ ;
FLOAT       : (DIGIT)+ '.' (DIGIT)* EXPONENT?
            | '.' (DIGIT)+ EXPONENT?
            | (DIGIT)+ EXPONENT ;
fragment
EXPONENT    : ('e'|'E') ('+'|'-')? (DIGIT)+ ;

COMMENT     : ( SL_COMMENT | ML_COMMENT ) -> skip ;
fragment
ML_COMMENT  : '/*' .*? '*/' ;
SL_COMMENT  : '//' ~[\r\n]* '\r'? '\n' ;

NL          : ( '\n' | '\r' | '\r\n' ) -> skip ;
WS          : [ \t]+ -> skip ; // Spaces and tabs.
LETTER      : [a-zA-Z] ;
DIGIT       : [0-9] ;