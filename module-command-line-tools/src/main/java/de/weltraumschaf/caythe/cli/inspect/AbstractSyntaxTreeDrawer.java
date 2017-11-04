package de.weltraumschaf.caythe.cli.inspect;

import de.weltraumschaf.caythe.intermediate.model.Module;
import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.validate.Validate;

import java.nio.file.Path;

/**
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
final class AbstractSyntaxTreeDrawer {
    private final IO io;

    AbstractSyntaxTreeDrawer(final IO io) {
        super();
        this.io = Validate.notNull(io, "io");
    }

    void draw(final Module module, final Path targetDirectory) {
        io.errorln("Not implemented yet!");
        //            final DotGenerator generator = new DotGenerator();
//            ast.accept(generator);
//            ctx.getIo().print(generator.getGraph());
    }
}
