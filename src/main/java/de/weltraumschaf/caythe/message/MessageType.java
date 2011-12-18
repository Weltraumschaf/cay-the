package de.weltraumschaf.caythe.message;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public enum MessageType {
    SOURCE_LINE, SYNTAX_ERROR,
    PARSER_SUMMARY, INTERPRETER_SUMMARY, COMPILER_SUMMARY,
    MISCELLANEOUS, TOKEN,
    ASSIGN, FETCH, BREAKPOINT, RUNTIME_ERROR,
    CALL, RETURN
}
