package de.weltraumschaf.caythe.backend.operations;

public final class Operations {
    private LogicOperations logic = new LogicOperations();
    private MathOperations math = new MathOperations();
    private RelationOperations rel = new RelationOperations();

    public LogicOperations logic() {
        return logic;
    }

    public MathOperations math() {
        return math;
    }

    public RelationOperations rel() {
        return rel;
    }
}
