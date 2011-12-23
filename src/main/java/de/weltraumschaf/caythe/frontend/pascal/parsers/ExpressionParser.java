package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl;
import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.intermediate.CodeNodeType;
import java.util.HashMap;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import java.util.EnumSet;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.frontend.Token;
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
public class ExpressionParser extends StatementParser {

    public ExpressionParser(PascalTopDownParser parent) {
        super(parent);
    }

    @Override
    public CodeNode parse(Token token) throws Exception {
        return parseExpression(token);
    }

    // Set of relational operators.
    private static final EnumSet<PascalTokenType> REL_OPS =
            EnumSet.of(EQUALS, NOT_EQUALS, LESS_THAN, LESS_EQUALS,
            GREATER_THAN, GREATER_EQUALS);
    // Map relational operator tokens to node types.
    private static final HashMap<PascalTokenType, CodeNodeType> REL_OPS_MAP = new HashMap<PascalTokenType, CodeNodeType>();

    static {
        REL_OPS_MAP.put(EQUALS, EQ);
        REL_OPS_MAP.put(NOT_EQUALS, NE);
        REL_OPS_MAP.put(LESS_THAN, LT);
        REL_OPS_MAP.put(LESS_EQUALS, LE);
        REL_OPS_MAP.put(GREATER_THAN, GT);
        REL_OPS_MAP.put(GREATER_EQUALS, GE);
    }

    // Synchronization set for starting an expression.
    static final EnumSet<PascalTokenType> EXPRESSION_START_SET =
        EnumSet.of(PLUS, MINUS, IDENTIFIER, INTEGER, REAL, STRING,
                   PascalTokenType.NOT, LEFT_PAREN);
    /**
     * Parse an expression.
     * @param token the initial token.
     * @return the root of the generated parse subtree.
     * @throws Exception if an error occurred.
     */
    private CodeNode parseExpression(Token token)
            throws Exception {
        // Parse a simple expression and make the root of its tree
        // the root node.
        CodeNode rootNode = parseSimpleExpression(token);

        token = currentToken();
        TokenType tokenType = token.getType();

        // Look for a relational operator.
        if (REL_OPS.contains(tokenType)) {

            // Create a new operator node and adopt the current tree
            // as its first child.
            CodeNodeType nodeType = REL_OPS_MAP.get(tokenType);
            CodeNode opNode = CodeFactory.createCodeNode(nodeType);
            opNode.addChild(rootNode);

            token = nextToken();  // consume the operator

            // Parse the second simple expression.  The operator node adopts
            // the simple expression's tree as its second child.
            opNode.addChild(parseSimpleExpression(token));

            // The operator node becomes the new root node.
            rootNode = opNode;
        }

        return rootNode;
    }
    // Set of additive operators.
    private static final EnumSet<PascalTokenType> ADD_OPS = EnumSet.of(PLUS, MINUS, PascalTokenType.OR);
    // Map additive operator tokens to node types.
    private static final HashMap<PascalTokenType, CodeNodeTypeImpl> ADD_OPS_MAP = new HashMap<PascalTokenType, CodeNodeTypeImpl>();

    static {
        ADD_OPS_MAP.put(PLUS, ADD);
        ADD_OPS_MAP.put(MINUS, SUBTRACT);
        ADD_OPS_MAP.put(PascalTokenType.OR, CodeNodeTypeImpl.OR);
    }

    /**
     * Parse a simple expression.
     * @param token the initial token.
     * @return the root of the generated parse subtree.
     * @throws Exception if an error occurred.
     */
    private CodeNode parseSimpleExpression(Token token)
            throws Exception {
        TokenType signType = null;  // type of leading sign (if any)

        // Look for a leading + or - sign.
        TokenType tokenType = token.getType();
        if (( tokenType == PLUS ) || ( tokenType == MINUS )) {
            signType = tokenType;
            token = nextToken();  // consume the + or -
        }

        // Parse a term and make the root of its tree the root node.
        CodeNode rootNode = parseTerm(token);

        // Was there a leading - sign?
        if (signType == MINUS) {

            // Create a NEGATE node and adopt the current tree
            // as its child. The NEGATE node becomes the new root node.
            CodeNode negateNode = CodeFactory.createCodeNode(NEGATE);
            negateNode.addChild(rootNode);
            rootNode = negateNode;
        }

        token = currentToken();
        tokenType = token.getType();

        // Loop over additive operators.
        while (ADD_OPS.contains(tokenType)) {

            // Create a new operator node and adopt the current tree
            // as its first child.
            CodeNodeType nodeType = ADD_OPS_MAP.get(tokenType);
            CodeNode opNode = CodeFactory.createCodeNode(nodeType);
            opNode.addChild(rootNode);

            token = nextToken();  // consume the operator

            // Parse another term.  The operator node adopts
            // the term's tree as its second child.
            opNode.addChild(parseTerm(token));

            // The operator node becomes the new root node.
            rootNode = opNode;

            token = currentToken();
            tokenType = token.getType();
        }

        return rootNode;
    }
    // Set of multiplicative operators.
    private static final EnumSet<PascalTokenType> MULT_OPS =
            EnumSet.of(STAR, SLASH, DIV, PascalTokenType.MOD, PascalTokenType.AND);
    // Map multiplicative operator tokens to node types.
    private static final HashMap<PascalTokenType, CodeNodeType> MULT_OPS_MAP = new HashMap<PascalTokenType, CodeNodeType>();

