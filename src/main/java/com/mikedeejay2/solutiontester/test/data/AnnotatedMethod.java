package com.mikedeejay2.solutiontester.test.data;

import com.mikedeejay2.solutiontester.annotations.Inputs;
import com.mikedeejay2.solutiontester.annotations.Results;
import com.mikedeejay2.solutiontester.annotations.Solution;
import com.mikedeejay2.solutiontester.test.SolverInputType;
import com.sun.istack.internal.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotatedMethod {
    private final Method method;
    private final List<String> inputsIDs;
    private final List<String> resultsIDs;
    private final List<String[]> solutionIDs;
    private boolean isInputs;
    private boolean isResults;
    private boolean isSolution;
    private SolverInputType type;
    private int refCount;
    private boolean isFilled;

    public AnnotatedMethod(@NotNull Method method) {
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

    public AnnotatedMethod fillAnnotations() {
        for(Annotation annotation : method.getDeclaredAnnotations()) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if(annotationType == Inputs.class) {
                ++refCount;
                isInputs = true;
                inputsIDs.add(((Inputs) annotation).id());
                type = SolverInputType.INPUTS;
            } else if(annotationType == Results.class) {
                ++refCount;
                isResults = true;
                resultsIDs.add(((Results) annotation).id());
                type = SolverInputType.RESULTS;
            } else if(annotationType == Solution.class) {
                ++refCount;
                isSolution = true;
                solutionIDs.add(((Solution) annotation).ids());
                type = SolverInputType.SOLUTION;
            }
        }
        this.isFilled = true;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public boolean isInputs() {
        return isInputs;
    }

    public boolean isResults() {
        return isResults;
    }

    public boolean isSolution() {
        return isSolution;
    }

    public int getRefCount() {
        return refCount;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public SolverInputType getType() {
        return type;
    }

    public List<String> getInputsIDs() {
        return inputsIDs;
    }

    public List<String> getResultsIDs() {
        return resultsIDs;
    }

    public List<String[]> getSolutionIDs() {
        return solutionIDs;
    }
}
