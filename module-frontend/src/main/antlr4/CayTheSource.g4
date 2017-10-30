grammar CayTheSource;

@header {
package de.weltraumschaf.caythe.frontend;
}

/*
 * Statement must be terminated with '\n'. Expressions always return a value.
 */

// Parser production rules:
///////////////////////////

type
    : NL*
      typeDeclaration
      importStatement*
      delegateStatement*
      propertyDeclaration*
      constructorDeclaration*
      methodDeclaration*
      EOF
    ;

typeDeclaration
    : visibility=typeVisibility facet=typeFacet NL+
    ;

typeVisibility
    : KW_EXPORT | KW_PUBLIC | KW_PROTECTED | KW_PACKAGE
    ;

typeFacet
    : KW_CLASS | KW_INTERFACE | KW_ANNOTATION | KW_ENUM
    ;

importStatement
    : KW_USE name=fullQualifiedIdentifier (KW_AS alias=IDENTIFIER)? NL+
    ;

delegateStatement
    : KW_DELEGATE delegate=IDENTIFIER NL+
    ;

propertyDeclaration
    : visibility=propertyVisibility propertyType=IDENTIFIER propertyName=IDENTIFIER
      (L_BRACE NL* propertyAccessor R_BRACE)? NL+
    ;

propertyAccessor
    : propertyGetter? propertySetter?
    ;

propertyGetter
    : KW_GET methodBody?
    ;

propertySetter
    : KW_SET (L_PAREN argumentName=IDENTIFIER R_PAREN methodBody)?
    ;

propertyVisibility
    : KW_EXPORT | KW_PUBLIC | KW_PROTECTED | KW_PACKAGE | KW_PRIVATE
    ;

constructorDeclaration
    : visibility=constructorVisibility
      KW_CONSTRUCTOR L_PAREN methodArguments? R_PAREN
      methodBody NL+
    ;

constructorVisibility
    : KW_EXPORT | KW_PUBLIC | KW_PROTECTED | KW_PACKAGE
    ;

methodDeclaration
    : visibility=methodVisibility
      returnType=IDENTIFIER?
      methodName=IDENTIFIER L_PAREN methodArguments? R_PAREN
      methodBody
    ;

methodVisibility
    : KW_EXPORT | KW_PUBLIC | KW_PROTECTED | KW_PACKAGE
    ;

methodArguments
    : methodArgument (COMMA methodArgument)*
    ;

methodArgument
    : argumentType=IDENTIFIER argumentName=IDENTIFIER
    ;

methodBody
    : L_BRACE NL+ statement* R_BRACE NL+
    ;

statement
    :   (assignStatement
        | letStatement
        | constStatement
        | returnStatement
        | breakStatement
        | continueStatement
        | ifExpression
        | loopExpression
        | expressionStatement)
        NL+
    ;

letStatement
    : KW_LET variableType=IDENTIFIER ( IDENTIFIER | assignStatement )
    ;

constStatement
    : KW_CONST constantType=IDENTIFIER assignStatement
    ;

assignStatement
    : assignExpression
    ;

returnStatement
    : KW_RETURN value=expression
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
    | methodCallExpression                                                                # callExpressionAlternative
    | fullQualifiedIdentifier                                                       # fullQualifiedIdentifierAlternative
    | objectCreation                                                                # objectCreationAlternative
    ;

literalExpression
    : literal
    | methodLiteral
    | arrayLiteral
    | hashLiteral
    ;

literal
    : NIL               # nilLiteral
    | BOOLEAN           # booleanLiteral
    | REAL              # realLiteral
    | INTEGER           # integerLiteral
    | STRING            # stringLiteral
    | IDENTIFIER        # identifierLiteral
    ;

methodLiteral
    : IDENTIFIER L_PAREN arguments=functionArguments? R_PAREN body=statementList
    ;

functionArguments
    : IDENTIFIER ( COMMA IDENTIFIER )*
    ;

arrayLiteral
    : L_BRACKET values=methodParameters? R_BRACKET
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
    : KW_IF condition=expression consequence=statementList
      ( KW_ELSE alternative=statementList )?
    ;

