package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.CodeFactory;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class BlockParser extends PascalTopDownParser {

    public BlockParser(PascalTopDownParser parent) {
        super(parent);
    }

    public CodeNode parse(Token token, SymbolTableEntry routineId) throws Exception {
        DeclarationsParser declarationsParser = new DeclarationsParser(this);
        StatementParser statementParser       = new StatementParser(this);
        // Parse any declarations.
        declarationsParser.parse(token, routineId);
        token = synchronize(StatementParser.STATEMENT_START_SET);
        TokenType tokenType = token.getType();
        CodeNode rootNode = null;

        // Look for the BEGIN token to parse a compund statement.
        if (BEGIN == tokenType) {
            rootNode = statementParser.parse(token);
        }
        // Missing BEGIN: Attempt to parse anyway if possible
        else {
            errorHandler.flag(token, MISSING_BEGIN, this);

            if (StatementParser.STATEMENT_START_SET.contains(tokenType)) {
                rootNode = CodeFactory.createCodeNode(COMPOUND);
                statementParser.parseList(token, rootNode, END, MISSING_END);
            }
        }

        return rootNode;
    }
}
