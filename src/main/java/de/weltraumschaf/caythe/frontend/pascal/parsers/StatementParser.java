package de.weltraumschaf.caythe.frontend.pascal.parsers;

import com.sun.tools.internal.ws.wsdl.framework.Defining;
import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.frontend.EofToken;
import de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.CodeFactory;
import de.weltraumschaf.caythe.intermediate.CodeNode;

import de.weltraumschaf.caythe.intermediate.Definition;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl;
import java.util.EnumSet;
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

    protected static final EnumSet<PascalTokenType> STATEMENT_START_SET =
        EnumSet.of(BEGIN, CASE, FOR, PascalTokenType.IF, REPEAT, WHILE, IDENTIFIER, SEMICOLON);

    protected static final EnumSet<PascalTokenType> STATEMENT_FOLLOW_SET =
        EnumSet.of(SEMICOLON, END, ELSE, UNTIL, DOT);

    public CodeNode parse(Token token) throws Exception {
        CodeNode statementNode = null;

        switch ((PascalTokenType) token.getType()) {

            case BEGIN: {
                CompoundStatementParser parser = new CompoundStatementParser(this);
                statementNode = parser.parse(token);
                break;
            }

            // An assignment statement begins with a variable's identifier.
            case IDENTIFIER: {
                String name         = token.getText().toLowerCase();
                SymbolTableEntry id = symbolTableStack.lookup(name);
                Definition idDefn   = id != null ? id.getDefinition() : DefinitionImpl.UNDEFINED;


                // Assignement statement or procedure call.
                switch ((DefinitionImpl) idDefn) {
                    case VARIABLE:
                    case VALUE_PARAM:
                    case VAR_PARAM:
                    case UNDEFINED: {
                        AssignmentStatementParser parser = new AssignmentStatementParser(this);
                        statementNode = parser.parse(token);
                        break;
                    }

                    case FUNCTION: {
                        AssignmentStatementParser parser = new AssignmentStatementParser(this);
                        statementNode = parser.parseFunctionNameAssignement(token);
                        break;
                    }

                    case PROCEDURE: {
                        CallParser parser = new CallParser(this);
                        statementNode = parser.parse(token);
                        break;
                    }

                    default: {
                        errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                        token = nextToken(); // consume identifier
                    }
                }

                break;
            }

            case REPEAT: {
                RepeatStatementParser parser = new RepeatStatementParser(this);
                statementNode = parser.parse(token);
                break;
            }

            case WHILE: {
                WhileStatementParser parser = new WhileStatementParser(this);
                statementNode = parser.parse(token);
                break;
            }

            case FOR: {
                ForStatementParser parser = new ForStatementParser(this);
                statementNode = parser.parse(token);
                break;
            }

            case IF: {
                IfStatementParser parser = new IfStatementParser(this);
                statementNode = parser.parse(token);
                break;
            }

            case CASE: {
                CaseStatementParser parser = new CaseStatementParser(this);
                statementNode = parser.parse(token);
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
        // Sync set for the terminator.
        EnumSet<PascalTokenType> terminatorSet = STATEMENT_START_SET.clone();
        terminatorSet.add(terminator);

        // Loop to parse each statement until the END token
        // or the end of the source file.
        while (!(token instanceof EofToken) && (token.getType() != terminator)) {
            // Parse a statement.  The parent node adopts the statement node.
            CodeNode statementNode = parse(token);
            parentNode.addChild(statementNode);

            token = currentToken();
            TokenType tokenType = token.getType();

            // Look for the semicolon between statements.
            if (tokenType == SEMICOLON) {
                token = nextToken();  // consume the ;
            }
            // If at the start of the next assignment statement, then missing a semicolon.
            else if (STATEMENT_START_SET.contains(tokenType)) {
                errorHandler.flag(token, MISSING_SEMICOLON, this);
            }

            // Sync at start of next statement or at terminator.
            token = synchronize(terminatorSet);
        }

        // Look for the terminator token.
        if (token.getType() == terminator) {
            token = nextToken();  // consume the terminator token
        } else {
            errorHandler.flag(token, errorCode, this);
        }
    }
}
