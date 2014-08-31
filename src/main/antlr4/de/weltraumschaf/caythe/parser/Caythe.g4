grammar Caythe;

/* Parser rules */
compilationUnit
    : importDeclaration* typeDeclaration* EOF
    ;
    
importDeclaration
    : 'import' qualifiedName ('.' '*')?
    ;
    
typeDeclaration
    : annotationDeclaration
    | classDeclaration
    | interfaceDeclaration
    ;

/* Annotations */
annotationDeclaration
    : modifier? 'annotation' IDENTIFIER '{' '}' 
    ;

/* Classes */
classDeclaration
    : modifier? 'class' IDENTIFIER classBody
    ;

classBody
    : '{' classBodyDeclaration* '}' 
    ;
    
classBodyDeclaration    
    : classConstDeclaration
    | classPropertyDeclaration
    | classDelegateDecalration
    | classMethodDeclaration
    ;

classConstDeclaration
    : modifier? 'const' IDENTIFIER IDENTIFIER '=' value
    ;

classPropertyDeclaration
    : 'property' ('(' ( 'read' | 'write' | 'readwrite' ) ')')? IDENTIFIER IDENTIFIER ('=' value)?
    ;
    
classDelegateDecalration
    : 'delegate' IDENTIFIER IDENTIFIER
    ;

classMethodDeclaration
    : modifier? IDENTIFIER? (',' IDENTIFIER)* IDENTIFIER '(' formalParameterList? ')' ('[' ']')*
    ;

/* Interfaces */        
interfaceDeclaration
    : modifier? 'interface' IDENTIFIER interfaceBody
    ;

interfaceBody
    : '{' interfaceBodyDeclaration* '}' 
    ;

interfaceBodyDeclaration
    :   interfaceConstDeclaration
    |   interfaceMethodDeclaration
    ;

interfaceConstDeclaration
    : 'const' IDENTIFIER IDENTIFIER '=' value
    ;

interfaceMethodDeclaration
    :   IDENTIFIER? (',' IDENTIFIER)* IDENTIFIER '(' formalParameterList? ')' ('[' ']')*
    ;

/* General */
formalParameterList
    :   formalParameter (',' formalParameter)* (',' lastFormalParameter)?
    |   lastFormalParameter
    ;

formalParameter
    :   IDENTIFIER IDENTIFIER
    ;

lastFormalParameter
    :   IDENTIFIER '...' IDENTIFIER
    ;
        
value
    : STRING
    | INTEGER
    | FLOAT
    ;

modifier 
    : 'public'
    | 'package'
    ;
    
qualifiedName
    :   IDENTIFIER ('.' IDENTIFIER)*
    ;
        
/* Lexer rules. */
STRING      : '"' .*? '"' ;
INTEGER     : DIGIT+ ;
FLOAT       : (DIGIT)+ '.' (DIGIT)* EXPONENT?
            | '.' (DIGIT)+ EXPONENT?
            | (DIGIT)+ EXPONENT ;
fragment
EXPONENT    : ('e'|'E') ('+'|'-')? (DIGIT)+ ;

IDENTIFIER  : LETTER ( LETTER | DIGIT )* ;
COMMENT     : ( SL_COMMENT | ML_COMMENT ) -> skip ;
fragment
ML_COMMENT  : '/*' .*? '*/' ;
SL_COMMENT  : '//' ~[\r\n]* '\r'? '\n' ;

LETTER      : [a-zA-Z] ;
DIGIT       : [0-9] ;
NL          : ( '\n' | '\r' | '\r\n' ) -> skip ;
WS          : [ \t]+ -> skip ; // Spaces and tabs.