# Cay-The

With  this  project  I  play  around   with  [ANTLR4][antlr]  to  create  a  own
programinglanguage. First  for learning purposes,  but second also to  build the
next worlds best programming language ;)

## Research and Specifiaction

Why  I'm doing  this?  Since decades  I'm blaming  other  languages for  errors,
faults and shortcommings.  Something my friends always here from  me: If I would
do a programming language I would never do it like this, but that yada yada.

Over time  I looked  into lot of  languages and  read a lot  of stuff  about the
topic. A dense  concetrate about that you can find  in the [paper][perfetc-lang]
which is part  of this repo. And if I  would do my own language then  it will be
like in this [specification][langspec].

## Status

This  project  is under  heavy  construction.  A lot  of  things  may change  or
disappear.

## Distribution

There  is   a  [distribution][dist]   file  with   the  latest   executable  and
documentation in a zip file. To execute Cay-The on the command line simply run:

    $> ./bin/caythe -h

### Prerequisite

You need a [Java 8][java8] VM and a Bash compatible shell to run the CLI.

## Helpfull Stuff and Links

- [ANTLR Cheat Sheet](antlr_cheat_sheet.md)

## Design Considerations

Some thoughts or questions about basic language stuff.

### The Difference Between Expressions and Statements

In Python it is defined as:

> Expressions only contain identifiers, literals and operators, where operators include arithmetic and boolean operators, the function call operator () the subscription operator [] and similar, and can be reduced to some kind of "value", which can be any Python object. ([source](https://stackoverflow.com/questions/4728073/what-is-the-difference-between-an-expression-and-a-statement-in-python))

Conclusions:

- Should add a method invocation operator `()` such as the existing subscript operator `[]`.
- Define the difference between expressions and statements. The Python way is nearly the same.
 
[antlr]:        http://www.antlr.org/
[dist]:         https://ci.weltraumschaf.de/job/cay-the/lastSuccessfulBuild/artifact/module-distribution/target/distribution-1.0.0-SNAPSHOT.zip
[java8]:        https://java.com/en/download/
[langspec]:     https://ci.weltraumschaf.de/job/cay-the/lastSuccessfulBuild/artifact/module-documentation/target/latex/output/language_specification.pdf
[perfetc-lang]: https://ci.weltraumschaf.de/job/cay-the/lastSuccessfulBuild/artifact/module-documentation/target/latex/output/a_perfect_programming_language.pdf