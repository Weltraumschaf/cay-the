parser grammar CaytheParser;

options {
	tokenVocab=CaytheLexer;
}

/* Parser rules */
compilationUnit
    : importDeclaration*
      typeDeclaration*
      EOF
    ;

importDeclaration
    : K_IMPORT qualifiedName (OP_DOT OP_STAR)?
    ;

typeDeclaration
    : annotationDeclaration
    | classDeclaration
    | interfaceDeclaration
    ;

/* Annotations */
annotationDeclaration
    : modifier? K_ANNOTATION IDENTIFIER OP_LCURLY OP_RCURLY
    ;

/* Classes */
classDeclaration
    : modifier? K_CLASS IDENTIFIER classBody
    ;

classBody
    : OP_LCURLY classBodyDeclaration* OP_RCURLY
    ;

classBodyDeclaration
    : classConstDeclaration
    | classPropertyDeclaration
    | classDelegateDecalration
    | classMethodDeclaration
    ;

classConstDeclaration
    : modifier? K_CONST type IDENTIFIER OP_ASSIGN value
    ;

classPropertyDeclaration
    : K_PROPERTY
      (OP_LPAREN ( K_READ | K_WRITE | K_READWRITE ) OP_RPAREN)?
      type IDENTIFIER (OP_ASSIGN value)?
    ;

classDelegateDecalration
    : K_DELEGATE type IDENTIFIER
    ;

classMethodDeclaration
    : modifier?
      (type (OP_COMMA type)*)?
      IDENTIFIER
      OP_LPAREN formalParameterList? OP_LPAREN (OP_LBRACK OP_RBRACK)*
      block
    ;

/* Interfaces */
interfaceDeclaration
    : modifier? K_INTERFACE IDENTIFIER interfaceBody
    ;

interfaceBody
    : OP_LCURLY interfaceBodyDeclaration* OP_RCURLY
    ;

interfaceBodyDeclaration
    :   interfaceConstDeclaration
    |   interfaceMethodDeclaration
    ;

interfaceConstDeclaration
    : K_CONST type IDENTIFIER '=' value
    ;

interfaceMethodDeclaration
    :   (type (OP_COMMA type)*)?
        IDENTIFIER
        OP_LPAREN formalParameterList? OP_RPAREN (OP_LBRACK OP_RBRACK)*
    ;

/* General */
formalParameterList
    :   formalParameter (OP_COMMA formalParameter)* (OP_COMMA lastFormalParameter)?
    |   lastFormalParameter
    ;

formalParameter
    :   IDENTIFIER IDENTIFIER
    ;

lastFormalParameter
    :   IDENTIFIER OP_ELLIPSIS IDENTIFIER
    ;

modifier
    : K_PUBLIC
    | K_PACKAGE
    ;

qualifiedName
    : IDENTIFIER (OP_DOT IDENTIFIER)*
    ;

/* Statements / Blocks */
block
    : OP_LCURLY blockStatement* OP_RCURLY
    ;

blockStatement
    : localVariableDeclarationStatement
    | statement
    | typeDeclaration
    ;

localVariableDeclarationStatement
    : type IDENTIFIER OP_ASSIGN value
    ;

statement
    : block
    | K_IF parExpression statement (K_ELSE statement)?
    | K_FOR OP_LPAREN forControl OP_RPAREN statement
    | K_WHILE parExpression statement
    | K_DO statement K_WHILE parExpression
    | K_SWITCH parExpression OP_LCURLY
        switchBlockStatementGroup* switchLabel*
      OP_RCURLY
    | K_RETURN expression?
    | K_BREAK
    | K_CONTINUE
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
    : K_CASE constantExpression OP_COLON
    | K_DEFAULT OP_COLON
    ;

forControl
    : enhancedForControl
    | forInit? OP_SEMICOLON expression? OP_SEMICOLON forUpdate?
    ;

forInit
    : localVariableDeclarationStatement
    | expressionList
    ;

enhancedForControl
    : type IDENTIFIER OP_ASSIGN expression
    ;

forUpdate
    : expressionList
    ;

/* Expressions */
parExpression
    : OP_LPAREN expression OP_RPAREN
    ;

expressionList
    : expression (OP_COMMA expression)*
    ;

statementExpression
    : expression
    ;

constantExpression
    : expression
    ;

expression
    : primary
    | expression OP_DOT IDENTIFIER
    | expression OP_LBRACK expression OP_RBRACK
    | expression OP_LPAREN expressionList? OP_RPAREN
    | K_NEW type OP_LPAREN OP_RPAREN
    | expression (OP_INC | OP_DEC)
    | (OP_PLUS | OP_MINUS | OP_INC | OP_DEC) expression
    | (OP_MINUS | K_NOT) expression
    | expression (OP_STAR | OP_SLASH | OP_PERCENT) expression
    | expression (OP_PLUS | OP_MINUS) expression
    | expression ('<=' | '>=' | OP_GREATER | OP_LESS) expression
    | expression (OP_EQUAL | OP_NOTEQUAL) expression
    | expression K_AND expression
    | expression K_OR expression
    | <assoc=right> expression
        ( OP_ASSIGN
        | OP_PLUS_ASSIGN
        | OP_MINUS_ASSIGN
        | OP_STAR_ASSIGN
        | OP_SLASH_ASSIGN
        | OP_PERCENT_ASSIGN )
        expression
    ;

primary
    : OP_LPAREN expression OP_RPAREN
    | value
    | IDENTIFIER
    ;

type
    : IDENTIFIER (OP_LESS IDENTIFIER (OP_COMMA IDENTIFIER)* OP_GREATER)?
    ;

value
    : STRING
    | INTEGER
    | FLOAT
    ;
