package de.weltraumschaf.caythe.intermediate;

/**
 * Implementations can probe equivalence of itself to a given other object of same type.
 * 
 * @param <T> probed object type
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Equivalence<T> {
    /**
     * Probes equivalence of itself against an other node and collects all
     * errors in the passed {@link Notification} object.
     *
     * @param other  Node to compare against.
     * @param result Object which collects all equivalence violations.
     */
    void probeEquivalence(T other, Notification result);
}
