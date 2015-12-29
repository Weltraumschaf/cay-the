
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
    public void isBuildInType() {
        assertThat(sut.isBuildInType("Foo"), is(false));
        assertThat(sut.isBuildInType("Bar"), is(false));
        assertThat(sut.isBuildInType("Bool"), is(true));
        assertThat(sut.isBuildInType("Float"), is(true));
        assertThat(sut.isBuildInType("Function"), is(true));
        assertThat(sut.isBuildInType("Int"), is(true));
        assertThat(sut.isBuildInType("Nil"), is(true));
        assertThat(sut.isBuildInType("String"), is(true));
    }

    @Test
    public void initNativeApis() {
        assertThat(
            sut.getGlobals().resolveFunction("print"),
            is(new FunctionSymbol(
                "print",
                FunctionSymbol.VOID,
                SymbolTable.arguments(BuildInTypeSymbol.STRING),
                sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolveFunction("println"),
            is(new FunctionSymbol(
                "println",
                FunctionSymbol.VOID,
                SymbolTable.arguments(BuildInTypeSymbol.STRING),
                sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolveFunction("error"),
            is(new FunctionSymbol(
                "error",
                FunctionSymbol.VOID,
                SymbolTable.arguments(BuildInTypeSymbol.STRING),
                sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolveFunction("errorln"),
            is(new FunctionSymbol(
                "errorln",
                FunctionSymbol.VOID,
                SymbolTable.arguments(BuildInTypeSymbol.STRING),
                sut.getGlobals())));
        assertThat(
            sut.getGlobals().resolveFunction("exit"),
            is(new FunctionSymbol(
                "exit",
                FunctionSymbol.VOID,
                SymbolTable.arguments(BuildInTypeSymbol.INT),
                sut.getGlobals())));
    }

    @Test
    public void isBuildInFunction() {
        assertThat(sut.isBuildInFunction("foo"), is(false));
        assertThat(sut.isBuildInFunction("bar"), is(false));
        assertThat(sut.isBuildInFunction("print"), is(true));
        assertThat(sut.isBuildInFunction("println"), is(true));
        assertThat(sut.isBuildInFunction("error"), is(true));
        assertThat(sut.isBuildInFunction("errorln"), is(true));
        assertThat(sut.isBuildInFunction("exit"), is(true));
    }

}