package de.weltraumschaf.caythe.testing;

import java.nio.file.Path;
import java.util.Objects;

/**
 */
public final class AstSpecification {
    private final String description;
    private final String given;
    private final String expectation;
    private final Path file;

    public AstSpecification(final String description, final String given, final String expectation, final Path file) {
        super();
        this.description = description;
        this.given = given;
        this.expectation = expectation;
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public String getGiven() {
        return given;
    }

    public String getExpectation() {
        return expectation;
    }

    public Path getFile() {
        return file;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof AstSpecification)) {
            return false;
        }

        final AstSpecification that = (AstSpecification) o;
        return Objects.equals(description, that.description) &&
            Objects.equals(given, that.given) &&
            Objects.equals(expectation, that.expectation) &&
            Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, given, expectation, file);
    }

    @Override
    public String toString() {
        return "AstSpecification{" +
            "description='" + description + '\'' +
            ", given='" + given + '\'' +
            ", expectation='" + expectation + '\'' +
            ", file=" + file +
            '}';
    }
}
