lexer grammar CaytheLexer;

// Reserved keywords:
K_AND           : 'and'         ;
K_ANNOTATION    : 'annotation'  ;
K_BREAK         : 'break'       ;
K_CASE          : 'case'        ;
K_CLASS         : 'class'       ;
K_CONST         : 'const'       ;
K_CONTINUE      : 'continue'    ;
K_DEFAULT       : 'default'     ;
K_DELEGATE      : 'delegate'    ;
K_DO            : 'do'          ;
K_ELSE          : 'else'        ;
K_FOR           : 'for'         ;
K_IF            : 'if'          ;
K_IMPLEMENTS    : 'implements'  ;
K_IMPORT        : 'import'      ;
K_INTERFACE     : 'interface'   ;
K_NEW           : 'new'         ;
K_NOT           : 'not'         ;
K_OR            : 'or'          ;
K_PACKAGE       : 'package'     ;
K_PROPERTY      : 'property'    ;
K_PUBLIC        : 'public'      ;
K_READ          : 'read'        ;
K_READWRITE     : 'readwrite'   ;
K_RETURN        : 'return'      ;
K_SWITCH        : 'switch'      ;
K_WHILE         : 'while'       ;
K_WRITE         : 'write'       ;

// Math:
OP_MINUS            : '-'   ;
OP_PLUS             : '+'   ;
OP_STAR             : '*'   ;
OP_SLASH            : '/'   ;
OP_PERCENT          : '%'   ;
// Assign:
OP_ASSIGN           : '='   ;
OP_PLUS_ASSIGN      : '+='  ;
OP_MINUS_ASSIGN     : '-='  ;
OP_STAR_ASSIGN      : '*='  ;
OP_SLASH_ASSIGN     : '/='  ;
OP_PERCENT_ASSIGN   : '%='  ;
OP_INC              : '++'  ;
OP_DEC              : '--'  ;
// Comparing:
OP_EQUAL            : '=='  ;
OP_NOTEQUAL         : '!='  ;
OP_LESS_EQUAL       : '<='  ;
OP_GREATER_EQUAL    : '>='  ;
OP_LESS             : '<'   ;
OP_GREATER          : '>'   ;
// Parenthesis:
OP_LPAREN       : '('   ;
OP_RPAREN       : ')'   ;
OP_LCURLY       : '{'   ;
OP_RCURLY       : '}'   ;
OP_LBRACK       : '['   ;
OP_RBRACK       : ']'   ;
// Punctuation:
OP_DOT          : '.'   ;
OP_COMMA        : ','   ;
OP_ELLIPSIS     : '...' ;
OP_COLON        : ':'   ;
OP_SEMICOLON    : ';'   ;

// Comments:
DOC_COMMENT     : '/**' .*? ('*/' | EOF)    -> skip ;
BLOCK_COMMENT   : '/*' .*? ('*/' | EOF)     -> skip ;
LINE_COMMENT    : '//' ~[\r\n]*             -> skip ;

// Values:
STRING      : '"' .*? '"' ;
INTEGER     : SIGN? DIGIT+ ;
FLOAT       : SIGN? DIGIT+ '.' DIGIT* EXPONENT?
            | '.' DIGIT+ EXPONENT?
            | DIGIT+ EXPONENT ;
fragment
EXPONENT    : ('e'|'E') ('+'|'-')? DIGIT+ ;

SIGN        : [+-] ;

// General:
IDENTIFIER  : LETTER ( LETTER | DIGIT )* ;

LETTER      : [a-zA-Z] ;
DIGIT       : [0-9] ;
NL          : '\r'? '\n'    -> channel(HIDDEN) ;
WS          : [ \t\f]+      -> skip ; // Space, tab and formfeed.
