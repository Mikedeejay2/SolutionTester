package com.mikedeejay2.solutiontester.test.data;

import com.mikedeejay2.solutiontester.annotations.Inputs;
import com.mikedeejay2.solutiontester.annotations.Results;
import com.mikedeejay2.solutiontester.annotations.Solution;
import com.sun.istack.internal.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Holder for an annotated method. Contains extra information and data about the method including what annotation is
 * appended to it and its IDs.
 *
 * @author Mikedeejay2
 * @since 1.0.0
 */
public class AnnotatedMethod {
    /**
     * The original <code>Method</code>
     */
    protected final Method method;

    /**
     * Input IDs if {@link AnnotatedMethod#isInputs}
     * <p>
     * Empty if not {@link AnnotatedMethod#isFilled}
     */
    protected final List<String> inputsIDs;

    /**
     * Results IDs if {@link AnnotatedMethod#isResults}
     * <p>
     * Empty if not {@link AnnotatedMethod#isFilled}
     */
    protected final List<String> resultsIDs;

    /**
     * Solution IDs if {@link AnnotatedMethod#isSolution}
     * <p>
     * Empty if not {@link AnnotatedMethod#isFilled}
     */
    protected final List<String[]> solutionIDs;

    /**
     * If this <code>Method</code> is an {@link Inputs} annotated method
     * <p>
     * False if not {@link AnnotatedMethod#isFilled}
     */
    protected boolean isInputs;

    /**
     * If this <code>Method</code> is a {@link Results} annotated method
     * <p>
     * False if not {@link AnnotatedMethod#isFilled}
     */
    protected boolean isResults;

    /**
     * If this <code>Method</code> is a {@link Solution} annotated method
     * <p>
     * False if not {@link AnnotatedMethod#isFilled}
     */
    protected boolean isSolution;

    /**
     * The {@link Type} of this <code>Method</code>.
     * <p>
     * Null if not {@link AnnotatedMethod#isFilled}
     */
    protected @Nullable AnnotatedMethod.Type type;

    /**
     * The reference count of this <code>Method</code>
     */
    protected int refCount;

    /**
     * Whether the information for this method has been filled out yet through calling
     * {@link AnnotatedMethod#fillAnnotations()}
     */
    protected boolean isFilled;

    /**
     * Construct a new <code>AnnotatedMethod</code>
     *
     * @param method The method instance to reference
     */
    public AnnotatedMethod(@NotNull Method method) {
        Objects.requireNonNull(method, "Method cannot be null");
        this.method = method;
        this.inputsIDs = new ArrayList<>();
        this.resultsIDs = new ArrayList<>();
        this.solutionIDs = new ArrayList<>();
        this.isInputs = false;
        this.isResults = false;
        this.isSolution = false;
        this.refCount = 0;
        this.isFilled = false;
        this.type = null;
    }

    /**
     * Fill the information relating to annotations and IDs
     */
    public void fillAnnotations() {
        for(Annotation annotation : method.getDeclaredAnnotations()) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if(annotationType == Inputs.class) {
                ++refCount;
                isInputs = true;
                inputsIDs.add(((Inputs) annotation).id());
                type = Type.INPUTS;
            } else if(annotationType == Results.class) {
                ++refCount;
                isResults = true;
                resultsIDs.add(((Results) annotation).id());
                type = Type.RESULTS;
            } else if(annotationType == Solution.class) {
                ++refCount;
                isSolution = true;
                solutionIDs.add(((Solution) annotation).ids());
                type = Type.SOLUTION;
            }
        }
        this.isFilled = true;
    }

    /**
     * Get the original <code>Method</code>
     *
     * @return The original Method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Get whether this <code>Method</code> is an {@link Inputs} annotated method
     * <p>
     * False if not {@link AnnotatedMethod#isFilled}
     *
     * @return Whether this method is an inputs method
     */
    public boolean isInputs() {
        return isInputs;
    }

    /**
     * Get whether this <code>Method</code> is a {@link Results} annotated method
     * <p>
     * False if not {@link AnnotatedMethod#isFilled}
     *
     * @return Whether this method is a results method
     */
    public boolean isResults() {
        return isResults;
    }

    /**
     * Get whether this <code>Method</code> is a {@link Solution} annotated method
     * <p>
     * False if not {@link AnnotatedMethod#isFilled}
     *
     * @return Whether this method is a solution method
     */
    public boolean isSolution() {
        return isSolution;
    }

    /**
     * Get the reference count of this <code>Method</code>
     *
     * @return The reference count
     */
    public int getRefCount() {
        return refCount;
    }

    /**
     * Get whether the information for this method has been filled out yet through calling
     * {@link AnnotatedMethod#fillAnnotations()}
     *
     * @return Whether the information is filled
     */
    public boolean isFilled() {
        return isFilled;
    }

    /**
     * Get the {@link Type} of this <code>Method</code>.
     * <p>
     * Null if not {@link AnnotatedMethod#isFilled}
     *
     * @return The <code>SolverInputType</code>
     */
    public @Nullable AnnotatedMethod.Type getType() {
        return type;
    }

    /**
     * Input IDs if {@link AnnotatedMethod#isInputs}
     * <p>
     * Empty if not {@link AnnotatedMethod#isFilled}
     *
     * @return Inputs IDs
     */
    public List<String> getInputsIDs() {
        return inputsIDs;
    }

    /**
     * If this <code>Method</code> is a {@link Results} annotated method
     * <p>
     * False if not {@link AnnotatedMethod#isFilled}
     *
     * @return Results IDs
     */
    public List<String> getResultsIDs() {
        return resultsIDs;
    }

    /**
     * If this <code>Method</code> is a {@link Solution} annotated method
     * <p>
     * False if not {@link AnnotatedMethod#isFilled}
     *
     * @return Solution IDs
     */
    public List<String[]> getSolutionIDs() {
        return solutionIDs;
    }

    /**
     * Auto-generated <code>toString()</code> method for debugging
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString()
    {
        return "AnnotatedMethod{" +
            "method=" + method +
            ", inputsIDs=" + inputsIDs +
            ", resultsIDs=" + resultsIDs +
            ", solutionIDs=" + solutionIDs +
            ", isInputs=" + isInputs +
            ", isResults=" + isResults +
            ", isSolution=" + isSolution +
            ", type=" + type +
            ", refCount=" + refCount +
            ", isFilled=" + isFilled +
            '}';
    }

    /**
     * Enum for indicating what type of annotation an <code>AnnotatedMethod</code> is
     */
    public enum Type
    {
        INPUTS,
        RESULTS,
        SOLUTION,
        ;
    }
}
