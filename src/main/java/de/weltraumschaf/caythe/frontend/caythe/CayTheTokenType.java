package de.weltraumschaf.caythe.frontend.caythe;

import de.weltraumschaf.caythe.frontend.TokenType;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public enum CayTheTokenType implements TokenType {
    // Reserved words.
    CONST, VAR, WHILE, FOR, IN, FUNCTION, IF, ELSE, DO,

    // Special symbols.
    PLUS("+"), MINUS("-"), STAR("*"), SLASH("/"), ASSIGN("="),
    COMMA(","), SEMICOLON(";"), QUOTE("'"), DOUBLE_QUOTE("\""),
    EQUALS("=="), NOT_EQUALS("!="), LESS_THAN("<"), LESS_EQUALS("<="),
    GREATER_EQUALS(">="), GREATER_THAN(">"), LEFT_PAREN("("), RIGHT_PAREN(")"),
    LEFT_BRACKET("["), RIGHT_BRACKET("]"), LEFT_BRACE("{"), RIGHT_BRACE("}"),

    IDENTIFIER, INTEGER, REAL, STRING, CHARACTER,

    ERROR, END_OF_FILE;

    private static final int FIRST_RESERVED_INDEX = CONST.ordinal();
    private static final int LAST_RESERVED_INDEX  = DO.ordinal();

    private static final int FIRST_SPECIAL_INDEX = PLUS.ordinal();
    private static final int LAST_SPECIAL_INDEX  = RIGHT_BRACE.ordinal();

    private String text;

    CayTheTokenType() {
        this.text = this.toString().toLowerCase();
    }

    CayTheTokenType(String text) {
        this.text = text;
    }

    public String getText() {
	return text;
    }

    // Set of lower-cased Pascal reserved word text strings.
    public static final HashSet<String> RESERVED_WORDS = new HashSet<String>();
    static {
        CayTheTokenType values[] = CayTheTokenType.values();
        for (int i = FIRST_RESERVED_INDEX; i <= LAST_RESERVED_INDEX; ++i) {
            RESERVED_WORDS.add(values[i].getText().toLowerCase());
        }
    }

    // Hash table of Pascal special symbols. Each special symbol's text
    // is the key to its Pascal token type.
    public static final HashMap<String, CayTheTokenType> SPECIAL_SYMBOLS =
	new HashMap<String, CayTheTokenType>();
    static {
        CayTheTokenType values[] = CayTheTokenType.values();
        for (int i = FIRST_SPECIAL_INDEX; i <= LAST_SPECIAL_INDEX; ++i) {
            SPECIAL_SYMBOLS.put(values[i].getText(), values[i]);
        }
    }
}
