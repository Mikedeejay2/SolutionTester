package com.mikedeejay2.solutiontester.internal.test;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.test.data.AnnotatedMethod;
import com.mikedeejay2.solutiontester.internal.test.data.IDHolder;
import com.mikedeejay2.solutiontester.internal.test.data.TestResults;
import com.mikedeejay2.solutiontester.internal.util.SolveUtils;
import com.sun.istack.internal.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Supplier;

public class SolutionTestSolver implements Supplier<TestResults> {
    private static final String ALL_IDENTIFIER = "%all%";

    private final SolutionTest test;
    private Class<? extends SolutionTest> solutionClass;
    private List<AnnotatedMethod> annotatedMethods;
    private Map<String, IDHolder> ids;

    public SolutionTestSolver(@NotNull SolutionTest test) {
        this.test = test;
        this.solutionClass = null;
        this.annotatedMethods = new ArrayList<>();
        this.ids = new HashMap<>();
    }

    @Override
    public TestResults get() {
        try {
            return solve();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private TestResults solve() throws InvocationTargetException, IllegalAccessException {
        // COLLECTION STAGE 1
        collectSolutionClass();
        collectAnnotatedMethods();
        collectIDs();
        // VALIDATION STAGE 1
        validateAnnotations();
        validateMethods();
        validateSingleResults();
        validateNoMissing();
        // COLLECTION STAGE 2
        collectInputs();
        collectResults();
        // VALIDATION STAGE 2
        validateInputsLength();
        validateReturnTypes();
        validateParameterTypes();

        return new TestResults(true);
    }

    private void collectSolutionClass() {
        this.solutionClass = test.getClass();
    }

    private void collectAnnotatedMethods() {
        for(Method method : solutionClass.getDeclaredMethods()) {
            if(!method.isAccessible()) method.setAccessible(true);
            AnnotatedMethod annotatedMethod = new AnnotatedMethod(method);
            annotatedMethod.fillAnnotations();
            if(annotatedMethod.getRefCount() == 0) continue;
            annotatedMethods.add(annotatedMethod);
        }
    }

    private void collectIDs() {
        for(AnnotatedMethod method : annotatedMethods) {
            if(method.isInputs()) {
                List<String> idList = method.getInputsIDs();
                addIDIfNotPresent(method, idList, SolverInputType.INPUTS);
            }
            if(method.isResults()) {
                List<String> idList = method.getResultsIDs();
                addIDIfNotPresent(method, idList, SolverInputType.RESULTS);
            }
        }
        for(AnnotatedMethod method : annotatedMethods) {
            if(method.isSolution()) {
                List<String[]> idList = method.getSolutionIDs();
                boolean all = false;
                idLoop:
                for(String[] idArr : idList) {
                    for(String id : idArr) {
                        if(id.equals(ALL_IDENTIFIER)) {
                            all = true;
                            break idLoop;
                        }
                    }
                }
                if(all) {
                    for(IDHolder holder : ids.values()) {
                        holder.addSolutionMethod(method);
                    }
                } else {
                    for(String[] idArr : idList) {
                        addIDIfNotPresent(method, idArr, SolverInputType.SOLUTION);
                    }
                }
            }
        }
    }

    private void validateAnnotations() {
        for(AnnotatedMethod method : annotatedMethods) {
            int test = 0;
            if(method.isInputs()) ++test;
            if(method.isResults()) ++test;
            if(method.isSolution()) ++test;
            if(test > 1) {
                throw new IllegalArgumentException(String.format(
                    "The method \"%s\" has too many type annotations in class \"%s\"",
                    method.getMethod().getName(), solutionClass.getName()
                                                                ));
            }
        }
    }

    private void validateMethods() {
        for(AnnotatedMethod annotatedMethod : annotatedMethods) {
            Method method = annotatedMethod.getMethod();
            Class<?> returnType = method.getReturnType();
            Class<?>[] parameterTypes = method.getParameterTypes();
            switch(annotatedMethod.getType()) {
                case INPUTS:
                    if(parameterTypes.length > 0) {
                        throw new IllegalArgumentException(String.format(
                            "The input method \"%s\" can not take parameters in class \"%s\"",
                            annotatedMethod.getMethod().getName(), solutionClass.getName()
                                                                        ));
                    }
                    if(!(returnType.isArray() && returnType.getComponentType().isArray())) {
                        throw new IllegalArgumentException(String.format(
                            "The input method \"%s\" does not have the correct return type in class \"%s\"",
                            annotatedMethod.getMethod().getName(), solutionClass.getName()
                                                                        ));
                    }
                    break;
                case RESULTS:
                    if(parameterTypes.length > 0) {
                        throw new IllegalArgumentException(String.format(
                            "The result method \"%s\" can not take parameters in class \"%s\"",
                            annotatedMethod.getMethod().getName(), solutionClass.getName()
                                                                        ));
                    }
                    if(!returnType.isArray()) {
                        throw new IllegalArgumentException(String.format(
                            "The input method \"%s\" does not have the correct return type in class \"%s\"",
                            annotatedMethod.getMethod().getName(), solutionClass.getName()
                                                                        ));
                    }
                    break;
            }
        }
    }

    private void validateSingleResults() {
        for(IDHolder holder : ids.values()) {
            if(holder.getResults().size() > 1) {
                throw new IllegalArgumentException(String.format(
                    "The ID \"%s\" has multiple results methods in class \"%s\"",
                    holder.getId(), solutionClass.getName()
                                                                ));
            }
        }
    }

    private void validateNoMissing(){
        if(ids.isEmpty()) {
            throw new IllegalArgumentException(String.format(
                "No IDs exist in class \"%s\"",
                solutionClass.getName()
                                                            ));
        }
        for(IDHolder holder : ids.values()) {
            if(holder.getInputsMethods().isEmpty()) {
                throw new IllegalArgumentException(String.format(
                    "The ID \"%s\" has no input methods in class \"%s\"",
                    holder.getId(), solutionClass.getName()
                                                                ));
            }
            if(holder.getResultsMethods().isEmpty()) {
                throw new IllegalArgumentException(String.format(
                    "The ID \"%s\" has no result methods in class \"%s\"",
                    holder.getId(), solutionClass.getName()
                                                                ));
            }
            if(holder.getSolutionMethods().isEmpty()) {
                throw new IllegalArgumentException(String.format(
                    "The ID \"%s\" has no solution methods in class \"%s\"",
                    holder.getId(), solutionClass.getName()
                                                                ));
            }
        }
    }

    private void collectInputs() throws InvocationTargetException, IllegalAccessException {
        for(IDHolder holder : ids.values()) {
            for(AnnotatedMethod annotatedMethod : holder.getInputsMethods()) {
                Object[][] result = (Object[][]) annotatedMethod.getMethod().invoke(test);
                holder.addInputs(result);
            }
        }
    }

    private void collectResults() throws InvocationTargetException, IllegalAccessException {
        for(IDHolder holder : ids.values()) {
            for(AnnotatedMethod annotatedMethod : holder.getResultsMethods()) {
                Object[] result = (Object[]) annotatedMethod.getMethod().invoke(test);
                holder.addResults(result);
            }
        }
    }

    private void validateInputsLength() {
        for(IDHolder holder : ids.values()) {
            Object[] results = holder.getResults().get(0);
            List<Object[][]> inputs = holder.getInputs();
            int previousLength = -1;
            for(Object[][] input2d : inputs) {
                if(input2d == null) {
                    throw new IllegalArgumentException(String.format(
                        "The ID \"%s\" has an input that returned null in class \"%s\"",
                        holder.getId(), solutionClass.getName()
                                                                    ));
                }
                if(input2d.length != results.length) {
                    throw new IllegalArgumentException(String.format(
                        "The ID \"%s\" has a set of inputs that does not set the size of the results in class \"%s\"",
                        holder.getId(), solutionClass.getName()
                                                                    ));
                }
                for(Object[] input1d : input2d) {
                    if(input1d == null) {
                        throw new IllegalArgumentException(String.format(
                            "The ID \"%s\" has a sub-input that returned null in class \"%s\"",
                            holder.getId(), solutionClass.getName()
                                                                        ));
                    }
                    if(previousLength == -1) previousLength = input1d.length;
                    if(input1d.length != previousLength) {
                        throw new IllegalArgumentException(String.format(
                            "The ID \"%s\" has a set of inputs that does not set the size of the results in class \"%s\"",
                            holder.getId(), solutionClass.getName()
                                                                        ));
                    }
                }
            }
        }
    }

    private void validateReturnTypes() {
        for(IDHolder holder : ids.values()) {
            Object[] results = holder.getResults().get(0);
            Class<?> solutionReturnType = null;
            for(AnnotatedMethod method : holder.getSolutionMethods()) {
                if(solutionReturnType == null) solutionReturnType = method.getMethod().getReturnType();
                if(solutionReturnType != method.getMethod().getReturnType()) {
                    throw new IllegalArgumentException(String.format(
                        "The ID \"%s\" has mismatched solution return types in class \"%s\"",
                        holder.getId(), solutionClass.getName()
                                                                    ));
                }
                for(Object result : results) {
                    if(result == null) {
                        if(solutionReturnType.isPrimitive()) {
                            throw new IllegalArgumentException(String.format(
                                "The ID \"%s\" has a result with an invalid null parameter type in class \"%s\"",
                                holder.getId(), solutionClass.getName()
                                                                            ));
                        }
                        continue;
                    }
                    Class<?> resultClass = result.getClass();
                    if(solutionReturnType.isArray() != resultClass.isArray()) {
                        throw new IllegalArgumentException(String.format(
                            "The ID \"%s\" has mismatched solution and result array return types in class \"%s\"",
                            holder.getId(), solutionClass.getName()
                                                                        ));
                    } else if(!SolveUtils.isInstance(solutionReturnType, resultClass)) {
                        throw new IllegalArgumentException(String.format(
                            "The ID \"%s\" has a result type that does not match the return type in class \"%s\"",
                            holder.getId(), solutionClass.getName()
                                                                        ));
                    }
                }
            }
        }
    }

    private void validateParameterTypes() {
        for(IDHolder holder : ids.values()) {
            Class<?>[] solutionParameterTypes = null;
            for(AnnotatedMethod solutionAnnoMethod : holder.getSolutionMethods()) {
                Method solutionMethod = solutionAnnoMethod.getMethod();
                Class<?>[] curParameterTypes = solutionMethod.getParameterTypes();
                if(solutionParameterTypes == null) solutionParameterTypes = curParameterTypes;
                if(curParameterTypes.length != solutionParameterTypes.length) {
                    throw new IllegalArgumentException(String.format(
                        "The ID \"%s\" has solutions with different parameter lengths in class \"%s\"",
                        holder.getId(), solutionClass.getName()
                                                                    ));
                }
                for(int i = 0; i < solutionParameterTypes.length; ++i) {
                    Class<?> solutionParamType = solutionParameterTypes[i];
                    Class<?> curParamType = curParameterTypes[i];
                    if(solutionParamType != curParamType) {
                        throw new IllegalArgumentException(String.format(
                            "The ID \"%s\" has mismatched parameter types for solutions in class \"%s\"",
                            holder.getId(), solutionClass.getName()
                                                                        ));
                    }
                }
            }
            for(Object[][] input2D : holder.getInputs()) {
                for(Object[] input1D : input2D) {
                    if(input1D.length != solutionParameterTypes.length) {
                        throw new IllegalArgumentException(String.format(
                            "The ID \"%s\" has an input with invalid parameter length in class \"%s\"",
                            holder.getId(), solutionClass.getName()
                                                                        ));
                    }
                    for(int i = 0; i < input1D.length; ++i) {
                        Class<?> expectedType = solutionParameterTypes[i];
                        Object input = input1D[i];
                        if(input == null) {
                            if(expectedType.isPrimitive()) {
                                throw new IllegalArgumentException(String.format(
                                    "The ID \"%s\" has an input with invalid null parameter type in class \"%s\"",
                                    holder.getId(), solutionClass.getName()
                                                                                ));
                            }
                            continue;
                        }
                        if(!SolveUtils.isInstance(expectedType, input.getClass())) {
                            throw new IllegalArgumentException(String.format(
                                "The ID \"%s\" has an input with invalid parameter types in class \"%s\"",
                                holder.getId(), solutionClass.getName()
                                                                            ));
                        }
                    }
                }
            }
        }
    }

    private void addIDIfNotPresent(AnnotatedMethod method, List<String> idList, SolverInputType type) {
        for(String id : idList) {
            addIDIfNotPresent(method, id, type);
        }
    }

    private void addIDIfNotPresent(AnnotatedMethod method, String[] idArr, SolverInputType type) {
        for(String id : idArr) {
            addIDIfNotPresent(method, id, type);
        }
    }

    private void addIDIfNotPresent(AnnotatedMethod method, String id, SolverInputType type) {
        validateID(id);
        IDHolder holder = ids.get(id);
        switch(type) {
            case INPUTS:
                holder.addInputsMethod(method);
                break;
            case RESULTS:
                holder.addResultsMethod(method);
                break;
            case SOLUTION:
                holder.addSolutionMethod(method);
                break;
        }
    }

    private void validateID(String id) {
        if(!ids.containsKey(id)) {
            ids.put(id, new IDHolder(id));
        }
    }
}
