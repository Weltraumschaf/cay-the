package de.weltraumschaf.caythe.intermediate.typeimpl;

import de.weltraumschaf.caythe.intermediate.TypeForm;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.Predefined;

import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class TypeChecker {

    public static boolean isInteger(TypeSpecification type) {
        return (type != null) && (type.baseType() == Predefined.integerType);
    }

    public static boolean areBothInteger(TypeSpecification type1, TypeSpecification type2) {
        return isInteger(type1) && isInteger(type2);
    }

    public static boolean isReal(TypeSpecification type) {
        return (type != null) && (type.baseType() == Predefined.realType);
    }

    public static boolean isIntegerOrReal(TypeSpecification type) {
        return isInteger(type) || isReal(type);
    }

    public static boolean isAtLeastOneReal(TypeSpecification type1, TypeSpecification type2) {
        return (isReal(type1) && isReal(type2)) ||
               (isReal(type1) && isInteger(type2)) ||
               (isInteger(type1) && isReal(type2));
    }

    public static boolean isBoolean(TypeSpecification type) {
        return (type != null) && (type.baseType() == Predefined.booleanType);
    }

    public static boolean areBothBoolean(TypeSpecification type1, TypeSpecification type2) {
        return isBoolean(type1) && isBoolean(type2);
    }

    public static boolean isChar(TypeSpecification type) {
        return (type != null) && (type.baseType() == Predefined.charType);
    }

    public static boolean areAssignementCompatible(TypeSpecification targetType, TypeSpecification valueType) {
        if (null == targetType || null == valueType) {
            return false;
        }

        targetType = targetType.baseType();
        valueType  = valueType.baseType();
        boolean compatible = false;

        // Identical types
        if (targetType == valueType) {
            compatible = true;
        }
        // real := integer
        else if (isReal(targetType) && isInteger(valueType)) {
            compatible = true;
        }
        // string := string
        else {
            compatible = targetType.isPascalString() && valueType.isPascalString();
        }

        return compatible;
    }

    public static boolean areComparisonCompatible(TypeSpecification type1, TypeSpecification type2) {
        if (null == type1 || null == type2) {
            return false;
        }

        type1 = type1.baseType();
        type2 = type2.baseType();
        TypeForm form = type1.getForm();
        boolean compatible = false;

        if (type1 == type2 && (form == SCALAR || form == ENUMERATION)) {
            compatible = true;
        }
        // One integer and one real.
        else if (isAtLeastOneReal(type1, type2)) {
            compatible = true;
        }
        // Two strings
        else {
            compatible = type1.isPascalString() && type2.isPascalString();
        }

        return compatible;
    }
}
