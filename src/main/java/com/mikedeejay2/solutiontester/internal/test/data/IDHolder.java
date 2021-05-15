package com.mikedeejay2.solutiontester.internal.test.data;

import com.sun.istack.internal.NotNull;

import java.util.*;

public class IDHolder {
    private final String id;
    private final List<AnnotatedMethod> resultsMethods;
    private final List<AnnotatedMethod> inputsMethods;
    private final List<AnnotatedMethod> solutionMethods;
    private final List<Object[][]> inputs;
    private final List<Object[]> results;
    private final List<Object[]> solutions;

    public IDHolder(@NotNull String id) {
        this.id = id;
        this.resultsMethods = new ArrayList<>();
        this.inputsMethods = new ArrayList<>();
        this.solutionMethods = new ArrayList<>();
        this.inputs = new ArrayList<>();
        this.results = new ArrayList<>();
        this.solutions = new ArrayList<>();
    }

    public IDHolder addInputsMethod(@NotNull final AnnotatedMethod method) {
        this.inputsMethods.add(method);
        return this;
    }

    public IDHolder addResultsMethod(@NotNull final AnnotatedMethod method) {
        this.resultsMethods.add(method);
        return this;
    }

    public IDHolder addSolutionMethod(@NotNull final AnnotatedMethod method) {
        this.solutionMethods.add(method);
        return this;
    }

    public IDHolder addInputsMethods(@NotNull final AnnotatedMethod... methods) {
        for(AnnotatedMethod method : methods) {
            addInputsMethod(method);
        }
        return this;
    }

    public IDHolder addResultsMethods(@NotNull final AnnotatedMethod... methods) {
        for(AnnotatedMethod method : methods) {
            addResultsMethod(method);
        }
        return this;
    }

    public IDHolder addSolutionMethods(@NotNull final AnnotatedMethod... methods) {
        for(AnnotatedMethod method : methods) {
            addSolutionMethod(method);
        }
        return this;
    }

    public IDHolder addInputs(@NotNull final Object[][] object) {
        this.inputs.add(object);
        return this;
    }

    public IDHolder addResults(@NotNull final Object[] object) {
        this.results.add(object);
        return this;
    }

    public IDHolder addSolution(@NotNull final Object[] object) {
        this.solutions.add(object);
        return this;
    }

    public IDHolder addInputs(@NotNull final Object[][]... object) {
        for(Object[][] i : object) {
            addInputs(i);
        }
        return this;
    }

    public IDHolder addResults(@NotNull final Object[]... object) {
        for(Object[] i : object) {
            addResults(i);
        }
        return this;
    }

    public IDHolder addSolution(@NotNull final Object[]... object) {
        for(Object[] i : object) {
            addSolution(i);
        }
        return this;
    }

    public String getId() {
        return id;
    }

    public List<AnnotatedMethod> getResultsMethods() {
        return Collections.unmodifiableList(resultsMethods);
    }

    public List<AnnotatedMethod> getInputsMethods() {
        return Collections.unmodifiableList(inputsMethods);
    }

    public List<AnnotatedMethod> getSolutionMethods() {
        return Collections.unmodifiableList(solutionMethods);
    }

    public List<Object[][]> getInputs() {
        return Collections.unmodifiableList(inputs);
    }

    public List<Object[]> getResults() {
        return Collections.unmodifiableList(results);
    }

    public List<Object[]> getSolutions() {
        return Collections.unmodifiableList(solutions);
    }
}
