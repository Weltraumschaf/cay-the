package de.weltraumschaf.caythe.message;

/**
 * Enumerates all available types of messages.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum MessageType {
    SOURCE_LINE,
    SYNTAX_ERROR,
    PARSER_SUMMARY,
    INTERPRETER_SUMMARY,
    COMPILER_SUMMARY,
    MISCELLANEOUS,
    TOKEN,
    ASSIGN,
    FETCH,
    BREAKPOINT,
    RUNTIME_ERROR,
    CALL,
    RETURN
}
