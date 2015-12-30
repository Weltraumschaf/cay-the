
package de.weltraumschaf.caythe.backend;

import org.antlr.v4.runtime.Token;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link SyntaxError}.
 */
public class SyntaxErrorTest {

    @Test
    public void formatMessage() {
        final Token token = mock(Token.class);
        when(token.getLine()).thenReturn(23);
        when(token.getCharPositionInLine()).thenReturn(42);

        final SyntaxError sut = new SyntaxError("Error found", token);

        assertThat(
            sut.getMessage(),
            is("Error found (at line 23, column 43)!"));
    }

}