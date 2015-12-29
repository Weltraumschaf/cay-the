package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.caythe.backend.KernelApi;
import de.weltraumschaf.caythe.backend.Native;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

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
     * Cached for fast verification lookup.
     */
    private final Collection<String> buildInTypeNames = new ArrayList<>();
    /**
     * Cached for fast verification lookup.
     */
    private final Collection<String> buildInFunctionNames = new ArrayList<>();
    private final Deque<Scope> scopeStack = new LinkedList<>();

    /**
     * Dedicated constructor.
     * <p>
     * Use {@link #newTable() factory method} to create instances.
     * </p>
     */
    SymbolTable() {
        super();
        scopeStack.push(globals);
    }

    /**
     * Get the global scope.
     *
     * @return never {@code null}, always same instance
     */
    public Scope globalScope() {
        return globals;
    }

    public Scope currentScope() {
        return scopeStack.peek();
    }

    public void pushNewScope() {
        pushScope(Scope.newLocal(scopeStack.peek()));
    }
    public void pushScope(final Scope newScope) {
        scopeStack.push(newScope);
    }

    public void popScope() {
        scopeStack.pop();
    }

    public int scopeDepth() {
        return scopeStack.size();
    }

    @Override
    public String toString() {
        return globals.toString();
    }

    private void defineBuildInType(final Symbol type) {
        globals.defineValue(type);
        buildInTypeNames.add(type.getName());
    }

    private void defineBuildInFunction(final String name, final List<Type> returnTypes, final List<ConstantSymbol> argumentTypes) {
        globals.defineFunction(new FunctionSymbol(
                name,
                returnTypes,
                argumentTypes,
                globalScope()));
        buildInFunctionNames.add(name);
    }

    public boolean isBuildInType(final String name) {
        return buildInTypeNames.contains(name);
    }

    public boolean isBuildInFunction(final String name) {
        return buildInFunctionNames.contains(name);
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
        BuildInTypeSymbol.values().stream().forEach((buildInType) -> {
            table.defineBuildInType(buildInType);
        });
    }

    private static void initNativeApis(final SymbolTable table) {
        for (final Method apiFunction : KernelApi.class.getDeclaredMethods()) {
            final Native annotation = apiFunction.getAnnotation(Native.class);

            if (null == annotation) {
                continue;
            }

            final List<Type> returnTypes;

            if (annotation.returnTypes().length == 0) {
                returnTypes = FunctionSymbol.VOID;
            } else {
                returnTypes = new ArrayList<>();

                for (final String type : annotation.returnTypes()) {
                    if (table.globalScope().isValueDefined(type)) {
                        returnTypes.add((Type) table.globalScope().resolveValue(type));
                    } else {
                        throw new IllegalStateException(
                            String.format(
                                "Undefined type '%s' used as return type for function '%s'!",
                                type, apiFunction.getName()));
                    }
                }
            }

            final List<ConstantSymbol> formalArguements;
            if (annotation.argumentTypes().length == 0) {
                formalArguements = FunctionSymbol.NOARGS;
            } else {
                final Collection<Type> types = new ArrayList<>();

                for (final String type : annotation.argumentTypes()) {
                    if (table.globalScope().isValueDefined(type)) {
                        types.add((Type) table.globalScope().resolveValue(type));
                    } else {
                        throw new IllegalStateException(
                            String.format(
                                "Undefined type '%s' used as return type for function '%s'!",
                                type, apiFunction.getName()));
                    }
                }

                formalArguements = arguments(types.toArray(new Type[0]));
            }

            table.defineBuildInFunction(apiFunction.getName(), returnTypes, formalArguements);
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

    static List<ConstantSymbol> arguments(final Type... types) {
        final List<ConstantSymbol> args = new ArrayList<>();

        if (null != types) {
            for (final Type type : types) {
                args.add(new ConstantSymbol("_", type));
            }
        }

        return args;
    }
}
