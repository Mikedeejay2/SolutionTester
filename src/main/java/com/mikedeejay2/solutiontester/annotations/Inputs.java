package com.mikedeejay2.solutiontester.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define that a method returns the inputs that will be sent to the {@link Solution} annotated
 * methods.
 * <p>
 * Methods annotated with this annotation should return a 2D Object array <code>Object[][]</code> representing each
 * separate input and its arguments such that <code>Object[0]</code> would be the first input and
 * <code>Object[0][0]</code> is the first argument of the first input.
 * <p>
 * Array lengths of both the inputs and results method <strong>must be the same length</strong>, as differing lengths
 * would lead to impossible or inaccurate comparisons of solution tests and expected results. The logic between the
 * {@link Inputs} and {@link Results} method is that <code>Inputs[0] -&gt; Solutions[0] -&gt; Results[0]</code>.
 * <p>
 * The length of all sub-arrays (<i><code>Inputs[X][this.length]</code></i>) in the {@link Inputs} annotation should
 * match the amount of arguments in the solution methods such that <code>Inputs[0][0]</code> is the first argument of
 * the first input and <code>Inputs[0][1]</code> is the second argument of the second input, etc. Data types should
 * also be matching at each input index according to the data type requested by the solution method at the given
 * argument index.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Inputs {
    /**
     * Optional ID that can be specified if there are multiple different tests inside of the same class.
     *
     * @return The ID of this annotation
     */
    String id() default "default";
}
