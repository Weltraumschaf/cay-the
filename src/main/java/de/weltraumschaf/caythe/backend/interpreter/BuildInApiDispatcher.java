package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.KernelApi;
import de.weltraumschaf.caythe.backend.env.Environment;
import de.weltraumschaf.caythe.backend.symtab.BuildInTypeSymbol;
import de.weltraumschaf.caythe.backend.symtab.ConstantSymbol;
import de.weltraumschaf.caythe.backend.symtab.FunctionSymbol;
import de.weltraumschaf.caythe.backend.symtab.Symbol;
import de.weltraumschaf.caythe.backend.symtab.Type;
import de.weltraumschaf.caythe.backend.symtab.Value;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dispatches the function call symbol for native APIs to the native Java implementations.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class BuildInApiDispatcher {

    private static final Map<Type, Class<?>> BUILDIN_TO_NATIVE;

    static {
        final Map<Type, Class<?>> tmp = new HashMap<>();
        tmp.put(BuildInTypeSymbol.BOOL, Boolean.class);
        tmp.put(BuildInTypeSymbol.FLOAT, Float.class);
//        tmp.put(BuildInTypeSymbol.FUNCTION, Object.class);
//        tmp.put(BuildInTypeSymbol.NIL, Void.class);
        tmp.put(BuildInTypeSymbol.STRING, String.class);
        BUILDIN_TO_NATIVE = Collections.unmodifiableMap(tmp);
    }
    private final KernelApi kernel;

    public BuildInApiDispatcher(final Environment env) {
        super();
        this.kernel = new KernelApi(env);
    }

    /**
     * Invokes the function identified by the symbol with the given arguments.
     *
     * @param function must not be {@code null}
     * @param arguments must not be {@code null}
     * @return  never {@code null}, unmodifiable
     */
    ReturnValues invoke(final FunctionSymbol function, final List<Value> arguments) {
        final int argumentCount = arguments.size();

        if (function.getArgumentTypes().size() != argumentCount) {
            throw new IllegalArgumentException(String.format("Argument count missmatch! Expected %d function arguments, but %d given.",
                function.getArgumentTypes().size(), argumentCount));
        }

        final Class<?>[] nativeTypes = new Class[argumentCount];
        final Object[] nativeArguments = new Object[argumentCount];
        final List<ConstantSymbol> expectedArgumentSymbols = function.getArgumentTypes();

        for (int i = 0; i < expectedArgumentSymbols.size(); ++i) {
            final Type type =  expectedArgumentSymbols.get(i).getType();
            nativeTypes[i] = BUILDIN_TO_NATIVE.get(type);
            final Value argument = arguments.get(i);

            if (BuildInTypeSymbol.NIL.equals(type)) {
                nativeArguments[i] = null;
            } else if (BuildInTypeSymbol.BOOL.equals(type)) {
                nativeArguments[i] = argument.asBool();
            } else if (BuildInTypeSymbol.INT.equals(type)) {
                nativeArguments[i] = argument.asInt();
            } else if (BuildInTypeSymbol.FLOAT.equals(type)) {
                nativeArguments[i] = argument.asFloat();
            } else if (BuildInTypeSymbol.STRING.equals(type)) {
                nativeArguments[i] = argument.asString();
            } else {
                throw new IllegalArgumentException(String.format("Unsupported function argument %s!", type));
            }
        }

        try {
            final Method method = kernel.getClass().getMethod(function.getName(), nativeTypes);
            final Object result = method.invoke(kernel, nativeArguments);

            return null == result
                ? ReturnValues.NOTHING
                : verifyResult(cast(result));
        } catch (final NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    @SuppressWarnings("unchecked")
    private Collection<Value> cast(final Object in) {
        return (Collection<Value>) in;
    }

    private ReturnValues verifyResult(final Collection<Value> in) {
        // Todo check size to size of returnTypes from symbol.
        return new ReturnValues(in.toArray(new Value[0]));
    }
}
