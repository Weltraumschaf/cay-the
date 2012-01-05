package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.RoutineCode;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;

import static de.weltraumschaf.caythe.intermediate.symboltableimpl.RoutineCodeImpl.*;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.ROUTINE_CODE;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CallParser extends StatementParser {

    public CallParser(PascalTopDownParser parent) {
        super(parent);
    }

    @Override
    public CodeNode parse(Token token) throws Exception {
        SymbolTableEntry pfId      = symbolTableStack.lookup(token.getText().toLowerCase());
        RoutineCode routineCode    = (RoutineCode) pfId.getAttribute(ROUTINE_CODE);
        StatementParser callParser;

        if ((routineCode == DECLARED) || (routineCode == FORWARD)) {
            callParser = new CallDeclaredParser(this);
        } else {
            callParser = new CallStandardParser(this);
        }

        return callParser.parse(token);
    }

    protected CodeNode parseActualParameters(Token token, SymbolTableEntry pfId, boolean isDecalred, boolean isReadReadln, boolean isWriteWriteln) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
