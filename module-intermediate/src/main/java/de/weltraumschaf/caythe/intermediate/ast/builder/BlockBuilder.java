package de.weltraumschaf.caythe.intermediate.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.Block;
import de.weltraumschaf.commons.validate.Validate;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;

/**
 * Builder to create blocks.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class BlockBuilder {

    private final Position position;
    public StatementBuilder statements;

    private BlockBuilder(final Position position) {
        super();
        this.position = Validate.notNull(position, "position");
    }

    public static BlockBuilder block(final int line, final int column) {
        return new BlockBuilder(new Position(line, column));
    }

    public static BlockBuilder block(final String file, final int line, final int column) {
        return new BlockBuilder(new Position(file, line, column));
    }

    public Block end() {
        return new Block(statements.getStatements(), position);
    }

    public StatementBuilder statements() {
        statements = new StatementBuilder(this);
        return statements;
    }

    String getFile() {
        return position.getFile();
    }
}
