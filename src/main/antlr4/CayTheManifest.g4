grammar CayTheManifest;

import CommonLexerRules;

@header {
package de.weltraumschaf.caythe.frontend;
}

// Parser rules:
manifest
    : EOF
    ;

// Lexer rules:
// Keywords:
KW_GROUP        : 'group' ;
KW_ARTIFACT     : 'artifact' ;
KW_VERSION      : 'version' ;
KW_NAMESPACE    : 'namespace' ;
KW_IMPORT       : 'import' ;
