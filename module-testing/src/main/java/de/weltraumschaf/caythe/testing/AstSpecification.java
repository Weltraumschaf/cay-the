package de.weltraumschaf.caythe.testing;

import java.util.Objects;

/**
 */
final class AstSpecification {
    private final String description;

    private final String given;

    private final String expectation;

    AstSpecification(final String description, final String given, final String expectation) {
        super();
        this.description = description;
        this.given = given;
        this.expectation = expectation;
    }

    String getDescription() {
        return description;
    }

    String getGiven() {
        return given;
    }

    String getExpectation() {
        return expectation;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof AstSpecification)) {
            return false;
        }

        final AstSpecification that = (AstSpecification) o;
        return Objects.equals(description, that.description) &&
            Objects.equals(given, that.given) &&
            Objects.equals(expectation, that.expectation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, given, expectation);
    }

    @Override
    public String toString() {
        return "AstSpecification{" +
            "description='" + description + '\'' +
            ", given='" + given + '\'' +
            ", expectation='" + expectation + '\'' +
            '}';
    }
}
