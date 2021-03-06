package de.weltraumschaf.caythe.intermediate.equivalence;

import de.weltraumschaf.caythe.intermediate.model.Describable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Implementations can probe equivalence of itself to a given other object of same type.
 *
 * @param <T> probed object type
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @since 1.0.0
 */
public interface Equivalence<T extends Describable> extends Describable {
    /**
     * Probes equivalence of itself against an other node and collects all
     * errors in the passed {@link Notification} object.
     *
     * @param other  Node to compare against.
     * @param result Object which collects all equivalence violations.
     */
    void probeEquivalence(T other, Notification result);

    @SuppressWarnings("unchecked")
    default <R extends Describable> void probeEquivalenceFor(final Class<R> wanted, final T other, final Notification result, final Consumer<R> callback) {
        if (probeEquivalentType(wanted, other, result)) {
            callback.accept((R) other);
        }
    }

    default <R extends Describable> boolean probeEquivalentType(final Class<R> wanted, final T other, final Notification result) {
        if (wanted.isAssignableFrom(other.getClass())) {
            return true;
        } else {
            result.error(difference("Probed node types",
                new ResultDescriber().nodeTypeMismatch(this, other)));
            return false;
        }
    }

    default <R extends Equivalence> void probeEquivalences(final Class<R> wanted, final List<R> a, final List<R> b, final Notification result) {
        final ResultDescriber describer = new ResultDescriber();

        if (isNotSameSize(a, b)) {
            result.error(
                difference(
                    "Value count",
                    describer.valueCountMismatch(a, b)),
                a.size(), b.size()
            );
        }

        int i = 0;

        for (final R value : a) {
            try {
                value.probeEquivalence(b.get(i), result);
            } catch (final IndexOutOfBoundsException ex) {
                result.error(
                    difference("Expected value",
                    "Other has not the expected value at index " + i));
            }

            i++;
        }
    }

    default String difference(final String what, final String format) {
        return what + " differ: " + format;
    }

    default boolean isNotEqual(final Object a, final Object b) {
        return !Objects.equals(a, b);
    }

    default boolean isNotSameSize(final Collection<?> a, final Collection<?> b) {
        return a.size() != b.size();
    }

    default boolean isNotSameSize(final Map<?, ?> a, final Map<?, ?> b) {
        return a.size() != b.size();
    }
}
