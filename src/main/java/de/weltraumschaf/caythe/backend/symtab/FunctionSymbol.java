package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.caythe.backend.interpreter.ReturnValues;
import de.weltraumschaf.caythe.frontend.CayTheBaseVisitor;
import de.weltraumschaf.caythe.frontend.CayTheParser;
import de.weltraumschaf.commons.validate.Validate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This describes methods and functions.
 * <p>
 * Functions are symbols with an own local values. They also always have a {@link #getEnclosing() parent values}.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class FunctionSymbol extends BaseSymbol implements Scope {

    public static final List<Type> VOID = Collections.emptyList();
    public static final List<ConstantSymbol> NOARGS = Collections.emptyList();
    public static final FunctionSymbol NULL = new FunctionSymbol("_", VOID, NOARGS, Scope.NULL);

    /**
     * Delegate for the local values of a method.
     */
    private transient Scope values;
    /**
     * Delegate for the local functions of a method.
     */
    private final transient Scope functions;
    /**
     * Unmodifiable.
     */
    private final List<Type> returnTypes;
    /**
     * Unmodifiable.
     */
    private final List<ConstantSymbol> argumentSymbols;
    private transient CayTheParser.BlockContext body;

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     * @param returnTypes must not be {@code null}
     * @param argumentTypes must not be {@code null}
     * @param enclosingScope must not be {@code null}
     */
    public FunctionSymbol(final String name, final List<Type> returnTypes, final List<ConstantSymbol> argumentTypes, final Scope enclosingScope) {
        super(name, BuildInTypeSymbol.FUNCTION);
        this.values = Scope.newLocal(enclosingScope);
        this.functions = Scope.newLocal(enclosingScope);
        this.returnTypes = Collections.unmodifiableList(new ArrayList<>(Validate.notNull(returnTypes, "returnTypes")));
        this.argumentSymbols = Collections.unmodifiableList(new ArrayList<>(Validate.notNull(argumentTypes, "argumentTypes")));
    }

    public void body(final CayTheParser.BlockContext body) {
        this.body = body;
    }

    public List<Type> getReturnTypes() {
        return returnTypes;
    }

    public List<ConstantSymbol> getArgumentTypes() {
        return argumentSymbols;
    }

    @Override
    public Symbol resolveValue(final String name) {
        return values.resolveValue(name);
    }

    @Override
    public void defineValue(final Symbol sym) {
        values.defineValue(sym);
    }

    @Override
    public boolean isValueDefined(final String identifier) {
        return values.isValueDefined(identifier);
    }

    @Override
    public void defineFunction(final FunctionSymbol sym) {
        functions.defineFunction(sym);
    }

    @Override
    public boolean isFunctionDefined(final String identifier) {
        return functions.isFunctionDefined(identifier);
    }

    @Override
    public FunctionSymbol resolveFunction(final String name) {
        return functions.resolveFunction(name);
    }

    @Override
    public Scope getEnclosing() {
        return values.getEnclosing();
    }

    @Override
    public boolean hasEnclosing() {
        return values.hasEnclosing();
    }

    @Override
    public void store(final Symbol symbol, final Value value) {
        values.store(symbol, value);
    }

    @Override
    public Value load(final Symbol symbol) {
        return values.load(symbol);
    }

    @Override
    public void wipe() {
        values.wipe();
    }

    @Override
    public String getScopeName() {
        return getName();
    }

    // TOdo this code should be moved into interpreter package.
    public ReturnValues evaluate(final CayTheBaseVisitor<ReturnValues> evaluator, final List<Value> arguments) {
        if (argumentSymbols.size() != arguments.size()) {
            throw new IllegalStateException(String.format("Function argument count missmatch! Expected are %d arguemnts, but given were %d.",
                argumentSymbols.size(), arguments.size()));
        }

        wipe();

        for (int i = 0; i < arguments.size(); ++i) {
            final Value argument = arguments.get(i);
            final ConstantSymbol argumentSymbol = argumentSymbols.get(i);

            if (argument.isOfType(argumentSymbol.getType())) {
                defineValue(argumentSymbol);
                store(argumentSymbol, argument);
            } else {
                throw new IllegalStateException(String.format("Function argument type missatch on %d argument! Expected type is %s, but given was %s.",
                    i, argument.getType(), argumentSymbol.getType()));
            }
        }

        return evaluator.visit(body);
    }

    @Override
    public String toString() {
        return String.format("func %s %s(%s)", returnTypes, getName(), argumentSymbols);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), returnTypes, argumentSymbols);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof FunctionSymbol)) {
            return false;
        }

        final FunctionSymbol other = (FunctionSymbol) obj;
        return super.equals(other)
            && Objects.equals(returnTypes, other.returnTypes)
            && Objects.equals(argumentSymbols, other.argumentSymbols);
    }

}
