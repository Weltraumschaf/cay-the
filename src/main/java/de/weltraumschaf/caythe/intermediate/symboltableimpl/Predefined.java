package de.weltraumschaf.caythe.intermediate.symboltableimpl;

import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.SymbolTableStack;
import de.weltraumschaf.caythe.intermediate.TypeFactory;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import java.util.ArrayList;

import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl.*;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.*;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Predefined {
    public static TypeSpecification integerType;
    public static TypeSpecification realType;
    public static TypeSpecification booleanType;
    public static TypeSpecification charType;
    public static TypeSpecification undefinedType;

    public static SymbolTableEntry integerId;
    public static SymbolTableEntry realId;
    public static SymbolTableEntry booleanId;
    public static SymbolTableEntry charId;
    public static SymbolTableEntry falseId;
    public static SymbolTableEntry trueId;

    public static void initialize(SymbolTableStack symbolTableStack) {
        initializeType(symbolTableStack);
        initializeConstants(symbolTableStack);
    }

    private static void initializeType(SymbolTableStack symbolTableStack) {
        // Type integer.
        integerId   = symbolTableStack.enterLocal("integer");
        integerType = TypeFactory.createType(SCALAR);
        integerType.setIdentifier(integerId);
        integerId.setDefinition(DefinitionImpl.TYPE);
        integerId.setTypeSpecification(integerType);

        // Type real.
        realId   = symbolTableStack.enterLocal("real");
        realType = TypeFactory.createType(SCALAR);
        realType.setIdentifier(realId);
        realId.setDefinition(DefinitionImpl.TYPE);
        realId.setTypeSpecification(realType);

        // Type boolean.
        booleanId   = symbolTableStack.enterLocal("boolean");
        booleanType = TypeFactory.createType(ENUMERATION);
        booleanType.setIdentifier(booleanId);
        booleanId.setDefinition(DefinitionImpl.TYPE);
        booleanId.setTypeSpecification(booleanType);

        // Type char.
        charId   = symbolTableStack.enterLocal("char");
        charType = TypeFactory.createType(SCALAR);
        charType.setIdentifier(charId);
        charId.setDefinition(DefinitionImpl.TYPE);
        charId.setTypeSpecification(charType);

        // undefined type.
        undefinedType = TypeFactory.createType(SCALAR);
    }

    private static void initializeConstants(SymbolTableStack symbolTableStack) {
        // Boolean enumeration constant false.
        falseId = symbolTableStack.enterLocal("false");
        falseId.setDefinition(DefinitionImpl.ENUMERATION_CONSTANT);
        falseId.setTypeSpecification(booleanType);
        falseId.setAttribute(CONSTANT_VALUE, new Integer(0));

        // Boolean enumeration constant true.
        falseId = symbolTableStack.enterLocal("true");
        falseId.setDefinition(DefinitionImpl.ENUMERATION_CONSTANT);
        falseId.setTypeSpecification(booleanType);
        falseId.setAttribute(CONSTANT_VALUE, new Integer(1));

        // Add false and true to the boolean enumeration type.
        ArrayList<SymbolTableEntry> constants = new ArrayList<SymbolTableEntry>();
        constants.add(falseId);
        constants.add(trueId);
        booleanType.setAttribute(ENUMERATION_CONSTANTS, constants);
    }
}
