package com.mikedeejay2.solutiontester.test;

import com.mikedeejay2.solutiontester.SolutionTest;
import com.mikedeejay2.solutiontester.test.data.AnnotatedMethod;
import com.mikedeejay2.solutiontester.test.data.IDHolder;
import com.mikedeejay2.solutiontester.test.data.TestResults;
import com.mikedeejay2.solutiontester.util.SolveUtils;
import com.sun.istack.internal.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Supplier;

public class SolutionTestSolver implements Supplier<TestResults> {
    private static final String ALL_IDENTIFIER = "%all%";

    private final SolutionTest test;
    private Class<? extends SolutionTest> solutionClass;
    private final List<AnnotatedMethod> annotatedMethods;
    private final Map<String, IDHolder> ids;

    public SolutionTestSolver(@NotNull SolutionTest test) {
        this.test = test;
        this.solutionClass = null;
        this.annotatedMethods = new ArrayList<>();
        this.ids = new LinkedHashMap<>();
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
        for(AnnotatedMethod method : annotatedMethods) {
            validateAnnotations(method);
            validateMethods(method);
        }
        validateIDsFilled();

        for(IDHolder holder : ids.values()) {
            validateSingleResults(holder);
            validateNoMissing(holder);
            // COLLECTION STAGE 2
            collectInputs(holder);
            collectResults(holder);
            // VALIDATION STAGE 2
            validateInputsLength(holder);
            validateReturnTypes(holder);
            validateParameterTypes(holder);
            // COLLECTION STAGE 3
            collectSolutions(holder);
        }

        return toTestResults();
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

    private void validateAnnotations(AnnotatedMethod method) {
        int test = 0;
        if(method.isInputs()) ++test;
        if(method.isResults()) ++test;
        if(method.isSolution()) ++test;
        if(test > 1) {
            StringBuilder message = new StringBuilder();
            message.append(String.format(
                "The method \"%s\" has too many type annotations in class \"%s\"",
                method.getMethod().getName(), solutionClass.getName()));
            if(test == 2) {
                message.append("\nThe method has ");
                int flag = 0;
                if(method.isInputs()) {
                    ++flag;
                    message.append("an inputs annotation and");
                }
                if(method.isResults()) {
                    ++flag;
                    if(flag == 1) {
                        message.append(" a results annotation and");
                    } else {
                        message.append(" a results annotation");
                    }
                }
                if(method.isSolution()) {
                    message.append(" a solution annotation");
                }
            }
            throw new IllegalArgumentException(message.toString());
        }
    }

    private void validateMethods(AnnotatedMethod annotatedMethod) {
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
                        "The input method \"%s\" does not have the correct return type in class \"%s\"" +
                        "\nInput methods can only return a 2D array of objects (Object[][]), each index being a list of " +
                        "parameters to send to the solution methods",
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
                        "The input method \"%s\" does not have the correct return type in class \"%s\"" +
                        "\nResult methods can only return an array of objects (Object[]), each index being an expected " +
                        "result of the solution methods",
                        annotatedMethod.getMethod().getName(), solutionClass.getName()
                                                                    ));
                }
                break;
        }
    }

    private void validateIDsFilled() {
        if(ids.isEmpty()) {
            throw new IllegalArgumentException(String.format(
                "No IDs exist in class \"%s\", this is usually caused by attempting to run a unit test on a class with " +
                "no testing methods",
                solutionClass.getName()
                                                            ));
        }
    }

    private void validateSingleResults(IDHolder holder) {
        if(holder.getResults().size() > 1) {
            throw new IllegalArgumentException(String.format(
                "The ID \"%s\" has multiple results methods in class \"%s\"" +
                "\nAn ID can only have a single results method linked to it",
                holder.getId(), solutionClass.getName()
                                                            ));
        }
    }

    private void validateNoMissing(IDHolder holder){
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

    private void collectInputs(IDHolder holder) throws InvocationTargetException, IllegalAccessException {
        for(AnnotatedMethod annotatedMethod : holder.getInputsMethods()) {
            Object[][] result = (Object[][]) annotatedMethod.getMethod().invoke(test);
            holder.addInputs(result);
        }
    }

    private void collectResults(IDHolder holder) throws InvocationTargetException, IllegalAccessException {
        for(AnnotatedMethod annotatedMethod : holder.getResultsMethods()) {
            Object[] result = (Object[]) annotatedMethod.getMethod().invoke(test);
            holder.addResults(result);
        }
    }

    private void validateInputsLength(IDHolder holder) {
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

    private void validateReturnTypes(IDHolder holder) {
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

    private void validateParameterTypes(IDHolder holder) {
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

    private void collectSolutions(IDHolder holder) throws InvocationTargetException, IllegalAccessException {
        List<AnnotatedMethod> solutionMethods = holder.getSolutionMethods();
        List<Object[][]> inputs = holder.getInputs();
        for(AnnotatedMethod annotatedMethod : solutionMethods) {
            Method method = annotatedMethod.getMethod();
            for(Object[][] input2D : inputs) {
                for(Object[] input1D : input2D) {
                    Object solution = method.invoke(test, input1D);
                    final long time1 = System.nanoTime();
                    holder.addSolution(solution);
                    final long time2 = System.nanoTime();
                    final long timeDif = time2 - time1;
                    holder.addTimeNanos(timeDif);
                }
            }
        }
    }

    private TestResults toTestResults() {
        boolean globalSuccess;
        int globalTotal = 0;
        int globalPassed = 0;
        int globalFailed = 0;
        final List<String> idsList = new ArrayList<>(ids.keySet());
        final Map<String, TestResults.TestResult> testResults = new HashMap<>();

        for(IDHolder holder : ids.values()) {
            boolean localSuccess;
            int localTotal = 0;
            int localPassed = 0;
            int localFailed = 0;
            String id = holder.getId();
            List<Object[][]> inputs = holder.getInputs();
            Object[] results = holder.getResults().get(0);
            List<Object> solutions = holder.getSolutions();
            List<String> methodNames = new ArrayList<>();
            List<Boolean> hasPassed = new ArrayList<>();
            long timeNanos = holder.getTimeNanos();
            double timeMS = holder.getTimeAsMS();

            extractMethodNames(methodNames, holder);
            processHasPassed(hasPassed, holder);

            for(Boolean curPassed : hasPassed) {
                ++localTotal;
                if(curPassed) ++localPassed;
                else ++localFailed;
            }
            localSuccess = localTotal == localPassed;
            globalTotal += localTotal;
            globalPassed += localPassed;
            globalFailed += localFailed;

            testResults.put(id, new TestResults.TestResult(
                localSuccess,
                localTotal,
                localPassed,
                localFailed,
                id,
                inputs,
                results,
                solutions,
                methodNames,
                hasPassed,
                timeNanos,
                timeMS
            ));
        }
        globalSuccess = globalPassed == globalTotal;
        return new TestResults(
            globalSuccess,
            globalTotal,
            globalPassed,
            globalFailed,
            idsList,
            testResults
        );
    }

    private void processHasPassed(List<Boolean> hasPassed, IDHolder holder) {
        Object[] curResults = holder.getResults().get(0);
        List<Object> curSolutions = holder.getSolutions();
        for(int i = 0; i < curSolutions.size(); ++i) {
            Object curResult = curResults[i % curResults.length];
            Object curSolution = curSolutions.get(i);
            boolean curPassed = SolveUtils.eEquals(curResult, curSolution);
            hasPassed.add(curPassed);
        }
    }

    private void extractMethodNames(List<String> methodNames, IDHolder holder) {
        for(AnnotatedMethod solutionMethod : holder.getSolutionMethods()) {
            methodNames.add(solutionMethod.getMethod().getName());
        }
    }

    private void extractSolutions(Map<String, List<Object>> solutions, IDHolder holder) {
        solutions.put(holder.getId(), holder.getSolutions());
    }

    private void extractResults(Map<String, Object[]> results, IDHolder holder) {
        results.put(holder.getId(), holder.getResults().get(0));
    }

    private void extractInputs(List<Object[][]> inputs, IDHolder holder) {
        inputs.addAll(holder.getInputs());
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
