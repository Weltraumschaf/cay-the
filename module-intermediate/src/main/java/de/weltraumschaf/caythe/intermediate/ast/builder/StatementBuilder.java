package de.weltraumschaf.caythe.intermediate.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.Return;
import de.weltraumschaf.commons.validate.Validate;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class StatementBuilder {
    private final Collection<AstNode> statements = new ArrayList<>();
    private final BlockBuilder parent;

    StatementBuilder(final BlockBuilder parent) {
        super();
        this.parent = Validate.notNull(parent, "parent");
    }

    public StatementBuilder returnStatement(final AstNode result, final int line, final int columns) {
        addStatement(new Return(result, new Position(parent.getFile(), line, columns)));
        return this;
    }

    void addStatement(final AstNode statement) {
        statements.add(Validate.notNull(statement, "statement"));
    }

    public BlockBuilder end() {
        return parent;
    }

    public BinaryOperationBuilder binops() {
        return new BinaryOperationBuilder(this);
    }

    Collection<AstNode> getStatements() {
        return statements;
    }

    String getFile() {
        return parent.getFile();
    }
}
