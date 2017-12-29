/*
 * This is the ANTLR grammar definition for source code.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
grammar CayTheSource;

@header {
package de.weltraumschaf.caythe.frontend;
}

/*
 * This grammar descirbse the source code of all top level units: class, interface,
 * annotation, enum.
 *
 * Declarations and statements must be terminated with a newline (NL = '\n').
 */


// The main entry rule to declare a type.
type
    : NL*
      typeDeclaration
      importDeclaration*
      delegateDeclaration*
      propertyDeclaration*
      methodDeclaration*
      EOF
    ;

/*
 * Declarations:
 *
 * Main declratarions. A type is declared of:
 *  - it's visibility and facet.
 *  - imports of other types.
 *  - properties.
 *  - delegates.
 *  - constructors.
 *  - methods.
 */

typeDeclaration
    : visibility=typeVisibility facet=typeFacet NL+
    ;

// Type can't be private because such a type would not be accessible by anything.
typeVisibility
    : KW_EXPORT | KW_PUBLIC | KW_PROTECTED | KW_PACKAGE
    ;

typeFacet
    : KW_CLASS | KW_INTERFACE | KW_ANNOTATION | KW_ENUM
    ;

importDeclaration
    : KW_USE name=fullQualifiedIdentifier (KW_AS alias=IDENTIFIER)? NL+
    ;

delegateDeclaration
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
    : KW_GET block?
    ;

propertySetter
    : KW_SET block?
    ;

propertyVisibility
    : KW_EXPORT | KW_PUBLIC | KW_PROTECTED | KW_PACKAGE | KW_PRIVATE
    ;

// Constructors can't be private because it make no sense to not instantiate
// an object, when there are no static methods.
constructorVisibility
    : KW_EXPORT | KW_PUBLIC | KW_PROTECTED | KW_PACKAGE
    ;

methodDeclaration
    : visibility=methodVisibility
      returnType=IDENTIFIER?
      methodName=IDENTIFIER L_PAREN methodArguments? R_PAREN
      block
    ;

methodVisibility
    : KW_EXPORT | KW_PUBLIC | KW_PROTECTED | KW_PACKAGE | KW_PRIVATE
    ;

// Int the method declaration we name it "arguments". Later in the method
// invocation we name the passed in values "parameters".
methodArguments
    : methodArgument (COMMA methodArgument)*
    ;

methodArgument
    : argumentType=IDENTIFIER argumentName=IDENTIFIER
    ;

/*
 * Statements:
 *
 * Statements are the top level building unit of a block used in methods (also
 * property accessors) and constructors.
 */

block
    : L_BRACE NL+ statements=statement* R_BRACE NL+
    ;

statement
    :   ( assignStatement
        | letStatement
        | constStatement
        | returnStatement
        | breakStatement
        | continueStatement
        | ifExpression
        | loopExpression
        | expressionStatement )
        NL+
    ;

letStatement
    : KW_LET variableType=IDENTIFIER ( variableName=IDENTIFIER | assignStatement )
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

/*
 * Expressions always return a value.
 */

expression
    // Here we define the operator precedence (top to bottom) because ANTLR4 can
    // deal with left recursion.
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
    | methodCallExpression                                                          # callExpressionAlternative
    | fullQualifiedIdentifier                                                       # fullQualifiedIdentifierAlternative
    | newObjectExpression                                                           # newObjectExpressionAlternative
    ;

literalExpression
    : literal
    | arrayLiteral
    | hashLiteral
    ;

ifExpression
    // We want at least one statetement.
    : KW_IF condition=expression consequence=block
      ( KW_ELSE alternative=block )?
    ;

loopExpression
    // We want at least one statetement.
    : KW_LOOP body=block                                            # endlessLoopExpression
    | KW_LOOP condition=expression body=block                       # conditionalLoopExpression
    | KW_LOOP init=loopInit SEMICOLON condition=expression SEMICOLON post=expression
      body=block                                                    # traditionalLoopExpression
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

newObjectExpression
    : typeName=IDENTIFIER DOT KW_NEW L_PAREN R_PAREN
    ;

methodParameters
    : methodParameter ( COMMA methodParameter)*
    ;

methodParameter
    : parameterName=IDENTIFIER COLON parameterValue=expression
    ;

/*
 * Literals:
 */

literal
    : NIL               # nilLiteral
    | BOOLEAN           # booleanLiteral
    | REAL              # realLiteral
    | INTEGER           # integerLiteral
    | STRING            # stringLiteral
    | IDENTIFIER        # identifierLiteral
    ;

arrayLiteral
    : L_BRACKET values=arrayValues? R_BRACKET
    ;

arrayValues
    : expression ( COMMA expression )*
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

fullQualifiedIdentifier
    : IDENTIFIER ( DOT IDENTIFIER)*
    ;

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
