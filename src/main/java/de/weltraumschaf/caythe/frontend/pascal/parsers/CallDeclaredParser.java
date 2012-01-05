package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.CodeFactory;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;

import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.ID;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl.CALL;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CallDeclaredParser extends CallParser {

    public CallDeclaredParser(PascalTopDownParser parent) {
        super(parent);
    }

    @Override
    public CodeNode parse(Token token) throws Exception {
        CodeNode callNode = CodeFactory.createCodeNode(CALL);
        SymbolTableEntry pfId = symbolTableStack.lookup(token.getText().toLowerCase());
        callNode.setAttribute(ID, pfId);
        callNode.setTypeSpecification(pfId.getTypeSpecification());

        token = nextToken(); // Consume procedure or function identifier.
        CodeNode parmsNode = parseActualParameters(token, pfId, true, false, false);
        callNode.addChild(parmsNode);
        return callNode;
    }
}
