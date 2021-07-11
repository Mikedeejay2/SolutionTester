package com.mikedeejay2.solutiontester.internal.data;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Holds all data of an ID.
 *
 * @author Mikedeejay2
 * @since 1.0.0
 */
public class IDHolder {
    /**
     * The ID of this <code>IDHolder</code>
     */
    protected final String id;

    /**
     * The list of result {@link AnnotatedMethod}s of this ID
     */
    protected final List<AnnotatedMethod> resultsMethods;

    /**
     * The list of input {@link AnnotatedMethod}s of this ID
     */
    protected final List<AnnotatedMethod> inputsMethods;

    /**
     * The list of solution {@link AnnotatedMethod}s of this ID
     */
    protected final List<AnnotatedMethod> solutionMethods;

    /**
     * List of inputs for this ID
     */
    protected final List<Object[][]> inputs;

    /**
     * List of results for this ID
     */
    protected final List<Object[]> results;

    /**
     * List of solutions for this ID
     */
    protected final List<Object> solutions;

    /**
     * The execution time of all solutions in nanoseconds
     */
    protected long timeNanos;

    /**
     * Constructs a new <code>IDHolder</code>
     *
     * @param id The ID
     */
    public IDHolder(@NotNull String id) {
        this.id = id;
        this.resultsMethods = new ArrayList<>();
        this.inputsMethods = new ArrayList<>();
        this.solutionMethods = new ArrayList<>();
        this.inputs = new ArrayList<>();
        this.results = new ArrayList<>();
        this.solutions = new ArrayList<>();
        this.timeNanos = 0;
    }

    /**
     * Add an inputs method to this ID
     *
     * @param method The {@link AnnotatedMethod} to add
     * @return This ID instance
     */
    public IDHolder addInputsMethod(@NotNull final AnnotatedMethod method) {
        this.inputsMethods.add(method);
        return this;
    }

    /**
     * Add a results method to this ID
     *
     * @param method The {@link AnnotatedMethod} to add
     * @return This ID instance
     */
    public IDHolder addResultsMethod(@NotNull final AnnotatedMethod method) {
        this.resultsMethods.add(method);
        return this;
    }

    /**
     * Add a solution method to this ID
     *
     * @param method The {@link AnnotatedMethod} to add
     * @return This ID instance
     */
    public IDHolder addSolutionMethod(@NotNull final AnnotatedMethod method) {
        this.solutionMethods.add(method);
        return this;
    }

    /**
     * Add multiple inputs methods to this ID
     *
     * @param methods The {@link AnnotatedMethod}s to add
     * @return This ID instance
     */
    public IDHolder addInputsMethods(@NotNull final AnnotatedMethod... methods) {
        for(AnnotatedMethod method : methods) {
            addInputsMethod(method);
        }
        return this;
    }

    /**
     * Add multiple inputs methods to this ID
     *
     * @param methods The {@link AnnotatedMethod}s to add
     * @return This ID instance
     */
    public IDHolder addResultsMethods(@NotNull final AnnotatedMethod... methods) {
        for(AnnotatedMethod method : methods) {
            addResultsMethod(method);
        }
        return this;
    }

    /**
     * Add multiple solution methods to this ID
     *
     * @param methods The {@link AnnotatedMethod}s to add
     * @return This ID instance
     */
    public IDHolder addSolutionMethods(@NotNull final AnnotatedMethod... methods) {
        for(AnnotatedMethod method : methods) {
            addSolutionMethod(method);
        }
        return this;
    }

    /**
     * Add inputs to this ID
     *
     * @param object The inputs to add
     * @return This ID instance
     */
    public IDHolder addInputs(@NotNull final Object[][] object) {
        this.inputs.add(object);
        return this;
    }

    /**
     * Add results to this ID
     *
     * @param object The results to add
     * @return This ID instance
     */
    public IDHolder addResults(@NotNull final Object[] object) {
        this.results.add(object);
        return this;
    }

    /**
     * Add a solution to this ID
     *
     * @param object The solution to add
     * @return This ID instance
     */
    public IDHolder addSolution(@NotNull final Object object) {
        this.solutions.add(object);
        return this;
    }

    /**
     * Add multiple inputs to this ID
     *
     * @param object The inputs to add
     * @return This ID instance
     */
    public IDHolder addInputs(@NotNull final Object[][]... object) {
        for(Object[][] i : object) {
            addInputs(i);
        }
        return this;
    }

    /**
     * Add multiple results to this ID
     *
     * @param object The inputs to add
     * @return This ID instance
     */
    public IDHolder addResults(@NotNull final Object[]... object) {
        for(Object[] i : object) {
            addResults(i);
        }
        return this;
    }

    /**
     * Add multiple solutions to this ID
     *
     * @param object The inputs to add
     * @return This ID instance
     */
    public IDHolder addSolution(@NotNull final Object... object) {
        for(Object i : object) {
            addSolution(i);
        }
        return this;
    }

    /**
     * Add execution time in nanoseconds to the existing execution time
     *
     * @param time The time to add to the existing execution time
     */
    public void addTimeNanos(long time) {
        timeNanos += time;
    }

    /**
     * Set the execution time in nanoseconds
     *
     * @param time The time in nanoseconds
     */
    public void setTimeNanos(long time) {
        this.timeNanos = time;
    }

    /**
     * Get the execution time in nanoseconds
     *
     * @return The time in nanoseconds
     */
    public long getTimeNanos() {
        return timeNanos;
    }

    /**
     * Get the execution time in milliseconds
     *
     * @return The time in milliseconds
     */
    public double getTimeAsMS() {
        return Math.round(timeNanos / 1000000D * 1000D) / 1000D;
    }

    /**
     * Get the ID of this <code>IDHolder</code>
     *
     * @return The ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get an unmodifiable list of results methods
     *
     * @return An unmodifiable list of results methods
     */
    public List<AnnotatedMethod> getResultsMethods() {
        return Collections.unmodifiableList(resultsMethods);
    }

    /**
     * Get an unmodifiable list of inputs methods
     *
     * @return An unmodifiable list of inputs methods
     */
    public List<AnnotatedMethod> getInputsMethods() {
        return Collections.unmodifiableList(inputsMethods);
    }

    /**
     * Get an unmodifiable list of solution methods
     *
     * @return An unmodifiable list of solution methods
     */
    public List<AnnotatedMethod> getSolutionMethods() {
        return Collections.unmodifiableList(solutionMethods);
    }

    /**
     * Get an unmodifiable list of inputs
     *
     * @return An unmodifiable list of inputs
     */
    public List<Object[][]> getInputs() {
        return Collections.unmodifiableList(inputs);
    }

    /**
     * Get an unmodifiable list of results
     *
     * @return An unmodifiable list of results
     */
    public List<Object[]> getResults() {
        return Collections.unmodifiableList(results);
    }


    /**
     * Get an unmodifiable list of solutions
     *
     * @return An unmodifiable list of solutions
     */
    public List<Object> getSolutions() {
        return Collections.unmodifiableList(solutions);
    }

    /**
     * Auto-generated <code>toString()</code> method for debugging
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString()
    {
        return "IDHolder{" +
            "id='" + id + '\'' +
            ", resultsMethods=" + resultsMethods +
            ", inputsMethods=" + inputsMethods +
            ", solutionMethods=" + solutionMethods +
            ", inputs=" + inputs +
            ", results=" + results +
            ", solutions=" + solutions +
            ", timeNanos=" + timeNanos +
            '}';
    }
}
