package de.weltraumschaf.caythe.cli;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public final class FooIT extends BaseTestCase {

    @Test
    @Ignore
    public void foo() throws IOException {
        subcommand("create")
            .argument("-h")
            .expectOut("hello world")
            .execute();
    }

}
