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
    : modifier? 'const' type IDENTIFIER '=' value
    ;

classPropertyDeclaration
    : 'property' ('(' ( 'read' | 'write' | 'readwrite' ) ')')? IDENTIFIER IDENTIFIER ('=' value)?
    ;

classDelegateDecalration
    : 'delegate' type IDENTIFIER
    ;

classMethodDeclaration
    : modifier? (type (',' type)*)? IDENTIFIER '(' formalParameterList? ')' ('[' ']')* block
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
    : 'const' type IDENTIFIER '=' value
    ;

interfaceMethodDeclaration
    :   (type (',' type)*)? IDENTIFIER '(' formalParameterList? ')' ('[' ']')*
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

modifier
    : 'public'
    | 'package'
    ;

qualifiedName
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

/* Statements / Blocks */
block
    : '{' blockStatement* '}'
    ;

blockStatement
    : localVariableDeclarationStatement
    | statement
    | typeDeclaration
    ;

localVariableDeclarationStatement
    : type IDENTIFIER '=' value
    ;

statement
    : block
    | 'if' parExpression statement ('else' statement)?
    | 'for' '(' forControl ')' statement
    | 'while' parExpression statement
    | 'do' statement 'while' parExpression
    | 'switch' parExpression '{' switchBlockStatementGroup* switchLabel* '}'
    | 'return' expression?
    | 'break'
    | 'continue'
    | statementExpression
    ;

/*
 * Matches cases then statements, both of which are mandatory.
 * To handle empty cases at the end, we add switchLabel* to statement.
 */
switchBlockStatementGroup
    : switchLabel+ blockStatement+
    ;

switchLabel
    : 'case' constantExpression ':'
    | 'default' ':'
    ;

forControl
    : enhancedForControl
    | forInit? ';' expression? ';' forUpdate?
    ;

forInit
    : localVariableDeclarationStatement
    | expressionList
    ;

enhancedForControl
    : type IDENTIFIER '=' expression
    ;

forUpdate
    : expressionList
    ;

/* Expressions */
parExpression
    : '(' expression ')'
    ;

expressionList
    : expression (',' expression)*
    ;

statementExpression
    : expression
    ;

constantExpression
    : expression
    ;

expression
    : primary
    | expression '.' IDENTIFIER
    | expression '[' expression ']'
    | expression '(' expressionList? ')'
    | 'new' type '(' ')'
    | expression ('++' | '--')
    | ('+'|'-'|'++'|'--') expression
    | ('~'|'!') expression
    | expression ('*'|'/'|'%') expression
    | expression ('+'|'-') expression
    | expression ('<' '<' | '>' '>' '>' | '>' '>') expression
    | expression ('<=' | '>=' | '>' | '<') expression
    | expression ('==' | '!=') expression
    | expression '&' expression
    | expression '^' expression
    | expression '|' expression
    | expression '&&' expression
    | expression '||' expression
    | expression '?' expression ':' expression
    | <assoc=right> expression
        ( '='
        | '+='
        | '-='
        | '*='
        | '/='
        | '&='
        | '|='
        | '^='
        | '%='
        )
        expression
    ;

primary
    : '(' expression ')'
    | value
    | IDENTIFIER
    ;

type
    : IDENTIFIER ('<' IDENTIFIER (',' IDENTIFIER)* '>')?
    ;

value
    : STRING
    | INTEGER
    | FLOAT
    ;

/* Lexer rules. */
STRING      : '"' .*? '"' ;
INTEGER     : SIGN? DIGIT+ ;
FLOAT       : SIGN? DIGIT+ '.' DIGIT* EXPONENT?
            | '.' DIGIT+ EXPONENT?
            | DIGIT+ EXPONENT ;
fragment
EXPONENT    : ('e'|'E') ('+'|'-')? DIGIT+ ;

SIGN        : [+-] ;

IDENTIFIER  : LETTER ( LETTER | DIGIT )* ;
COMMENT     : ( SL_COMMENT | ML_COMMENT ) -> skip ;
fragment
ML_COMMENT  : '/*' .*? '*/' ;
SL_COMMENT  : '//' ~[\r\n]* '\r'? '\n' ;

LETTER      : [a-zA-Z] ;
DIGIT       : [0-9] ;
NL          : ( '\n' | '\r' | '\r\n' ) -> skip ;
WS          : [ \t]+ -> skip ; // Spaces and tabs.