lexer grammar CommonLexerRules;

fragment
CHARACTER       : DIGIT | LETTER ;
fragment
LETTER          : LOWER_LETTER | UPPER_LETTER ;
fragment
LOWER_LETTER    : [a-z] ;
fragment
UPPER_LETTER    : [A-Z] ;
fragment
DIGIT       : [0-9] ;

NL          : '\r'? '\n' ;

// Ignored:
ML_COMMENT  : '/*' .*? '*/'         -> skip ;
SL_COMMENT  : '//' ~[\r\n]* NL      -> skip ;
WS          : [ \t\u000C]+          -> skip ;