loopExpression
    // We want at least one statetement.
    : KW_LOOP body=statementList                                            # endlessLoopExpression
    | KW_LOOP condition=expression body=statementList                       # conditionalLoopExpression
    | KW_LOOP init=loopInit SEMICOLON condition=expression SEMICOLON post=expression
      body=statementList                                                    # traditionalLoopExpression
    ;

loopInit
    : assignExpression ( COMMA assignStatement )?
    ;

assignExpression
    : identifier=assignmentReceiver OP_ASSIGN value=expression
    ;

assignmentReceiver
    : ( KW_THIS DOT )? fullQualifiedIdentifier
    ;

methodCallExpression
    : identifier=fullQualifiedIdentifier L_PAREN arguments=methodParameters? R_PAREN
    ;

statementList
    : L_BRACE statements=statement* R_BRACE
    ;

fullQualifiedIdentifier
    : IDENTIFIER ( DOT IDENTIFIER)*
    ;

objectCreation
    : KW_NEW typeName=IDENTIFIER L_PAREN methodParameters R_PAREN
    ;

methodParameters
    : methodParameter ( COMMA methodParameter)*
    ;

methodParameter
    : parameterName=IDENTIFIER COLON parameterValue=expression
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

OP_AND      : 'and' ;
OP_OR       : 'or' ;
OP_NOT      : 'not' ;

RELOP_LT    : '<' ;
RELOP_LTE   : '<=' ;
RELOP_GT    : '>' ;
RELOP_GTE   : '>=' ;
RELOP_EQ    : '==' ;
RELOP_NEQ   : '!=' ;

// Delimiters:
DOT         : '.' ;
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
KW_LET          : 'let' ;
KW_CONST        : 'const' ;
KW_USE          : 'use' ;
KW_AS           : 'as' ;
KW_DELEGATE     : 'delegate';
KW_GET          : 'get' ;
KW_SET          : 'set' ;
KW_CONSTRUCTOR  : 'constructor';
KW_THIS         : 'this' ;
KW_NEW          : 'new' ;
// Control structures:
KW_RETURN       : 'return' ;
KW_IF           : 'if' ;
KW_ELSE         : 'else' ;
KW_LOOP         : 'loop' ;
KW_BREAK        : 'break' ;
KW_CONTINUE     : 'continue' ;
KW_SWITCH       : 'switch' ;
KW_CASE         : 'case' ;
KW_DEFAULT      : 'default' ;
KW_FALLTHROUGH  : 'fallthrough' ;
// Visibilities:
KW_PRIVATE      : 'private' ;
KW_PACKAGE      : 'package' ;
KW_PROTECTED    : 'protected' ;
KW_PUBLIC       : 'public' ;
KW_EXPORT       : 'export' ;
// Type facets:
KW_CLASS        : 'class' ;
KW_INTERFACE    : 'interface' ;
KW_ANNOTATION   : 'annotation' ;
KW_ENUM         : 'enum' ;

INTEGER : DIGIT+ ;
REAL    : (DIGIT)+ '.' (DIGIT)* EXPONENT?
        | '.' (DIGIT)+ EXPONENT?
        | (DIGIT)+ EXPONENT ;
fragment
EXPONENT: ('e'|'E') ('+' | '-') ? ? DIGIT+ ;
BOOLEAN : ( TRUE | FALSE ) ;
STRING  : '"' ( ~'"' | '\\' '"')* '"' ;

// Literal values:
TRUE    : 'true' ;
FALSE   : 'false' ;
NIL     : 'nil' ;

// Must be defined after keywords. Instead keywords will be recognized as identifier.
IDENTIFIER  : LETTER (ALPHANUM | '-' | '_')* ;
ALPHANUM    : LETTER | DIGIT ;
LETTER      : [a-zA-Z] ;
DIGIT       : [0-9] ;

// Delimiter for expressions:
NL : '\n' ;

// Ignored stuff:
MULTILINE_COMMENT   : '/*' .*? '*/'     -> channel(HIDDEN) ;
SINGLELINE_COMMENT  : '//' .*? '\n'     -> channel(HIDDEN) ;
WHITESPACE          : [ \t\r\u000C]+    -> skip ;
