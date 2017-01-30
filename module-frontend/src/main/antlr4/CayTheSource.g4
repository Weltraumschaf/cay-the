grammar CayTheSource;

@header {
package de.weltraumschaf.caythe.frontend;
}

/*
 * Statements must be terminated with ';'. Expressions always return a value.
 */

// Parser production rules:
///////////////////////////

unit
   : statement* EOF
   ;

statement
    : assignStatement
    | letStatement
    | constStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | ifExpression
    | loopExpression
    | expressionStatement
    | NL
    ;

letStatement
    : KW_LET ( IDENTIFIER NL | assignStatement )
    ;

constStatement
    : KW_CONST assignStatement
    ;

assignStatement
    : assignExpression NL
    ;

returnStatement
    : KW_RETURN value=expression NL
    ;

breakStatement
    : KW_BREAK NL
    ;

continueStatement
    : KW_CONTINUE NL
    ;

expressionStatement
    : expression NL
    ;

expression
    // Here we define the operator precedence because ANTLR4 can deal with left recursion.
    : operator=( OP_NOT | OP_SUB ) operand=expression                              # negationOperation
    | <assoc=right> firstOperand=expression OP_POW secondOperand=expression                                    # powerOperation
    | firstOperand=expression operator=( OP_MUL | OP_DIV | OP_MOD ) secondOperand=expression                   # multiplicativeOperation
    | firstOperand=expression operator=( OP_ADD | OP_SUB ) secondOperand=expression                            # additiveOperation
    | firstOperand=expression operator=( RELOP_LT |RELOP_LTE | RELOP_GT | RELOP_GTE ) secondOperand=expression # relationOperation
    | firstOperand=expression operator=( RELOP_EQ | RELOP_NEQ ) secondOperand=expression                       # equalOperation
    | firstOperand=expression OP_AND secondOperand=expression                                                  # logicalAndOperation
    | firstOperand=expression OP_OR secondOperand=expression                                                   # logicalOrOperation
    | L_PAREN expression R_PAREN                                                    # nestedExpression // Grouped expression.
    | identifier=expression L_BRACKET index=expression R_BRACKET                    # subscriptExpression // foo[1] or bar["name"].
    | literalExpression                                                             # literalExpressionAlternative
    | ifExpression                                                                  # ifExpressionAlternative
    | callExpression                                                                # callExpressionAlternative
    ;

literalExpression
    : literal
    | functionLiteral
    | arrayLiteral
    | hashLiteral
    ;

literal
    : NULL              # nullLiteral
    | BOOLEAN           # booleanLiteral
    | FLOAT             # floatLiteral
    | INTEGER           # integerLiteral
    | STRING            # stringLiteral
    | IDENTIFIER        # identifierLiteral
    ;

functionLiteral
    : KW_FUNCTION L_PAREN arguments=functionArguments? R_PAREN L_BRACE body=statement* R_BRACE
    ;

functionArguments
    : IDENTIFIER ( COMMA IDENTIFIER )*
    ;

arrayLiteral
    : L_BRACKET values=expressionList? R_BRACKET
    ;

hashLiteral
    : L_BRACE values=hashValues? R_BRACE
    ;

hashValues
    : hashPair ( COMMA hashPair )*
    ;

hashPair
    : key=expression COLON value=expression
    ;

ifExpression
    // We want at least one statetement.
    : KW_IF condition=expression L_BRACE consequence=statement+ R_BRACE
        ( KW_ELSE L_BRACE alternative=statement+ R_BRACE )?
    ;

loopExpression
    // We want at least one statetement.
    : KW_LOOP L_BRACE body=statement+ R_BRACE                                            # endlessLoopExpression
    | KW_LOOP condition=expression L_BRACE body=statement+ R_BRACE                       # conditionalLoopExpression
    | KW_LOOP init=loopInit SEMICOLON condition=expression SEMICOLON post=expression
      L_BRACE body=statement+ R_BRACE                                                    # traditionalLoopExpression
    ;

loopInit
    : assignExpression ( COMMA assignStatement )?
    ;

assignExpression
    : identifier=IDENTIFIER OP_ASSIGN value=expression
    ;

callExpression
    : identifier=IDENTIFIER L_PAREN arguments=expressionList? R_PAREN
    ;

expressionList
    : expression ( COMMA expression )*
    ;

// Lexer tokens:
////////////////

// Operators:
OP_ASSIGN   : '=' ;
OP_ADD      : '+' ;
OP_SUB      : '-' ;
OP_MUL      : '*' ;
OP_DIV      : '/' ;
OP_MOD      : '%' ;
OP_POW      : '^' ;

OP_AND      : '&&' ;
OP_OR       : '||' ;
OP_NOT      : '!' ;

RELOP_LT    : '<' ;
RELOP_LTE   : '<=' ;
RELOP_GT    : '>' ;
RELOP_GTE   : '>=' ;
RELOP_EQ    : '==' ;
RELOP_NEQ   : '!=' ;


// Delimiters:
COMMA       : ',' ;
COLON       : ':' ;
SEMICOLON   : ';' ;
L_PAREN     : '(' ;
R_PAREN     : ')' ;
L_BRACE     : '{' ;
R_BRACE     : '}' ;
L_BRACKET   : '[' ;
R_BRACKET   : ']' ;


// Keywords:
KW_LET      : 'let' ;
KW_CONST    : 'const' ;
KW_FUNCTION : 'fn' ;
// Control structures:
KW_RETURN   : 'return' ;
KW_IF       : 'if' ;
KW_ELSE     : 'else' ;
KW_LOOP     : 'loop' ;
KW_BREAK    : 'break' ;
KW_CONTINUE : 'continue' ;
//KW_SWITCH   : 'switch' ;
//KW_CASE     : 'case' ;
//KW_DEFAULT  : 'default' ;

INTEGER : DIGIT+ ;
FLOAT   : (DIGIT)+ '.' (DIGIT)* EXPONENT?
        | '.' (DIGIT)+ EXPONENT?
        | (DIGIT)+ EXPONENT ;
fragment
EXPONENT: ('e'|'E') ('+' | '-') ? ? DIGIT+ ;
BOOLEAN : ( TRUE | FALSE ) ;
STRING  : '"' ( ~'"' | '\\' '"')* '"' ;

// Must be defined after keywords. Instead keywords will be recognized as identifier.
IDENTIFIER  : LETTER ALPHANUM* ;
ALPHANUM    : LETTER | DIGIT ;
LETTER      : [a-zA-Z] ;
DIGIT       : [0-9] ;

// Literal values:
TRUE    : 'true' ;
FALSE   : 'false' ;
NULL    : 'null' ;

// Delimiter for expressions:
NL : '\n' ;

// Ignored stuff:
MULTILINE_COMMENT   : '/*' .*? '*/'     -> channel(HIDDEN) ;
SINGLELINE_COMMENT  : '//' .*? '\n'     -> channel(HIDDEN) ;
WHITESPACE          : [ \t\r\u000C]+  -> skip ;
