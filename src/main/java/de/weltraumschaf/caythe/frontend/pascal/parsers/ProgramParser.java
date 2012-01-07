package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;

import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import java.util.EnumSet;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class ProgramParser extends DeclarationsParser {

    public ProgramParser(PascalTopDownParser parent) {
        super(parent);
    }

    static final EnumSet<PascalTokenType> PROGRAM_SART_SET =
        EnumSet.of(PROGRAM, SEMICOLON);

    static {
        PROGRAM_SART_SET.addAll(DeclarationsParser.DECLARATION_START_SET);
    }

    @Override
    public SymbolTableEntry parse(Token token, SymbolTableEntry parentId) throws Exception {
        token = synchronize(PROGRAM_SART_SET);
        DeclaredRoutineParser routineParser = new DeclaredRoutineParser(this);
        routineParser.parse(token, parentId);

        token = currentToken();

        if (token.getType() != DOT) {
            errorHandler.flag(token, PascalErrorCode.MISSING_PERIOD, this);
        }

        return null;
    }
}
