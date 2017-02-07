package de.weltraumschaf.caythe.backend.types;

public enum Type {
    NULL,
    ERROR,

    INTEGER,
    FLOAT,
    BOOLEAN,
    STRING,

    RETURN_VALUE,
    BREAK,
    CONTINUE,

    FUNCTION,
    BUILTIN,

    ARRAY,
    HASH;
}
