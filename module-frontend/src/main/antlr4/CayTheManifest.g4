grammar CayTheManifest;

@header {
package de.weltraumschaf.caythe.frontend;
}

// Parser rules:
manifest
    : statement* EOF
    ;

statement 
    : expression NL
    | NL
    ;

expression
    : groupDirective
    | artifactDirective
    | versionDirective
    | namespaceDirective
    | importDirectives?
    ;

groupDirective
    : KW_GROUP fullQualifiedName
    ;

artifactDirective
    : KW_ARTIFACT fullQualifiedName
    ;

versionDirective
    : KW_VERSION version
    ;

namespaceDirective
    : KW_NAMESPACE fullQualifiedName
    ;

importDirectives
    : importDirective (NL importDirective)*
    ;

importDirective
    : KW_IMPORT coordinate
    ;

coordinate
    : group=fullQualifiedName ':' artifact=fullQualifiedName ':' version
    ;

fullQualifiedName
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

version
    : major=NUMBER '.' minor=NUMBER '.' patch=NUMBER ('-' identifiers=IDENTIFIER)?
    ;

// Lexer rules:
// Keywords:
KW_GROUP        : 'group' ;
KW_ARTIFACT     : 'artifact' ;
KW_VERSION      : 'version' ;
KW_NAMESPACE    : 'namespace' ;
KW_IMPORT       : 'import' ;

IDENTIFIER
    :   LETTER (CHARACTER | '-')*
    ;

NUMBER
    : DIGIT+
    ;

CHARACTER       : DIGIT | LETTER ;
LETTER          : LOWER_LETTER | UPPER_LETTER ;
LOWER_LETTER    : [a-z] ;
UPPER_LETTER    : [A-Z] ;
DIGIT           : [0-9] ;

NL              : '\r'? '\n' ;

// Ignored:
ML_COMMENT  : '/*' .*? '*/'         -> skip ;
SL_COMMENT  : '//' ~[\r\n]* NL      -> skip ;
WS          : [ \t\u000C]+          -> skip ;
