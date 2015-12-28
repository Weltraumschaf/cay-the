package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.caythe.backend.KernelApi;
import de.weltraumschaf.caythe.backend.Native;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Holds the global scope and initializes build in types.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class SymbolTable {

    /**
     * The one and only global scope.
     */
    private final Scope globals = Scope.newGlobal();

    /**
     * Dedicated constructor.
     * <p>
     * Use {@link #newTable() factory method} to create instances.
     * </p>
     */
    SymbolTable() {
        super();
    }

    /**
     * Get the global scope.
     *
     * @return never {@code null}, always same instance
     */
    public Scope getGlobals() {
        return globals;
    }

    @Override
    public String toString() {
        return globals.toString();
    }

    /**
     * Define build in types.
     *
     * @param table must not be {@code null}
     */
    public static void init(final SymbolTable table) {
        initBuildInTypes(table);
        initNativeApis(table);
    }

    private static void initBuildInTypes(final SymbolTable table) {
        for (final Field field : BuildInTypeSymbol.class.getDeclaredFields()) {
            if (null == field.getAnnotation(BuildInTypeSymbol.BuildInType.class)) {
                continue;
            }

            try {
                table.globals.define((Symbol) field.get(null));
            } catch (final IllegalArgumentException | IllegalAccessException ex) {
                throw new IllegalStateException(ex.getMessage(), ex);
            }
        }
    }

    private static void initNativeApis(final SymbolTable table) {
        for (final Method api : KernelApi.class.getDeclaredMethods()) {
            if (null == api.getAnnotation(Native.class)) {
                continue;
            }

            table.globals.define(
                new FunctionSymbol(
                    api.getName(),
                    /* no return type */BuildInTypeSymbol.NIL,
                    table.getGlobals()));
        }
    }

    /**
     * Crete and initialize a new table.
     *
     * @return never {@code null}, always new instance
     */
    public static SymbolTable newTable() {
        final SymbolTable table = new SymbolTable();
        init(table);
        return table;
    }
}
