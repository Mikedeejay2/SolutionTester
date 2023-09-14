package com.mikedeejay2.solutiontester;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define that a method is a solution that is designed to be tested with the {@link Inputs}
 * annotation and compared against the {@link Results} method.
 * <p>
 * Methods annotated with this annotation should never return void as the comparison test with the expected results
 * compares the return value of the solution method and the expected return value based off of the {@link Results}
 * annotation at the appropriate index in relation to the {@link Inputs} annotation index that was used to send
 * arguments to the solution method. The return type of the solution method should also match the data type held within
 * the returned array of the {@link Results} annotated method.
 * <p>
 * The amount of input arguments of the solution method should match the length of all sub-arrays in the {@link Inputs}
 * annotation such that <code>Inputs[0][0]</code> is the first argument of the first input and <code>Inputs[0][1]</code>
 * is the second argument of the second input, etc. Data types should also be matching at each input index according to
 * the data type requested by the solution method at the given argument index.
 * <p>
 * Example code, part of <a href="https://github.com/Mikedeejay2/SolutionTesterExample/blob/master/src/main/java/com/mikedeejay2/example/ExampleSolution.java">ExampleSolution</a>:
 * <pre>{@code
 *  &#064;Solution
 *  public int addNums(int a, int b) {
 *      return a + b;
 *  }
 * }</pre>
 *
 * @author Mikedeejay2
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Solution {
    /**
     * Optional IDs that can be specified if there are multiple different tests inside of the same class. By default,
     * solutions run through all ids with the special ID placeholder <code>%all%</code>.
     *
     * @return The IDs of this annotation
     * @since 1.0.0
     */
    String[] ids() default {"%all%"};
}
