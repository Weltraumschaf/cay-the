package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.intermediate.typeimpl.TypeChecker;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.Predefined;
import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.intermediate.CodeFactory;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import java.util.EnumSet;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.*;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl.ENUMERATION;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class ForStatementParser extends StatementParser {

    public ForStatementParser(PascalTopDownParser parent) {
        super(parent);
    }
    // Synchronization set for TO or DOWNTO.
    private static final EnumSet<PascalTokenType> TO_DOWNTO_SET =
            ExpressionParser.EXPRESSION_START_SET.clone();

    static {
        TO_DOWNTO_SET.add(TO);
        TO_DOWNTO_SET.add(DOWNTO);
        TO_DOWNTO_SET.addAll(StatementParser.STATEMENT_FOLLOW_SET);
    }
    // Synchronization set for DO.
    private static final EnumSet<PascalTokenType> DO_SET =
            StatementParser.STATEMENT_START_SET.clone();

    static {
        DO_SET.add(DO);
        DO_SET.addAll(StatementParser.STATEMENT_FOLLOW_SET);
    }

    @Override
    public CodeNode parse(Token token) throws Exception {
        token = nextToken();  // consume the FOR
        Token targetToken = token;

        // Create the loop COMPOUND, LOOP, and TEST nodes.
        CodeNode compoundNode = CodeFactory.createCodeNode(COMPOUND);
        CodeNode loopNode     = CodeFactory.createCodeNode(LOOP);
        CodeNode testNode     = CodeFactory.createCodeNode(TEST);

        // Parse the embedded initial assignment.
        AssignmentStatementParser assignmentParser =
                new AssignmentStatementParser(this);
        CodeNode initAssignNode = assignmentParser.parse(token);
        TypeSpecification controlType = initAssignNode != null
                ? initAssignNode.getTypeSpecification()
                : Predefined.undefinedType;

        // Set the current line number attribute.
        setLineNumber(initAssignNode, targetToken);

        // Type check: The control variable's type must be integer
        //             or enumeration.
        if (!TypeChecker.isInteger(controlType)
                && ( controlType.getForm() != ENUMERATION )) {
            errorHandler.flag(token, INCOMPATIBLE_TYPES, this);
        }

        // The COMPOUND node adopts the initial ASSIGN and the LOOP nodes
        // as its first and second children.
        compoundNode.addChild(initAssignNode);
        compoundNode.addChild(loopNode);

        // Synchronize at the TO or DOWNTO.
        token = synchronize(TO_DOWNTO_SET);
        TokenType direction = token.getType();

        // Look for the TO or DOWNTO.
        if (( direction == TO ) || ( direction == DOWNTO )) {
            token = nextToken();  // consume the TO or DOWNTO
        } else {
            direction = TO;
            errorHandler.flag(token, MISSING_TO_DOWNTO, this);
        }

        // Create a relational operator node: GT for TO, or LT for DOWNTO.
        CodeNode relOpNode = CodeFactory.createCodeNode(direction == TO ? GT : LT);
        relOpNode.setTypeSpecification(Predefined.booleanType);

        // Copy the control VARIABLE node. The relational operator
        // node adopts the copied VARIABLE node as its first child.
        CodeNode controlVarNode = initAssignNode.getChildren().get(0);
        relOpNode.addChild(controlVarNode.copy());

        // Parse the termination expression. The relational operator node
        // adopts the expression as its second child.
        ExpressionParser expressionParser = new ExpressionParser(this);
        CodeNode exprNode = expressionParser.parse(token);
        relOpNode.addChild(exprNode);

        // Type check: The termination expression type must be assignment
        //             compatible with the control variable's type.
        TypeSpecification exprType = exprNode != null
                ? exprNode.getTypeSpecification()
                : Predefined.undefinedType;

        if (!TypeChecker.areAssignmentCompatible(controlType, exprType)) {
            errorHandler.flag(token, INCOMPATIBLE_TYPES, this);
        }

        // The TEST node adopts the relational operator node as its only child.
        // The LOOP node adopts the TEST node as its first child.
        testNode.addChild(relOpNode);
        loopNode.addChild(testNode);

        // Synchronize at the DO.
        token = synchronize(DO_SET);
        if (token.getType() == DO) {
            token = nextToken();  // consume the DO
        } else {
            errorHandler.flag(token, MISSING_DO, this);
        }

        // Parse the nested statement. The LOOP node adopts the statement
        // node as its second child.
        StatementParser statementParser = new StatementParser(this);
        loopNode.addChild(statementParser.parse(token));

        // Create an assignment with a copy of the control variable
        // to advance the value of the variable.
        CodeNode nextAssignNode = CodeFactory.createCodeNode(ASSIGN);
        nextAssignNode.setTypeSpecification(controlType);
        nextAssignNode.addChild(controlVarNode.copy());

        // Create the arithmetic operator node:
        // ADD for TO, or SUBTRACT for DOWNTO.
        CodeNode arithOpNode = CodeFactory.createCodeNode(direction == TO ? ADD : SUBTRACT);
        arithOpNode.setTypeSpecification(Predefined.integerType);

        // The next operator node adopts a copy of the loop variable as its
        // first child and the value 1 as its second child.
        arithOpNode.addChild(controlVarNode.copy());
        CodeNode oneNode = CodeFactory.createCodeNode(INTEGER_CONSTANT);
        oneNode.setAttribute(VALUE, 1);
        oneNode.setTypeSpecification(Predefined.integerType);
        arithOpNode.addChild(oneNode);

        // The next ASSIGN node adopts the arithmetic operator node as its
        // second child. The loop node adopts the next ASSIGN node as its
        // third child.
        nextAssignNode.addChild(arithOpNode);
        loopNode.addChild(nextAssignNode);

        // Set the current line number attribute.
        setLineNumber(nextAssignNode, targetToken);

        return compoundNode;
    }
}
