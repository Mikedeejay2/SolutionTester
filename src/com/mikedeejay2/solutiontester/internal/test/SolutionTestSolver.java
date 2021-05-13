package com.mikedeejay2.solutiontester.internal.test;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.test.data.AnnotatedMethod;
import com.mikedeejay2.solutiontester.internal.test.data.IDHolder;
import com.mikedeejay2.solutiontester.internal.test.data.TestResults;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SolutionTestSolver implements Supplier<TestResults> {
    private static final String ALL_IDENTIFIER = "%all%";

    private final SolutionTest test;
    private Class<? extends SolutionTest> solutionClass;
    private Method[] allMethods;
    private List<AnnotatedMethod> annotatedMethods;
    private Map<String, IDHolder> ids;

    public SolutionTestSolver(SolutionTest test) {
        this.test = test;
        this.solutionClass = null;
        this.allMethods = null;
        this.annotatedMethods = new ArrayList<>();
        this.ids = new HashMap<>();
    }

    @Override
    public TestResults get() {
        collectSolutionClass();
        collectAllMethods();
        collectAnnotatedMethods();
        collectIDs();

        return null;
    }

    private void validateID(String id)
    {
        if(!ids.containsKey(id)) ids.put(id, new IDHolder(id));
    }

    private void collectSolutionClass() {
        this.solutionClass = test.getClass();
    }

    private void collectAllMethods() {
        this.allMethods = solutionClass.getDeclaredMethods();
        for(Method method : allMethods) {
            if(!method.isAccessible()) {
                method.setAccessible(true);
            }
        }
    }

    private void collectAnnotatedMethods() {
        for(Method method : allMethods) {
            AnnotatedMethod annotatedMethod = new AnnotatedMethod(method);
            annotatedMethod.fillAnnotations();
            annotatedMethods.add(annotatedMethod);
        }
    }

    private void collectIDs() {
        for(AnnotatedMethod method : annotatedMethods) {
            if(method.isInputs()) {
                String id = method.getInputsID();
                validateID(id);
                ids.get(id).addInputsMethod(method);
            }
            if(method.isResults()) {
                String id = method.getResultsID();
                validateID(id);
                ids.get(id).addResultsMethod(method);
            }
            if(method.isSolution()) {
                String[] idArr = method.getSolutionIDs();
                boolean all = false;
                for(String id : idArr) {
                    if(id.equals(ALL_IDENTIFIER)) {
                        all = true;
                        break;
                    }
                }
                if(all) {
                    ids.values().forEach((holder) -> holder.addSolutionMethods(method));
                } else {
                    for(String id : idArr) {
                        validateID(id);
                        ids.get(id).addSolutionMethod(method);
                    }
                }
            }
        }
    }
}
