# Cay-The

An example framework for compilers and interpreters in Java.

## Introduction

This is a learning code repo. The whole code I've copied or written
while reading a book about compilers and interpreters. More
information about that in my [blog](https://blog.weltraumschaf.de/2011/12/28/writing-compilers-and-interpreters-a-software-engineering-approach/).

## Installation

Checkout this repo and run <kbd>mvn package</kbd> inside the repo dir.
After that you can invoke the tool with <kbd>./caythe -h</kbd>.

## Execute a Pascal program

You can compile or execute only Pascal programs except you implement your
own parser classes.

To run a program and get its intermediate code tree and cross reference table type:

    ./caythe --lang pascal --mode execute pascal/newton.pas -ix

It will print out the intermediate code:

    <COMPOUND line="10">
        <LOOP line="11">
            <CALL id="writeln" level="0" line="12" />
            <CALL id="write" level="0" line="13">
                <PARAMETERS>
                    <WRITE_PARM>
                        <STRING_CONSTANT value="Enter new number (0 to quit): "
                        type_id="$anon_49ae9f49" />
                    </WRITE_PARM>
                </PARAMETERS>
            </CALL>
            <CALL id="read" level="0" line="14">
                <PARAMETERS>
                    <VARIABLE id="number" level="1" type_id="integer" />
                </PARAMETERS>
            </CALL>
            <IF line="16">
                <EQ type_id="boolean">
                    <VARIABLE id="number" level="1" type_id="integer" />
                    <INTEGER_CONSTANT value="0" type_id="integer" />
                </EQ>
                <COMPOUND line="16">
                    <CALL id="writeln" level="0" line="17">
                        <PARAMETERS>
                            <WRITE_PARM>
                                <VARIABLE id="number" level="1" type_id="integer" />
                                <INTEGER_CONSTANT value="12" type_id="integer" />
                            </WRITE_PARM>
                            <WRITE_PARM>
                                <REAL_CONSTANT value="0.0" type_id="real" />
                                <INTEGER_CONSTANT value="12" type_id="integer" />
                                <INTEGER_CONSTANT value="6" type_id="integer" />
                            </WRITE_PARM>
                        </PARAMETERS>
                    </CALL>
                </COMPOUND>
                <IF line="19">
                    <LT type_id="boolean">
                        <VARIABLE id="number" level="1" type_id="integer" />
                        <INTEGER_CONSTANT value="0" type_id="integer" />
                    </LT>
                    <COMPOUND line="19">
                        <CALL id="writeln" level="0" line="20">
                            <PARAMETERS>
                                <WRITE_PARM>
                                    <STRING_CONSTANT value="*** ERROR:  number < 0"
                                    type_id="$anon_49ae9f59" />
                                </WRITE_PARM>
                            </PARAMETERS>
                        </CALL>
                    </COMPOUND>
                    <COMPOUND line="22">
                        <ASSIGN line="23" type_id="real">
                            <VARIABLE id="sqroot" level="1" type_id="real" />
                            <CALL id="sqrt" level="0" type_id="real">
                                <PARAMETERS>
                                    <VARIABLE id="number" level="1"
                                    type_id="integer" />
                                </PARAMETERS>
                            </CALL>
                        </ASSIGN>
                        <CALL id="writeln" level="0" line="24">
                            <PARAMETERS>
                                <WRITE_PARM>
                                    <VARIABLE id="number" level="1"
                                    type_id="integer" />
                                    <INTEGER_CONSTANT value="12" type_id="integer"
                                    />
                                </WRITE_PARM>
                                <WRITE_PARM>
                                    <VARIABLE id="sqroot" level="1" type_id="real"
                                    />
                                    <INTEGER_CONSTANT value="12" type_id="integer"
                                    />
                                    <INTEGER_CONSTANT value="6" type_id="integer" />
                                </WRITE_PARM>
                            </PARAMETERS>
                        </CALL>
                        <CALL id="writeln" level="0" line="25" />
                        <ASSIGN line="27" type_id="real">
                            <VARIABLE id="root" level="1" type_id="real" />
                            <INTEGER_CONSTANT value="1" type_id="integer" />
                        </ASSIGN>
                        <LOOP line="28">
                            <ASSIGN line="29" type_id="real">
                                <VARIABLE id="root" level="1" type_id="real" />
                                <FLOAT_DIVIDE type_id="real">
                                    <ADD type_id="real">
                                        <FLOAT_DIVIDE type_id="real">
                                            <VARIABLE id="number" level="1"
                                            type_id="integer" />
                                            <VARIABLE id="root" level="1"
                                            type_id="real" />
                                        </FLOAT_DIVIDE>
                                        <VARIABLE id="root" level="1" type_id="real"
                                        />
                                    </ADD>
                                    <INTEGER_CONSTANT value="2" type_id="integer" />
                                </FLOAT_DIVIDE>
                            </ASSIGN>
                            <CALL id="writeln" level="0" line="30">
                                <PARAMETERS>
                                    <WRITE_PARM>
                                        <VARIABLE id="root" level="1" type_id="real"
                                        />
                                        <INTEGER_CONSTANT value="24"
                                        type_id="integer" />
                                        <INTEGER_CONSTANT value="6"
                                        type_id="integer" />
                                    </WRITE_PARM>
                                    <WRITE_PARM>
                                        <FLOAT_DIVIDE type_id="real">
                                            <MULTIPLY type_id="real">
                                                <INTEGER_CONSTANT value="100"
                                                type_id="integer" />
                                                <CALL id="abs" level="0"
                                                type_id="real">
                                                    <PARAMETERS>
                                                        <SUBTRACT type_id="real">
                                                            <VARIABLE id="root"
                                                                level="1"
                                                            type_id="real" />
                                                            <VARIABLE id="sqroot"
                                                                level="1"
                                                            type_id="real" />
                                                        </SUBTRACT>
                                                    </PARAMETERS>
                                                </CALL>
                                            </MULTIPLY>
                                            <VARIABLE id="sqroot" level="1"
                                            type_id="real" />
                                        </FLOAT_DIVIDE>
                                        <INTEGER_CONSTANT value="12"
                                        type_id="integer" />
                                        <INTEGER_CONSTANT value="2"
                                        type_id="integer" />
                                    </WRITE_PARM>
                                    <WRITE_PARM>
                                        <STRING_CONSTANT value="%" type_id="char" />
                                    </WRITE_PARM>
                                </PARAMETERS>
                            </CALL>
                            <TEST>
                                <LT type_id="boolean">
                                    <CALL id="abs" level="0" type_id="real">
                                        <PARAMETERS>
                                            <SUBTRACT type_id="real">
                                                <FLOAT_DIVIDE type_id="real">
                                                    <VARIABLE id="number" level="1"
                                                    type_id="integer" />
                                                    <CALL id="sqr" level="0"
                                                    type_id="real">
                                                        <PARAMETERS>
                                                            <VARIABLE id="root"
                                                                level="1"
                                                            type_id="real" />
                                                        </PARAMETERS>
                                                    </CALL>
                                                </FLOAT_DIVIDE>
                                                <INTEGER_CONSTANT value="1"
                                                type_id="integer" />
                                            </SUBTRACT>
                                        </PARAMETERS>
                                    </CALL>
                                    <REAL_CONSTANT value="1.0E-6" type_id="real" />
                                </LT>
                            </TEST>
                        </LOOP>
                    </COMPOUND>
                </IF>
            </IF>
            <TEST>
                <EQ type_id="boolean">
                    <VARIABLE id="number" level="1" type_id="integer" />
                    <INTEGER_CONSTANT value="0" type_id="integer" />
                </EQ>
            </TEST>
        </LOOP>
    </COMPOUND>

and the cross reference table:

    Identifier       Line numbers    Type specification
    ----------       ------------    ------------------
    epsilon          004 033
                                     Defined as: constant
                                     Scope nesting level: 1
                                     Type form = scalar, Type id = real
                                     Value = 1.0E-6
    number           007 014 016 017 019 023 024 029 033 035
                                     Defined as: variable
                                     Scope nesting level: 1
                                     Type form = scalar, Type id = integer
    root             008 027 029 029 029 030 031 033
                                     Defined as: variable
                                     Scope nesting level: 1
                                     Type form = scalar, Type id = real
    sqroot           008 023 024 031 031
                                     Defined as: variable
                                     Scope nesting level: 1
                                     Type form = scalar, Type id = real

## Links

- [Pascal](http://en.wikibooks.org/wiki/Pascal_Programming)
- [JOpt Simple](http://pholser.github.com/jopt-simple/index.html)
- [Apache Commons CLI](http://commons.apache.org/cli/introduction.html)