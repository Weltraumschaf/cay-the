package de.weltraumschaf.caythe;

/**
 */
final class ByteCodeVisitor extends CayTheBaseVisitor<Programm> {

    @Override
    protected Programm defaultResult() {
        return Programm.EMPTY;
    }

}
