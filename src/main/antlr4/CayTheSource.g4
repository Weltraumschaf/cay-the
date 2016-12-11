grammar CayTheSource;

@header {
package de.weltraumschaf.caythe.frontend;
}

// Parser rules:
compilationUnit     
    : statements EOF 
    ;
statements          
    : ( statement NL )* | statement 
    ;
statement           
    : block
    | variableDeclaration
    | constantDeclaration
    | assignment
    | functionCall
    | functionDeclaration
    | whileLoop
    | ifBranch 
    | orExpression
    | NL?
    ;
returnStatement
    : KW_RETURN (ret+=orExpression (',' ret+=orExpression)* )? NL
    ;
variableDeclaration
    : KW_VAR type=ID id=ID ( ASSIGN value=orExpression )?
    ;
constantDeclaration
    : KW_CONST type=ID id=ID ASSIGN value=orExpression
    ;
assignment
    : id=ID ASSIGN value=orExpression
    ;
functionCall
    : id=ID LPAREN ( args+=orExpression (',' args+=orExpression)* )? RPAREN 
    ;
functionDeclaration
    : KW_FUNC (returnTypes+=ID (',' returnTypes+=ID)* )? id=ID 
      LPAREN (args+=formalArgument (',' args+=formalArgument)* )? RPAREN 
      body=block
    ;
formalArgument
    : type=ID id=ID
    ;
whileLoop           
    : KW_WHILE condition=orExpression block 
    ;
ifBranch            
    : KW_IF ifCondition=orExpression ifBlock=block 
    ( KW_ELSE KW_IF elseIfCondition=orExpression elseIfBlock=block )* 
    ( KW_ELSE elseBlock=block )? 
    ;
block               
    : LBRACE blockStatements=statements returnStatement? RBRACE 
    ;

orExpression           
    : left=andExpression ( OR right=andExpression )* 
    ;
andExpression          
    : left=equalExpression ( AND right=equalExpression )* 
    ;
equalExpression        
    : left=relationExpression 
    ( operator=(EQUAL | NOT_EQUAL) right=relationExpression )* 
    ;
relationExpression     
    : left=simpleExpression 
    ( operator=(GREATER_THAN | LESS_THAN | GREATER_EQUAL | LESS_EQUAL) 
      right=simpleExpression )*
    ;
simpleExpression       
    : left=term ( operator=(ADD | SUB) right=term )* 
    ;
term                    
    : left=factor ( operator=(MUL | DIV | MOD) right=factor )*
    ;
factor                  
    : base=atom ( CARET exponent=simpleExpression )? 
    ;
atom    
    : functionCall
    | variableOrConstantDereference
    | constant
    | LPAREN orExpression RPAREN 
    | negation
    ;
negation
    : NOT atom
    ;
constant             
    : value=( BOOL_VALUE | INTEGER_VALUE | FLOAT_VALUE | STRING_VALUE ) 
    ;
variableOrConstantDereference
    : id=ID
    ;
    
// Lexer rules:
// Keywords:
KW_WHILE    : 'while' ;
KW_IF       : 'if' ;
KW_ELSE     : 'else' ;
KW_VAR      : 'var' ;
KW_CONST    : 'const' ;
KW_FUNC     : 'func' ;
KW_RETURN   : 'return' ;

// Operators:
EQUAL           : '==' ;
LESS_EQUAL      : '<=' ;
GREATER_EQUAL   : '>=' ;
NOT_EQUAL       : '!=' ;
GREATER_THAN    : '>' ;
LESS_THAN       : '<' ;
ADD             : '+' ;
SUB             : '-' ;
MUL             : '*' ;
DIV             : '/' ;
MOD             : '%' ;
ASSIGN      : '=' ;
NOT         : '!' ;
TILDE       : '~' ;
QUESTION    : '?' ;
COLON       : ':' ;
AND         : '&&' ;
OR          : '||' ;
INC         : '++' ;
DEC         : '--' ;
BITAND      : '&' ;
BITOR       : '|' ;
CARET       : '^' ;
ARROW       : '->' ;
COLONCOLON  : '::' ;

// Structureing:
LPAREN          : '(' ;
RPAREN          : ')' ;
LBRACE          : '{' ;
RBRACE          : '}' ;
LBRACK          : '[' ;
RBRACK          : ']' ;
SEMI            : ';' ;
COMMA           : ',' ;
DOT             : '.' ;
DOUBLE_QUOTE    : '"' ;

// Types:
NIL             : 'nil' ;
BOOL_VALUE      : TRUE | FALSE ;
TRUE            : 'true' ;
FALSE           : 'false' ;
INTEGER_VALUE   : SIGN? DIGIT+ ;
FLOAT_VALUE     : SIGN? (DIGIT)+ DOT (DIGIT)* EXPONENT?
                | SIGN? DOT (DIGIT)+ EXPONENT?
                | SIGN? (DIGIT)+ EXPONENT ;
fragment
EXPONENT        : ('e'|'E') SIGN? ? DIGIT+ ;
STRING_VALUE    : DOUBLE_QUOTE ( ESCAPED_QUOTE | ~('\n'|'\r') )*? DOUBLE_QUOTE ;
fragment 
ESCAPED_QUOTE   : '\\"';
ID              : LETTER CHARACTER* ;
SIGN            : '+' | '-' ;

// General:
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
