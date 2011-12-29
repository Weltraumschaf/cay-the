package de.weltraumschaf.caythe.util;

import de.weltraumschaf.caythe.intermediate.TypeForm;
import de.weltraumschaf.caythe.intermediate.Definition;
import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.SymbolTableStack;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl;
import de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl;
import java.util.ArrayList;

import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.*;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.*;

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
     * @param symbolTableStack the symbol table stack.
     */
    public void print(SymbolTableStack symbolTableStack) {
        System.out.println("\n===== CROSS-REFERENCE TABLE =====");
        SymbolTableEntry programId = symbolTableStack.getProgramId();
        printRoutine(programId);
    }

    private void printRoutine(SymbolTableEntry routineId) {
        Definition definition = routineId.getDefinition();
        System.out.println("\n*** " + definition.toString() + " " + routineId.getName() + " ***");
        printColumnHeadings();

        // Print the entries int the routine's symbol table.
        SymbolTable symTab = (SymbolTable) routineId.getAttribute(ROUTINE_SYMBOL_TABLE);
        ArrayList<TypeSpecification> newRecordType = new ArrayList<TypeSpecification>();
        printSymbolTable(symTab, newRecordType);

        // Print cross reference table for any records defined int the routine.
        if (newRecordType.size() > 0) {
            printRecords(newRecordType);
        }

        // Print any procedures and functions defined int the routine.
        ArrayList<SymbolTableEntry> routineIds = (ArrayList<SymbolTableEntry>) routineId.getAttribute(ROUTINE_ROUTINES);

        if (routineIds != null) {
            for (SymbolTableEntry rtnId : routineIds) {
                printRoutine(rtnId);
            }
        }
    }

    /**
     * Print column headings.
     */
    private void printColumnHeadings() {
        System.out.println();
        System.out.println(String.format(NAME_FORMAT, "Identifier")
                + NUMBERS_LABEL + "Type specification");
        System.out.println(String.format(NAME_FORMAT, "----------")
                + NUMBERS_UNDERLINE + "------------------");
    }

    private void printSymbolTable(SymbolTable symTab, ArrayList<TypeSpecification> recordType) {
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
            printEntry(entry, recordType);
        }
    }

    private void printRecords(ArrayList<TypeSpecification> recordTypes) {
        for (TypeSpecification recordType : recordTypes) {
            SymbolTableEntry recordId = recordType.getIdentifier();
            String name = recordId != null ? recordId.getName() : "<unnamed>";
            System.out.println("\n--- Record " + name + " ---");
            printColumnHeadings();
            SymbolTable symTab = (SymbolTable) recordType.getAttribute(RECORD_SYMBOL_TABLE);
            ArrayList<TypeSpecification> newRecordTypes = new ArrayList<TypeSpecification>();
            printSymbolTable(symTab, newRecordTypes);

            // Print cross reference table for any nested records.
            if (newRecordTypes.size() > 0) {
                printRecords(newRecordTypes);
            }
        }
    }

    private void printEntry(SymbolTableEntry entry, ArrayList<TypeSpecification> recordType) {
        Definition definition = entry.getDefinition();
        int nestingLevel = entry.getSymbolTable().getNestingLevel();
        System.out.println(INDENT + "Defined as: " + definition.getTetx());
        System.out.println(INDENT + "Scope nesting level: " + nestingLevel);

        // Print the type specification.
        TypeSpecification type = entry.getTypeSpecification();
        printType(type);

        switch ((DefinitionImpl) definition) {
            case CONSTANT: {
                Object value = entry.getAttribute(CONSTANT_VALUE);
                System.out.println(INDENT + "Value = " + toString(value));

                // PRint the type details only if the type is unamed.
                if (type.getIdentifier() == null) {
                    printTypeDetails(type, recordType);
                }

                break;
            }

            case ENUMERATION_CONSTANT: {
                Object value = entry.getAttribute(CONSTANT_VALUE);
                System.out.println(INDENT + "Value = " + toString(value));
                break;
            }

            case TYPE: {
                // Print the type details only when the type is first defined.
                if (entry == type.getIdentifier()) {
                    printTypeDetails(type, recordType);
                }

                break;
            }

            case VARIABLE: {
                // Print the type details only if the type is unamed.
                if (type.getIdentifier() == null) {
                    printTypeDetails(type, recordType);
                }

                break;
            }
        }
    }

    private void printType(TypeSpecification type) {
        if (null != type) {
            TypeForm form = type.getForm();
            SymbolTableEntry typeId = type.getIdentifier();
            String typeName = typeId != null ? typeId.getName() : "<unnamed>";
            System.out.println(INDENT + "Type form = " + form + ", Type id = " + typeName);
        }
    }

    private String toString(Object value) {
        return value instanceof String
                ? "'" + (String) value + "'"
                : value.toString();
    }
    private static final String ENUM_CONST_FORMAT = "%" + NAME_WIDTH + "s = %s";

    private void printTypeDetails(TypeSpecification type, ArrayList<TypeSpecification> recordType) {
        TypeForm form = type.getForm();

        switch ((TypeFormImpl) form) {
            case ENUMERATION: {
                ArrayList<SymbolTableEntry> constantIds = (ArrayList<SymbolTableEntry>) type.getAttribute(ENUMERATION_CONSTANTS);
                System.out.println(INDENT + "--- Enumeration constants ---");

                for (SymbolTableEntry constantId: constantIds) {
                    String name = constantId.getName();
                    Object value = constantId.getAttribute(CONSTANT_VALUE);
                    System.out.println(INDENT + String.format(ENUM_CONST_FORMAT, name, value));
                }

                break;
            }

            case SUBRANGE: {
                Object minValue = type.getAttribute(SUBRANGE_MIN_VALUE);
                Object maxValue = type.getAttribute(SUBRANGE_MAX_VALUE);
                TypeSpecification baseTypeSpec = (TypeSpecification) type.getAttribute(SUBRANGE_BASE_TYPE);
                System.out.println(INDENT + "--- Base type ---");
                printType(baseTypeSpec);

                // Print the base type details only if the type is unamed.
                if (baseTypeSpec.getIdentifier() == null) {
                    printTypeDetails(baseTypeSpec, recordType);
                }

                System.out.println(INDENT + "Range = " + toString(minValue) + ".." + toString(maxValue));

                break;
            }

            case ARRAY: {
                TypeSpecification indexType = (TypeSpecification) type.getAttribute(ARRAY_INDEX_TYPE);
                TypeSpecification elementType = (TypeSpecification) type.getAttribute(ARRAY_ELEMENT_TYPE);
                int count = (Integer) type.getAttribute(ARRAY_ELEMENT_COUNT);
                System.out.println(INDENT + "--- Index type ---");
                printType(indexType);

                if (indexType.getIdentifier() == null) {
                    printTypeDetails(indexType, recordType);
                }

                System.out.println(INDENT + "--- Element type ---");
                printType(elementType);
                System.out.println(INDENT.toString() + count + " elements");

                if (elementType.getIdentifier() == null) {
                    printTypeDetails(elementType, recordType);
                }
                break;
            }

            case RECORD: {
                recordType.add(type);
                break;
            }
        }
    }
}
