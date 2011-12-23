package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl;
import de.weltraumschaf.caythe.intermediate.CodeFactory;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import java.util.EnumSet;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.CodeNode;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class IfStatementParser extends StatementParser {

    public IfStatementParser(PascalTopDownParser parent) {
        super(parent);
    }

    // Synchronization set for THEN.
    private static final EnumSet<PascalTokenType> THEN_SET =
        StatementParser.STATEMENT_START_SET.clone();

    static {
        THEN_SET.add(THEN);
        THEN_SET.addAll(StatementParser.STATEMENT_FOLLOW_SET);
    }

    @Override
    public CodeNode parse(Token token) throws Exception {
        token = nextToken();  // consume the IF

        // Create an IF node.
        CodeNode ifNode = CodeFactory.createCodeNode(CodeNodeTypeImpl.IF);

        // Parse the expression.
        // The IF node adopts the expression subtree as its first child.
        ExpressionParser expressionParser = new ExpressionParser(this);
        ifNode.addChild(expressionParser.parse(token));

        // Synchronize at the THEN.
        token = synchronize(THEN_SET);
        if (token.getType() == THEN) {
            token = nextToken();  // consume the THEN
        }
        else {
            errorHandler.flag(token, MISSING_THEN, this);
        }

        // Parse the THEN statement.
        // The IF node adopts the statement subtree as its second child.
        StatementParser statementParser = new StatementParser(this);
        ifNode.addChild(statementParser.parse(token));
        token = currentToken();

        // Look for an ELSE.
        if (token.getType() == ELSE) {
            token = nextToken();  // consume the THEN

            // Parse the ELSE statement.
            // The IF node adopts the statement subtree as its third child.
            ifNode.addChild(statementParser.parse(token));
        }

        return ifNode;
    }


}
