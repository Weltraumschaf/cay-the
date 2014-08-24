grammar Test;

import CommonLexerRules, Keywords;

compilationUnit
    : importDeclaration* typeDeclaration* EOF
    ;

importDeclaration
    : K_IMPORT qualifiedName ('.' '*')? NL
    ;

typeDeclaration
    : annotationDeclaration
    | classDeclaration
    | interfaceDeclaration
    | NL
    ;
    
modifier
    : classOrInterfaceModifier
    | K_NATIVE
    ;
        
classOrInterfaceModifier
    : annotation 
    | ( K_PUBLIC | K_PACKAGE )
    ;

annotationDeclaration  : 
    K_INTERFACE IDENTIFIER  NL
    ;
    
classDeclaration       : 
    K_INTERFACE IDENTIFIER  NL
    ;
    
interfaceDeclaration   
    : K_PUBLIC? K_INTERFACE IDENTIFIER typeParameters? interfaceBody
    ;
    
interfaceBody
    : '{' interfaceBodyDeclaration* '}'
    ;

interfaceBodyDeclaration
    : modifier* interfaceMemberDeclaration
    | NL
    ;

interfaceMemberDeclaration
    : NL
//    | constDeclaration
//    | interfaceMethodDeclaration
//    | genericInterfaceMethodDeclaration
    ;
            
typeParameters
    : '<' typeParameter (',' typeParameter)* '>'
    ;

typeParameter
    : IDENTIFIER (K_IMPLEMENTS typeBound)?
    ;

typeBound
    : type ('&' type)*
    ;
    
type
    : classOrInterfaceType ('[' ']')*
    ;
    
classOrInterfaceType
    : IDENTIFIER typeArguments? ('.' IDENTIFIER typeArguments? )*
    ;
    
typeArguments
    : '<' typeArgument (',' typeArgument)* '>'
    ;

typeArgument
    : type
    | '?' (K_IMPLEMENTS type)?
    ;
    
qualifiedName
    : IDENTIFIER ('.' IDENTIFIER)*
    ;
        
annotation
    : '@' annotationName ( '(' ')' )?
    ;

annotationName : qualifiedName ;    