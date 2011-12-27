package de.weltraumschaf.caythe.intermediate.typeimpl;

import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.TypeForm;
import de.weltraumschaf.caythe.intermediate.TypeKey;
import de.weltraumschaf.caythe.intermediate.TypeSpec;
import java.util.HashMap;

import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl.*;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.*;
/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class TypeSpecImpl extends HashMap<TypeKey, Object> implements TypeSpec {

    static class Predefined {
        static Object integerType;
        static Object charType;
    }

    private TypeForm form;
    private SymbolTableEntry identifer;

    public TypeSpecImpl(TypeForm form) {
        this.form = form;
        this.identifer = null;
    }

    public TypeSpecImpl(String value) {
        this.form = ARRAY;

        TypeSpec indexType = new TypeSpecImpl(SUBRANGE);
        indexType.setAttribute(SUBRANGE_BASE_TYPE, Predefined.integerType);
        indexType.setAttribute(SUBRANGE_MIN_VALUE, 1);
        indexType.setAttribute(SUBRANGE_MAX_VALUE, value.length());

        setAttribute(ARRAY_INDEX_TYPE, indexType);
        setAttribute(ARRAY_ELEMENT_TYPE, Predefined.charType);
        setAttribute(ARRAY_ELEMENT_COUNT, value.length());
    }

    @Override
    public TypeSpec baseType() {
        return (TypeFormImpl) form == SUBRANGE
                    ? (TypeSpec) getAttribute(SUBRANGE_BASE_TYPE)
                    : this;
    }

    @Override
    public Object getAttribute(TypeKey key) {
        return this.get(key);
    }

    @Override
    public void setAttribute(TypeKey key, Object value) {
        this.put(key, value);
    }

    @Override
    public TypeForm getForm() {
        return form;
    }

    @Override
    public SymbolTableEntry getIdentifier() {
        return identifer;
    }

    @Override
    public void setIdentifer(SymbolTableEntry identifer) {
        this.identifer = identifer;
    }

    @Override
    public boolean isPascalString() {
        if (ARRAY == form) {
            TypeSpec elementType = (TypeSpec) getAttribute(ARRAY_ELEMENT_TYPE);
            TypeSpec indexType   = (TypeSpec) getAttribute(ARRAY_INDEX_TYPE);

            return (elementType.baseType() == Predefined.charType ) &&
                   (indexType.baseType()   == Predefined.integerType);
        }

        return false;
    }

}
