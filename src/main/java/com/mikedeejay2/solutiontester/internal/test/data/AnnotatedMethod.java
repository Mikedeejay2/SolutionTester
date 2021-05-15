package com.mikedeejay2.solutiontester.internal.test.data;

import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;
import com.sun.istack.internal.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotatedMethod {
    private final Method method;
    private boolean isInputs;
    private boolean isResults;
    private boolean isSolution;
    private int refCount;
    private Inputs inputsAnnotation;
    private Results resultsAnnotation;
    private Solution solutionAnnotation;
    private String inputsID;
    private String resultsID;
    private String[] solutionIDs;
    private boolean isFilled;

    public AnnotatedMethod(@NotNull Method method) {
        this.method = method;
        this.isInputs = false;
        this.isResults = false;
        this.isSolution = false;
        this.refCount = 0;
        this.inputsAnnotation = null;
        this.resultsAnnotation = null;
        this.solutionAnnotation = null;
        this.isFilled = false;
    }

    public AnnotatedMethod fillAnnotations() {
        inputsAnnotation = method.getAnnotation(Inputs.class);
        resultsAnnotation = method.getAnnotation(Results.class);
        solutionAnnotation = method.getAnnotation(Solution.class);
        if(inputsAnnotation != null) {
            ++refCount;
            isInputs = true;
            inputsID = inputsAnnotation.id;
        }
        if(resultsAnnotation != null) {
            ++refCount;
            isResults = true;
            resultsID = resultsAnnotation.id;
        }
        if(solutionAnnotation != null) {
            ++refCount;
            isSolution = true;
            solutionIDs = solutionAnnotation.ids;
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

    public Annotation getInputsAnnotation() {
        return inputsAnnotation;
    }

    public Annotation getResultsAnnotation() {
        return resultsAnnotation;
    }

    public Annotation getSolutionAnnotation() {
        return solutionAnnotation;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public String getInputsID() {
        return inputsID;
    }

    public String getResultsID() {
        return resultsID;
    }

    public String[] getSolutionIDs() {
        return solutionIDs;
    }
}
