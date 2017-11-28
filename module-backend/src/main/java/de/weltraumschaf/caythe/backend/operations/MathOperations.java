package de.weltraumschaf.caythe.backend.operations;

import de.weltraumschaf.caythe.backend.types.*;

public final class MathOperations {

    public ObjectType add(final ObjectType left, final ObjectType right) {
        switch (left.type()) {
            case INTEGER:
                return new IntegerType(left.castToInteger().value() + right.castToInteger().value());
            case FLOAT:
                return new RealType(left.castToReal().value() + right.castToReal().value());
            case STRING:
                return new StringType(left.castToString().value() + right.castToString().value());
            default:
                throw new UnsupportedOperationException("Can't use type " + left.type() + " for addition!");
        }
    }

    public ObjectType subtract(final ObjectType left, final ObjectType right) {
        switch (left.type()) {
            case INTEGER:
                return new IntegerType(left.castToInteger().value() - right.castToInteger().value());
            case FLOAT:
                return new RealType(left.castToReal().value() - right.castToReal().value());
            default:
                throw new UnsupportedOperationException("Can't use type " + left.type() + " for addition!");
        }

    }

    public ObjectType multiply(final ObjectType left, final ObjectType right) {
        switch (left.type()) {
            case INTEGER:
                return new IntegerType(left.castToInteger().value() * right.castToInteger().value());
            case FLOAT:
                return new RealType(left.castToReal().value() * right.castToReal().value());
            default:
                throw new UnsupportedOperationException("Can't use type " + left.type() + " for addition!");
        }
    }

    public ObjectType divide(final ObjectType left, final ObjectType right) {
        switch (left.type()) {
            case INTEGER:
                return new IntegerType(left.castToInteger().value() / right.castToInteger().value());
            case FLOAT:
                return new RealType(left.castToReal().value() / right.castToReal().value());
            default:
                throw new UnsupportedOperationException("Can't use type " + left.type() + " for addition!");
        }
    }

    public ObjectType modulo(final ObjectType left, final ObjectType right) {
        switch (left.type()) {
            case INTEGER:
                return new IntegerType(left.castToInteger().value() % right.castToInteger().value());
            case FLOAT:
                return new RealType(left.castToReal().value() % right.castToReal().value());
            default:
                throw new UnsupportedOperationException("Can't use type " + left.type() + " for addition!");
        }
    }

    public ObjectType power(final ObjectType left, final ObjectType right) {
        return new RealType(Math.pow(left.castToReal().value(), right.castToReal().value()));
    }

    public ObjectType negate(final ObjectType operand) {
        if (operand.isOf(Type.INTEGER)) {
            return new IntegerType(operand.castToInteger().value() * -1L);
        } else if (operand.isOf(Type.FLOAT)) {
            return new RealType(operand.castToReal().value() * -1.0d);
        } else {
            throw new UnsupportedOperationException("Can't negate type " + operand.type() + "!");
        }
    }
}
