package de.weltraumschaf.caythe.cli;

import junitx.framework.FileAssert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertThat;

public final class FooIT extends BaseTestCase {

    @Test
    @Ignore
    public void foo() {
        subcommand("create")
            .argument("-h")
            .expectOut("hello world")
            .execute();
    }

}
