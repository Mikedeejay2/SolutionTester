package com.mikedeejay2.solutiontester.internal.test.data;

import com.sun.istack.internal.NotNull;

import java.util.*;

public class IDHolder {
    private final String id;
    private List<AnnotatedMethod> resultsMethods;
    private List<AnnotatedMethod> inputsMethods;
    private List<AnnotatedMethod> solutionMethods;

    public IDHolder(@NotNull String id) {
        this.id = id;
        this.resultsMethods = new ArrayList<>();
        this.inputsMethods = new ArrayList<>();
        this.solutionMethods = new ArrayList<>();
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

    public String getId() {
        return id;
    }

    public List<AnnotatedMethod> getResultsMethods() {
        return resultsMethods;
    }

    public List<AnnotatedMethod> getInputsMethods() {
        return inputsMethods;
    }

    public List<AnnotatedMethod> getSolutionMethods() {
        return solutionMethods;
    }
}
