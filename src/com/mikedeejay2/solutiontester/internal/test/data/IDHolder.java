package com.mikedeejay2.solutiontester.internal.test.data;

import java.util.*;

public class IDHolder {
    private String id;
    private List<AnnotatedMethod> resultsMethods;
    private List<AnnotatedMethod> inputsMethods;
    private List<AnnotatedMethod> solutionMethods;

    public IDHolder(String id) {
        this.id = id;
        this.resultsMethods = new ArrayList<>();
        this.inputsMethods = new ArrayList<>();
        this.solutionMethods = new ArrayList<>();
    }

    public IDHolder addInputsMethod(AnnotatedMethod method) {
        this.inputsMethods.add(method);
        return this;
    }

    public IDHolder addInputsMethods(AnnotatedMethod... methods) {
        for(AnnotatedMethod method : methods) {
            addInputsMethod(method);
        }
        return this;
    }

    public IDHolder addResultsMethod(AnnotatedMethod method) {
        this.resultsMethods.add(method);
        return this;
    }

    public IDHolder addResultsMethods(AnnotatedMethod... methods) {
        for(AnnotatedMethod method : methods) {
            addResultsMethod(method);
        }
        return this;
    }

    public IDHolder addSolutionMethod(AnnotatedMethod method) {
        this.solutionMethods.add(method);
        return this;
    }

    public IDHolder addSolutionMethods(AnnotatedMethod... methods) {
        for(AnnotatedMethod method : methods) {
            addSolutionMethod(method);
        }
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
