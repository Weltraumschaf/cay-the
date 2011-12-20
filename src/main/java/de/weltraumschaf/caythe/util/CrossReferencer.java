package de.weltraumschaf.caythe.util;

import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.SymbolTableStack;
import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CrossReferencer {

    private static final int NAME_WIDTH = 16;
    private static final String NAME_FORMAT = "%-" + NAME_WIDTH + "s";
    private static final String NUMBERS_LABEL = " Line numbers    ";
    private static final String NUMBERS_UNDERLINE = " ------------    ";
    private static final String NUMBER_FORMAT = " %03d";
    private static final int LABEL_WIDTH = NUMBERS_LABEL.length();
    private static final int INDENT_WIDTH = NAME_WIDTH + LABEL_WIDTH;
    private static final StringBuilder INDENT = new StringBuilder(INDENT_WIDTH);

    static {
        for (int i = 0; i < INDENT_WIDTH; ++i) {
            INDENT.append(" ");
        }
    }

    /**
     * Print the cross-reference table.
     * @param symTabStack the symbol table stack.
     */
    public void print(SymbolTableStack symTabStack) {
        System.out.println("\n===== CROSS-REFERENCE TABLE =====");
        printColumnHeadings();

        printSymTab(symTabStack.getLocalSymTab());
    }

    /**
     * Print column headings.
     */
    private void printColumnHeadings() {
        System.out.println();
        System.out.println(String.format(NAME_FORMAT, "Identifier")
                + NUMBERS_LABEL);
        System.out.println(String.format(NAME_FORMAT, "----------")
                + NUMBERS_UNDERLINE);
    }

    private void printSymTab(SymbolTable symTab) {
        // Loop over the sorted list of symbol table entries.
        ArrayList<SymbolTableEntry> sorted = symTab.sortedEntries();

        for (SymbolTableEntry entry : sorted) {
            ArrayList<Integer> lineNumbers = entry.getLineNumbers();

            // For each entry, print the identifier name
            // followed by the line numbers.
            System.out.print(String.format(NAME_FORMAT, entry.getName()));

            if (null != lineNumbers) {
                for (Integer lineNumber : lineNumbers) {
                    System.out.print(String.format(NUMBER_FORMAT, lineNumber));
                }
            }

            System.out.println();
        }
    }
}
