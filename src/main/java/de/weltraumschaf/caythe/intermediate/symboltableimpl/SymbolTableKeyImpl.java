package de.weltraumschaf.caythe.intermediate.symboltableimpl;

import de.weltraumschaf.caythe.intermediate.SymbolTableKey;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public enum SymbolTableKeyImpl implements SymbolTableKey {
    // Constant.
    CONSTANT_VALUE,

    // Procedure or function.
    ROUTINE_CODE, ROUTINE_SYMBOL_TABLE, ROUTINE_INTERMEDIATE_CODE,
    ROUTINE_PARAMS, ROUTINE_ROUTINES,

    // Variable or record field value.
    DATA_VALUE
}
