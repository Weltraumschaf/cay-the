
package de.weltraumschaf.caythe.backend.symtab;

import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link SymbolTable}.
 */
public class SymbolTableTest {

    private final SymbolTable sut = SymbolTable.newTable();

    @Test
    public void initBuildInTypes() {
        assertThat(sut.getGlobals().resolveValue("Bool"), is(BuildInTypeSymbol.BOOL));
        assertThat(sut.getGlobals().resolveValue("Float"), is(BuildInTypeSymbol.FLOAT));
        assertThat(sut.getGlobals().resolveValue("Function"), is(BuildInTypeSymbol.FUNCTION));
        assertThat(sut.getGlobals().resolveValue("Int"), is(BuildInTypeSymbol.INT));
        assertThat(sut.getGlobals().resolveValue("Nil"), is(BuildInTypeSymbol.NIL));
        assertThat(sut.getGlobals().resolveValue("String"), is(BuildInTypeSymbol.STRING));
    }

    @Test
    public void initNativeApis() {
        assertThat(
            sut.getGlobals().resolveFunction("print"),
            is(new FunctionSymbol("print", BuildInTypeSymbol.NIL, sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolveFunction("println"),
            is(new FunctionSymbol("println", BuildInTypeSymbol.NIL, sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolveFunction("error"),
            is(new FunctionSymbol("error", BuildInTypeSymbol.NIL, sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolveFunction("errorln"),
            is(new FunctionSymbol("errorln", BuildInTypeSymbol.NIL, sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolveFunction("exit"),
            is(new FunctionSymbol("exit", BuildInTypeSymbol.NIL, sut.getGlobals())));
    }

}