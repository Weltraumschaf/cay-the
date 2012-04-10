package de.weltraumschaf.caythe.frontend.pascal;

import de.weltraumschaf.caythe.frontend.ErrorCode;

/**
 * Error codes for Pascal.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum PascalErrorCode implements ErrorCode {
    ALREADY_FORWARDED("Already specified in FORWARD"),
    CASE_CONSTANT_REUSED("CASE constant reused"),
    IDENTIFIER_REDEFINED("Redefined identifier"),
    IDENTIFIER_UNDEFINED("Undefined identifier"),
    INCOMPATIBLE_ASSIGNMENT("Incompatible assignment"),
    INCOMPATIBLE_TYPES("Incompatible types"),
    INVALID_ASSIGNMENT("Invalid assignment statement"),
    INVALID_CHARACTER("Invalid character"),
    INVALID_CONSTANT("Invalid constant"),
    INVALID_EXPONENT("Invalid exponent"),
    INVALID_EXPRESSION("Invalid expression"),
    INVALID_FIELD("Invalid field"),
    INVALID_FRACTION("Invalid fraction"),
    INVALID_IDENTIFIER_USAGE("Invalid identifier usage"),
    INVALID_INDEX_TYPE("Invalid index type"),
    INVALID_NUMBER("Invalid number"),
    INVALID_STATEMENT("Invalid statement"),
    INVALID_SUBRANGE_TYPE("Invalid subrange type"),
    INVALID_TARGET("Invalid assignment target"),
    INVALID_TYPE("Invalid type"),
    INVALID_VAR_PARAM("Invalid VAR parameter"),
    MIN_GT_MAX("Min limit greater than max limit"),
    MISSING_BEGIN("Missing BEGIN"),
    MISSING_COLON("Missing :"),
    MISSING_COLON_EQUALS("Missing :="),
    MISSING_COMMA("Missing ,"),
    MISSING_CONSTANT("Missing constant"),
    MISSING_DO("Missing DO"),
    MISSING_DOT_DOT("Missing .."),
    MISSING_END("Missing END"),
    MISSING_EQUALS("Missing ="),
    MISSING_FOR_CONTROL("Invalid FOR control variable"),
    MISSING_IDENTIFIER("Missing identifier"),
    MISSING_LEFT_BRACKET("Missing ["),
    MISSING_OF("Missing OF"),
    MISSING_PERIOD("Missing ."),
    MISSING_PROGRAM("Missing PROGRAM"),
    MISSING_RIGHT_BRACKET("Missing ]"),
    MISSING_RIGHT_PAREN("Missing )"),
    MISSING_SEMICOLON("Missing ;"),
    MISSING_THEN("Missing THEN"),
    MISSING_TO_DOWNTO("Missing TO or DOWNTO"),
    MISSING_UNTIL("Missing UNTIL"),
    MISSING_VARIABLE("Missing variable"),
    NOT_CONSTANT_IDENTIFIER("Not a constant identifier"),
    NOT_RECORD_VARIABLE("Not a record variable"),
    NOT_TYPE_IDENTIFIER("Not a type identifier"),
    RANGE_INTEGER("Integer literal out of range"),
    RANGE_REAL("Real literal out of range"),
    STACK_OVERFLOW("Stack overflow"),
    TOO_MANY_LEVELS("Nesting level too deep"),
    TOO_MANY_SUBSCRIPTS("Too many subscripts"),
    UNEXPECTED_EOF("Unexpected end of file"),
    UNEXPECTED_TOKEN("Unexpected token"),
    UNIMPLEMENTED("Unimplemented feature"),
    UNRECOGNIZABLE("Unrecognizable input"),
    WRONG_NUMBER_OF_PARAMS("Wrong number of actual parameters"),

    // Fatal errors.
    IO_ERROR(-101, "Object I/O error"),
    TOO_MANY_ERRORS(-102, "Too many syntax errors"),
    EMPTY_INPUT_ERROR(-103, "Empty source file given");

    /**
     * The status.
     *
     * Negative numbers for fatal errors.
     */
    private int status;
    /**
     * Error message.
     */
    private String message;

    /**
     * Initializes status with 0.
     *
     * @param message
     */
    PascalErrorCode(String message) {
	this(0, message);
    }

    /**
     * Designated constructor.
     *
     * @param status
     * @param message
     */
    PascalErrorCode(int status, String message) {
	this.status  = status;
	this.message = message;
    }

    @Override
    public int getStatus() {
	return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(message);

        if (status != 0) {
            sb.append(" (status: ").append(status).append(")");
        }

        sb.append('!');

	return sb.toString();
    }

}
