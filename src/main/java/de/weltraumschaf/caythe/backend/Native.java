package de.weltraumschaf.caythe.backend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

/**
 * Indicates methods which implements a native functionality.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Native {

    /**
     * Lists the return types.
     *
     * @return empty means void
     */
    String[] returnTypes() default {};
    /**
     * Lists the formal argument types.
     *
     * @return empty means void
     */
    String[] argumentTypes() default {};

}
