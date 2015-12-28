
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
        assertThat(sut.getGlobals().resolve("Bool"), is(BuildInTypeSymbol.BOOL));
        assertThat(sut.getGlobals().resolve("Float"), is(BuildInTypeSymbol.FLOAT));
        assertThat(sut.getGlobals().resolve("Function"), is(BuildInTypeSymbol.FUNCTION));
        assertThat(sut.getGlobals().resolve("Int"), is(BuildInTypeSymbol.INT));
        assertThat(sut.getGlobals().resolve("Nil"), is(BuildInTypeSymbol.NIL));
        assertThat(sut.getGlobals().resolve("String"), is(BuildInTypeSymbol.STRING));
    }

    @Test
    public void initNativeApis() {
        assertThat(
            sut.getGlobals().resolve("print"),
            is(new FunctionSymbol("print", BuildInTypeSymbol.NIL, sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolve("println"),
            is(new FunctionSymbol("println", BuildInTypeSymbol.NIL, sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolve("error"),
            is(new FunctionSymbol("error", BuildInTypeSymbol.NIL, sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolve("errorln"),
            is(new FunctionSymbol("errorln", BuildInTypeSymbol.NIL, sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolve("exit"),
            is(new FunctionSymbol("exit", BuildInTypeSymbol.NIL, sut.getGlobals())));
    }

}