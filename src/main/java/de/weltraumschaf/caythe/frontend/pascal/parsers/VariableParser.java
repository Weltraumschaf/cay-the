package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import de.weltraumschaf.caythe.intermediate.CodeFactory;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.Definition;
import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.TypeForm;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.Predefined;
import de.weltraumschaf.caythe.intermediate.typeimpl.TypeChecker;
import java.util.EnumSet;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.*;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class VariableParser extends StatementParser {

    public VariableParser(PascalTopDownParser parent) {
        super(parent);
    }
    // Synchronization set to start a subscript or a field.
    private static final EnumSet<PascalTokenType> SUBSCRIPT_FIELD_START_SET =
            EnumSet.of(LEFT_BRACKET, DOT);

    @Override
    public CodeNode parse(Token token)
            throws Exception {
        // Look up the identifier in the symbol table stack.
        String name = token.getText().toLowerCase();
        SymbolTableEntry variableId = symbolTableStack.lookup(name);

        // If not found, flag the error and enter the identifier
        // as an undefined identifier with an undefined type.
        if (variableId == null) {
            errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
            variableId = symbolTableStack.enterLocal(name);
            variableId.setDefinition(UNDEFINED);
            variableId.setTypeSpecification(Predefined.undefinedType);
        }

        return parse(token, variableId);
    }

    /**
     * Parse a variable.
     * @param token the initial token.
     * @param variableId the symbol table entry of the variable identifier.
     * @return the root node of the generated parse tree.
     * @throws Exception if an error occurred.
     */
    public CodeNode parse(Token token, SymbolTableEntry variableId) throws Exception {
        // Check how the variable is defined.
        Definition defnCode = variableId.getDefinition();

        if (! ( (defnCode == DefinitionImpl.VARIABLE) ||
                (defnCode == DefinitionImpl.VALUE_PARM) ||
                (defnCode == DefinitionImpl.VAR_PARM) ||
                (isFunctionTarget && (defnCode == DefinitionImpl.FUNCTION) )
              )
            )
        {
            errorHandler.flag(token, INVALID_IDENTIFIER_USAGE, this);
        }

        variableId.appendLineNumber(token.getLineNumber());

        CodeNode variableNode = CodeFactory.createCodeNode(CodeNodeTypeImpl.VARIABLE);
        variableNode.setAttribute(ID, variableId);

        token = nextToken();  // consume the identifier
        // Parse array subscripts or record fields.
        TypeSpecification variableType = variableId.getTypeSpecification();

        if (!isFunctionTarget) {
            while (SUBSCRIPT_FIELD_START_SET.contains(token.getType())) {
                CodeNode subFldNode = token.getType() == LEFT_BRACKET
                                      ? parseSubscripts(variableType)
                                      : parseField(variableType);
                token = currentToken();

                // Update the variable's type.
                // The variable node adopts the SUBSCRIPTS or FIELD node.
                variableType = subFldNode.getTypeSpecification();
                variableNode.addChild(subFldNode);
            }
        }
        
        variableNode.setTypeSpecification(variableType);
        return variableNode;
    }
    // Synchronization set for the ] token.
    private static final EnumSet<PascalTokenType> RIGHT_BRACKET_SET =
            EnumSet.of(RIGHT_BRACKET, EQUALS, SEMICOLON);

    /**
     * Parse a set of comma-separated subscript expressions.
     * @param variableType the type of the array variable.
     * @return the root node of the generated parse tree.
     * @throws Exception if an error occurred.
     */
    private CodeNode parseSubscripts(TypeSpecification variableType) throws Exception {
        Token token;
        ExpressionParser expressionParser = new ExpressionParser(this);

        // Create a SUBSCRIPTS node.
        CodeNode subscriptsNode = CodeFactory.createCodeNode(SUBSCRIPTS);

        do {
            token = nextToken();  // consume the [ or , token

            // The current variable is an array.
            if (variableType.getForm() == TypeFormImpl.ARRAY) {

                // Parse the subscript expression.
                CodeNode exprNode = expressionParser.parse(token);
                TypeSpecification exprType = exprNode != null ? exprNode.getTypeSpecification()
                        : Predefined.undefinedType;

                // The subscript expression type must be assignment
                // compatible with the array index type.
                TypeSpecification indexType =
                        (TypeSpecification) variableType.getAttribute(ARRAY_INDEX_TYPE);

                if (!TypeChecker.areAssignmentCompatible(indexType, exprType)) {
                    errorHandler.flag(token, INCOMPATIBLE_TYPES, this);
                }

                // The SUBSCRIPTS node adopts the subscript expression tree.
                subscriptsNode.addChild(exprNode);

                // Update the variable's type.
                variableType =
                        (TypeSpecification) variableType.getAttribute(ARRAY_ELEMENT_TYPE);
            } // Not an array type, so too many subscripts.
            else {
                errorHandler.flag(token, TOO_MANY_SUBSCRIPTS, this);
                expressionParser.parse(token);
            }

            token = currentToken();
        } while (token.getType() == COMMA);

        // Synchronize at the ] token.
        token = synchronize(RIGHT_BRACKET_SET);
        if (token.getType() == RIGHT_BRACKET) {
            token = nextToken();  // consume the ] token
        } else {
            errorHandler.flag(token, MISSING_RIGHT_BRACKET, this);
        }

        subscriptsNode.setTypeSpecification(variableType);
        return subscriptsNode;
    }

    /**
     * Parse a record field.
     * @param variableType the type of the record variable.
     * @return the root node of the generated parse tree.
     * @throws Exception if an error occurred.
     */
    private CodeNode parseField(TypeSpecification variableType)
            throws Exception {
        // Create a FIELD node.
        CodeNode fieldNode = CodeFactory.createCodeNode(CodeNodeTypeImpl.FIELD);

        Token token = nextToken();  // consume the . token
        TokenType tokenType = token.getType();
        TypeForm variableForm = variableType.getForm();

        if (( tokenType == PascalTokenType.IDENTIFIER ) && ( variableForm == TypeFormImpl.RECORD )) {
            SymbolTable symbolTable = (SymbolTable) variableType.getAttribute(RECORD_SYMBOL_TABLE);
            String fieldName = token.getText().toLowerCase();
            SymbolTableEntry fieldId = symbolTable.lookup(fieldName);

            if (fieldId != null) {
                variableType = fieldId.getTypeSpecification();
                fieldId.appendLineNumber(token.getLineNumber());

                // Set the field identifier's name.
                fieldNode.setAttribute(ID, fieldId);
            } else {
                errorHandler.flag(token, INVALID_FIELD, this);
            }
        } else {
            errorHandler.flag(token, INVALID_FIELD, this);
        }

        token = nextToken();  // consume the field identifier

        fieldNode.setTypeSpecification(variableType);
        return fieldNode;
    }

    private boolean isFunctionTarget = false;

    public CodeNode parseFunctionNameTarget(Token token) throws Exception {
        isFunctionTarget = true;
        return parse(token);
    }
}
