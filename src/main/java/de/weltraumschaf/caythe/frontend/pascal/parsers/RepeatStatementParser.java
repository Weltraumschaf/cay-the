package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.CodeFactory;
import de.weltraumschaf.caythe.intermediate.CodeNode;

import de.weltraumschaf.caythe.intermediate.symboltableimpl.Predefined;
import de.weltraumschaf.caythe.intermediate.typeimpl.TypeChecker;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class RepeatStatementParser extends StatementParser {

    public RepeatStatementParser(PascalTopDownParser parent) {
        super(parent);
    }

    @Override
    public CodeNode parse(Token token) throws Exception {
        token = nextToken();  // consume the REPEAT

        // Create the LOOP and TEST nodes.
        CodeNode loopNode = CodeFactory.createCodeNode(LOOP);
        CodeNode testNode = CodeFactory.createCodeNode(TEST);

        // Parse the statement list terminated by the UNTIL token.
        // The LOOP node is the parent of the statement subtrees.
        StatementParser statementParser = new StatementParser(this);
        statementParser.parseList(token, loopNode, UNTIL, MISSING_UNTIL);
        token = currentToken();

        // Parse the expression.
        // The TEST node adopts the expression subtree as its only child.
        ExpressionParser expressionParser = new ExpressionParser(this);
        CodeNode exprNode = expressionParser.parse(token);
        testNode.addChild(exprNode);

        // Type check: The test expesion must be boolean.
        TypeSpecification exprType = exprNode != null
                ? exprNode.getTypeSpecification()
                : Predefined.undefinedType;

        if (!TypeChecker.isBoolean(exprType)) {
            errorHandler.flag(token, INCOMPATIBLE_TYPES, this);
        }

        // The LOOP node adopts the TEST node.
        loopNode.addChild(testNode);

        return loopNode;
    }


}
