package de.weltraumschaf.caythe.cli;

import com.beust.jcommander.Parameter;
import de.weltraumschaf.commons.validate.Validate;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Helper classes to generate a usage string for classes annotated with {@link com.beust.jcommander.Parameter}.
 */
final class UsageBuilder {

    static final char SEPARATOR = ' ';

    static String generate(final Class toInspect) {
        Validate.notNull(toInspect, "toInspect");
        final StringBuilder required = new StringBuilder();
        final StringBuilder optional = new StringBuilder();

        for (final Field f : findAllParameters(toInspect)) {
            final Parameter parameter = f.getAnnotation(Parameter.class);

            if (null == parameter) {
                continue;
            }

            if (parameter.required()) {
                required
                    .append(SEPARATOR)
                    .append(joinParameterNames(parameter.names()));
            } else {
                optional
                    .append(SEPARATOR)
                    .append('[')
                    .append(joinParameterNames(parameter.names()))
                    .append(']');
            }
        }

        final String usage = required.toString().trim() + SEPARATOR + optional.toString().trim();
        return usage.trim();
    }

    private static String joinParameterNames(final String[] names) {
        return Arrays.stream(names).map(String::toString).collect(Collectors.joining("|"));
    }

    static Collection<Field> findAllParameters(final Class toInspect) {
        Validate.notNull(toInspect, "toInspect");
        return Arrays.stream(toInspect.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Parameter.class))
            .collect(Collectors.toList());
    }
}
