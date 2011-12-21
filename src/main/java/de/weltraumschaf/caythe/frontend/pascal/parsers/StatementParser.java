package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.frontend.EofToken;
import de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.CodeFactory;
import de.weltraumschaf.caythe.intermediate.CodeNode;

import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class StatementParser extends PascalTopDownParser {

    public StatementParser(PascalTopDownParser parent) {
        super(parent);
    }

    public CodeNode parse(Token token) throws Exception {
        CodeNode statementNode = null;

        switch ((PascalTokenType) token.getType()) {

            case BEGIN: {
                CompoundStatementParser compoundParser =
                        new CompoundStatementParser(this);
                statementNode = compoundParser.parse(token);
                break;
            }

            // An assignment statement begins with a variable's identifier.
            case IDENTIFIER: {
                AssignmentStatementParser assignmentParser =
                        new AssignmentStatementParser(this);
                statementNode = assignmentParser.parse(token);
                break;
            }

            default: {
                statementNode = CodeFactory.createCodeNode(NO_OP);
                break;
            }
        }

        // Set the current line number as an attribute.
        setLineNumber(statementNode, token);

        return statementNode;
    }

    protected void setLineNumber(CodeNode node, Token token) {
        if (node != null) {
            node.setAttribute(LINE, token.getLineNumber());
        }
    }

    protected void parseList(Token token, CodeNode parentNode, PascalTokenType terminator, PascalErrorCode errorCode) throws Exception {
        // Loop to parse each statement until the END token
        // or the end of the source file.
        while (!( token instanceof EofToken )
                && ( token.getType() != terminator )) {

            // Parse a statement.  The parent node adopts the statement node.
            CodeNode statementNode = parse(token);
            parentNode.addChild(statementNode);

            token = currentToken();
            TokenType tokenType = token.getType();

            // Look for the semicolon between statements.
            if (tokenType == SEMICOLON) {
                token = nextToken();  // consume the ;
            } // If at the start of the next assignment statement,
            // then missing a semicolon.
            else if (tokenType == IDENTIFIER) {
                errorHandler.flag(token, MISSING_SEMICOLON, this);
            } // Unexpected token.
            else if (tokenType != terminator) {
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                token = nextToken();  // consume the unexpected token
            }
        }

        // Look for the terminator token.
        if (token.getType() == terminator) {
            token = nextToken();  // consume the terminator token
        } else {
            errorHandler.flag(token, errorCode, this);
        }
    }
}