    static {
        MULT_OPS_MAP.put(STAR, MULTIPLY);
        MULT_OPS_MAP.put(SLASH, FLOAT_DIVIDE);
        MULT_OPS_MAP.put(DIV, INTEGER_DIVIDE);
        MULT_OPS_MAP.put(PascalTokenType.MOD, CodeNodeTypeImpl.MOD);
        MULT_OPS_MAP.put(PascalTokenType.AND, CodeNodeTypeImpl.AND);
    };

    /**
     * Parse a term.
     * @param token the initial token.
     * @return the root of the generated parse subtree.
     * @throws Exception if an error occurred.
     */
    private CodeNode parseTerm(Token token)
            throws Exception {
        // Parse a factor and make its node the root node.
        CodeNode rootNode = parseFactor(token);

        token = currentToken();
        TokenType tokenType = token.getType();

        // Loop over multiplicative operators.
        while (MULT_OPS.contains(tokenType)) {

            // Create a new operator node and adopt the current tree
            // as its first child.
            CodeNodeType nodeType = MULT_OPS_MAP.get(tokenType);
            CodeNode opNode = CodeFactory.createCodeNode(nodeType);
            opNode.addChild(rootNode);

            token = nextToken();  // consume the operator

            // Parse another factor.  The operator node adopts
            // the term's tree as its second child.
            opNode.addChild(parseFactor(token));

            // The operator node becomes the new root node.
            rootNode = opNode;

            token = currentToken();
            tokenType = token.getType();
        }

        return rootNode;
    }

    /**
     * Parse a factor.
     * @param token the initial token.
     * @return the root of the generated parse subtree.
     * @throws Exception if an error occurred.
     */
    private CodeNode parseFactor(Token token)
            throws Exception {
        TokenType tokenType = token.getType();
        CodeNode rootNode = null;

        switch ((PascalTokenType) tokenType) {

            case IDENTIFIER: {
                // Look up the identifier in the symbol table stack.
                // Flag the identifier as undefined if it's not found.
                String name = token.getText().toLowerCase();
                SymbolTableEntry id = symbolTableStack.lookup(name);
                if (id == null) {
                    errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
                    id = symbolTableStack.enterLocal(name);
                }

                rootNode = CodeFactory.createCodeNode(VARIABLE);
                rootNode.setAttribute(ID, id);
                id.appendLineNumber(token.getLineNumber());

                token = nextToken();  // consume the identifier
                break;
            }

            case INTEGER: {
                // Create an INTEGER_CONSTANT node as the root node.
                rootNode = CodeFactory.createCodeNode(INTEGER_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());

                token = nextToken();  // consume the number
                break;
            }

            case REAL: {
                // Create an REAL_CONSTANT node as the root node.
                rootNode = CodeFactory.createCodeNode(REAL_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());

                token = nextToken();  // consume the number
                break;
            }

            case STRING: {
                String value = (String) token.getValue();

                // Create a STRING_CONSTANT node as the root node.
                rootNode = CodeFactory.createCodeNode(STRING_CONSTANT);
                rootNode.setAttribute(VALUE, value);

                token = nextToken();  // consume the string
                break;
            }

            case NOT: {
                token = nextToken();  // consume the NOT

                // Create a NOT node as the root node.
                rootNode = CodeFactory.createCodeNode(CodeNodeTypeImpl.NOT);

                // Parse the factor.  The NOT node adopts the
                // factor node as its child.
                rootNode.addChild(parseFactor(token));

                break;
            }

            case LEFT_PAREN: {
                token = nextToken();      // consume the (

                // Parse an expression and make its node the root node.
                rootNode = parseExpression(token);

                // Look for the matching ) token.
                token = currentToken();
                if (token.getType() == RIGHT_PAREN) {
                    token = nextToken();  // consume the )
                } else {
                    errorHandler.flag(token, MISSING_RIGHT_PAREN, this);
                }

                break;
            }

            default: {
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                break;
            }
        }

        return rootNode;
    }
}
