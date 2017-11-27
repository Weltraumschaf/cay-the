package de.weltraumschaf.caythe.intermediate.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.Block;
import de.weltraumschaf.caythe.intermediate.ast.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Builder to create blocks.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class BlockBuilder {
    private final Collection<AstNode> statements = new ArrayList<>();
    private final Position position;

    private BlockBuilder(final Position position) {
        super();
        this.position = position;
    }

    public static BlockBuilder block(final int line, final int column) {
        return new BlockBuilder(new Position(line, column));
    }

    public Block end() {
        return new Block(statements, position);
    }

    public BlockBuilder statement(final AstNode statement, final int line, final int column) {
        statements.add(new Statement(statement, new Position(line, column)));
        return this;
    }
}
