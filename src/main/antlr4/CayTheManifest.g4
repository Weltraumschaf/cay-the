grammar CayTheManifest;

import CommonLexerRules;

@header {
package de.weltraumschaf.caythe.frontend;
}

// Parser rules:
manifest
    :
        groupDirective
        artifactDirective
        versionDirective
        namespace
        importDirective*
        EOF
    ;

groupDirective
    : KW_GROUP namespace NL+
    ;

artifactDirective
    : KW_ARTIFACT namespace NL+
    ;

versionDirective
    : KW_VERSION version NL+
    ;

namespaceDirective
    : KW_NAMESPACE namespace NL+
    ;

importDirective
    : KW_IMPORT namespace ':' namespace ':' version NL+
    ;

namespace
    :   IDENTIFIER ('.' IDENTIFIER)*
    ;

version
    : DIGIT+ '.' DIGIT+ '.' DIGIT+
    ;

// Lexer rules:
// Keywords:
KW_GROUP        : 'group' ;
KW_ARTIFACT     : 'artifact' ;
KW_VERSION      : 'version' ;
KW_NAMESPACE    : 'namespace' ;
KW_IMPORT       : 'import' ;
