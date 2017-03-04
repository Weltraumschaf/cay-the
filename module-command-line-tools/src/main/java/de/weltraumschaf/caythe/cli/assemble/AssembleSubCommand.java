package de.weltraumschaf.caythe.cli.assemble;

import de.weltraumschaf.caythe.backend.vm.Assembler;
import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 */
public final class AssembleSubCommand implements SubCommand {
    private final String ASM_FILE_EXTENSION = ".ctasm";
    private final String OBJ_FILE_EXTENSION = ".cto";
    private final CliContext ctx;

    public AssembleSubCommand(final CliContext ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public void execute() throws Exception {
        final AssembleCliOptions options = ctx.getOptions().getAssemble();
        final Assembler asm = new Assembler();
        final Path source = Paths.get(options.getFile());
        final byte[] code = asm.assemble(source);
        final String outputFilename = source.toString().replace(ASM_FILE_EXTENSION, OBJ_FILE_EXTENSION);
        Files.write(Paths.get(outputFilename), code);
    }
}
