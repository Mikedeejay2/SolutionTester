package com.mikedeejay2.solutiontester;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define that a method returns the expected results that will be compared to the the
 * {@link Solution} annotated methods.
 * <p>
 * Methods annotated with this annotation should return an Object array <code>Object[]</code> representing each
 * separate result such that <code>Object[0]</code> would be the first result, <code>Object[1]</code> would be the
 * second result.
 * <p>
 * Array lengths of both the inputs and results method <strong>must be the same length</strong>, as differing lengths
 * would lead to impossible or inaccurate comparisons of solution tests and expected results. The logic between the
 * {@link Inputs} and {@link Results} method is that <code>Inputs[0] -&gt; Solutions[0] -&gt; Results[0]</code>.
 * <p>
 * The data type of the returned Objects held in the <code>Object[]</code> array should match the return data type of the
 * solution method
 * <p>
 * Example code, part of <a href="https://github.com/Mikedeejay2/SolutionTesterExample/blob/master/src/main/java/com/mikedeejay2/example/ExampleSolution.java">ExampleSolution</a>:
 * <pre>
 *  &#064;Results
 *  public Object[] results() {
 *      return new Object[]{ 4, 3, 10 };
 *  }
 * </pre>
 *
 * @author Mikedeejay2
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Results {
    /**
     * Optional ID that can be specified if there are multiple different tests inside of the same class.
     *
     * @return The ID of this annotation
     * @since 1.0.0
     */
    String id() default "default";
}
