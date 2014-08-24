grammar Caythe;

import CommonLexerRules, Keywords, Operators;

// General rules.
unit            : imports | annotationStmnt | classStmnt | interfaceStmnt ;
imports         : importStmnt? | ( importStmnt )* ;
importStmnt     : K_IMPORT fullQualifiedName=TYPENAME NL ;
annotationStmnt : modifier=K_PUBLIC? K_ANNOTATION IDENTIFIER '{' annotationBody '}' ;
classStmnt      : modifier=K_PUBLIC? K_CLASS IDENTIFIER '{' classBody '}' ;
interfaceStmnt  : modifier=K_PUBLIC? K_INTERFACE IDENTIFIER '{' interfaceBody '}' ;
// Type rules.
annotationBody  : NL ;
classBody       : NL ;
interfaceBody   : NL ;

foo         : statement? | ( statement )* ;
statement   : expression NL                         // expressionStatement
            | id=IDENTIFIER OP_EQUAL val=expression // assignStatement
            ;
expression  : left=expression operator=( OP_STAR | OP_SLASH ) right=expression  
            | left=expression operator=( OP_PLUS | OP_MINUS ) right=expression  
            | val=value                                         
            | id=IDENTIFIER                                     
            | OP_LPAREN inParens=expression OP_RPAREN           
            ;
value       : INTEGER | FLOAT ;

TYPENAME    : IDENTIFIER ( '.' IDENTIFIER )* ;
