package de.weltraumschaf.caythe.backend.symtab;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes the types built in in the language.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class BuildInTypeSymbol extends BaseSymbol implements Type {

    /**
     * Build in NIL type which represents nothing/not available.
     */
    @BuildInType
    public static final Type NIL = new BuildInTypeSymbol("Nil");
    /**
     * Build in boolean type.
     */
    @BuildInType
    public static final Type BOOL = new BuildInTypeSymbol("Bool");
    /**
     * Build in integer type.
     */
    @BuildInType
    public static final Type INT = new BuildInTypeSymbol("Int");
    /**
     * Build in float type.
     */
    @BuildInType
    public static final Type FLOAT = new BuildInTypeSymbol("Float");
    /**
     * Build in string type.
     */
    @BuildInType
    public static final Type STRING = new BuildInTypeSymbol("String");
    /**
     * Build in function type (functions are first class citizens).
     */
    @BuildInType
    public static final Type FUNCTION = new BuildInTypeSymbol("Function");

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     */
    public BuildInTypeSymbol(final String name) {
        super(name, Type.NULL);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public static @interface BuildInType {
    }
}
