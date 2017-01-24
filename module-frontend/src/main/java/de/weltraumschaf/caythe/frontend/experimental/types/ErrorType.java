package de.weltraumschaf.caythe.frontend.experimental.types;

import java.util.Objects;

public final class ErrorType implements ObjectType {
    private final String message;

    public ErrorType(final String message) {
        super();
        this.message = message;
    }

    @Override
    public Type type() {
        return Type.ERROR;
    }

    @Override
    public String inspect() {
        return message;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ErrorType)) {
            return false;
        }

        final ErrorType errorType = (ErrorType) o;
        return Objects.equals(message, errorType.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "ErrorType{" +
            "message='" + message + '\'' +
            '}';
    }
}
