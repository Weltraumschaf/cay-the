package de.weltraumschaf.caythe;

import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.validate.Validate;

/**
 */
final class DefaultEnvironmnet implements Environment {

    private final IO io;

    public DefaultEnvironmnet(final IO io) {
        super();
        this.io = Validate.notNull(io, "io");
    }

    @Override
    public void stdOut(final String output) {
        io.print(output);
    }

    @Override
    public void stdErr(final String output) {
        io.error(output);
    }

    @Override
    public String stdIn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
