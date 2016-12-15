grammar CayTheManifest;

import CommonLexerRules;

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
    : KW_IMPORT group=fullQualifiedName ':' artifact=fullQualifiedName ':' version
    ;

fullQualifiedName
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

version
    : major=DIGIT+ '.' minor=DIGIT+ '.' patch=DIGIT+
    ;

// Lexer rules:
// Keywords:
KW_GROUP        : 'group' ;
KW_ARTIFACT     : 'artifact' ;
KW_VERSION      : 'version' ;
KW_NAMESPACE    : 'namespace' ;
KW_IMPORT       : 'import' ;
