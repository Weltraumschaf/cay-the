# ANTLR Grammar Cheat Sheet

Adopted form [Atlassian](https://theantlrguy.atlassian.net/wiki/display/ANTLR3/ANTLR+Cheat+Sheet).

## Example Grammar

    grammar Name; // File Name.g4
    
    // Parser production rules:
    def : modifier+ 'int' ID '=' INT ';'
        | modifier+ 'int' ID ';'
        ;
    modifier : 'public' | 'static' ;
    
    // Lexer rules:
    INT : '0'..'9'+ ;
    ID  : 'a'..'z'+ ;
    WS  : (' '|'\r'|'\n')+ {$channel = HIDDEN;} ;

## ANTLR Symbols & Keywords

| Symbol            | Description   |
|:-----------------:|---------------|
| $                 | Attribute     |
| @                 | Action        |
| ::                | action or dynamically-scoped attribute scope specifier|
| :                 | rule definition|
| ;                 | end rule      |
| \|                | alternative   |
| 's'               | char or string literal|
| .                 | wildcard      |
| =                 | label assignment|
| +=                | list label assignment|
| [..]              | argument or return value spec|
| {...}             | action        |
| {{...}}           | forced action; execute even while backtracking
| (...)             | subrule       |
| +                 | 1 or more     |
| *                 | 0 or more     |
| ?                 | optional or semantic predicate|
| ~                 | match not     |
| !                 | don't include in AST|
| ^                 | make AST root node|
| =>                | always execute predicate|
| ->                | rewrite rule |
| &lt;token options&gt; | token option spec like ID&lt;node=VarNode&gt; |
| ^(...)            | tree grammar or rewrite element |
| // ...            | single-line comment |
| /* ... */         | multi-line comment |

| Keyword   | Description   |
|:---------:|---------------|
| scope     | Dynamically-scoped attribute|
| fragment  | lexer rule is a helper rule, not real token for parser|
| lexer     | grammar type  |
| tree      | grammar type  |
| parser    | grammar type  |
| grammar   | grammar header|
| returns   | rule return value(s)|
| throws    | rule throws exception(s)|
| catch     | catch rule exceptions|
| finally   | do this no matter what|
| options   | grammar or rule options|
| tokens    | can add tokens with this; usually imaginary tokens|
| import    | import grammar(s)|