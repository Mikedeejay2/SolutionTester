package com.mikedeejay2.solutiontester.internal;

import com.mikedeejay2.solutiontester.SolutionTest;
import com.mikedeejay2.solutiontester.internal.data.TestResults;
import com.mikedeejay2.solutiontester.internal.data.AnnotatedMethod;
import com.mikedeejay2.solutiontester.internal.data.IDHolder;
import com.mikedeejay2.solutiontester.internal.util.SolveUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Supplier;

/**
 * Test solver for {@link SolutionTest}s. Used to generate a {@link TestResults} for further processing and analyzing.
 * See {@link SolutionPrinter} for printing {@link TestResults}.
 * <p>
 * Use {@link SolutionTestSolver#get()} to generate {@link TestResults}
 *
 * @author Mikedeejay2
 * @since 1.0.0
 */
public class SolutionTestSolver implements Supplier<TestResults> {
    /**
     * String to identify all IDs
     */
    protected static final String ALL_IDENTIFIER = "%all%";

    /**
     * The {@link SolutionTest} being tested
     */
    protected final SolutionTest test;

    /**
     * The solution test <code>Classes</code> being tested
     */
    protected Class<?>[] solutionClasses;

    /**
     * The list of {@link AnnotatedMethod} of the {@link SolutionTestSolver#solutionClasses}
     */
    protected final List<AnnotatedMethod> annotatedMethods;

    /**
     * The compiled list of all IDs used in the {@link SolutionTestSolver#solutionClasses}
     */
    protected final Map<String, IDHolder> ids;

    /**
     * Construct a new <code>SolutionTestSolver</code>
     *
     * @param test The {@link SolutionTest} to be tested
     * @since 1.0.0
     */
    public SolutionTestSolver(@NotNull SolutionTest test) {
        this.test = test;
        this.solutionClasses = null;
        this.annotatedMethods = new ArrayList<>();
        this.ids = new LinkedHashMap<>();
    }

    /**
     * Get {@link TestResults}. This method generates a new {@link TestResults} each time, so computation time should
     * be considered when calling this method.
     * <p>
     * Computation time for this method can be a few milliseconds or more since a large amount of reflection is used to
     * collect all data from the {@link SolutionTest} and invoke solution methods. In Intellij Idea, JUnit tests show
     * the relatively how much time this method takes, the average being 20-30ms.
     *
     * @return The generated <code>TestResults</code>
     * @since 1.0.0
     */
    @Override
    public TestResults get() {
        try {
            return solve();
        } catch(Exception e) {
            e.getCause().printStackTrace();
            return null;
        }
    }

    /**
     * Solve the given {@link SolutionTest} and create a {@link TestResults}
     *
     * @return The generated <code>TestResults</code>
     * @throws InvocationTargetException If a reflected method throws and exception
     * @throws IllegalAccessException    If reflection tries to call an inaccessible method
     * @since 1.0.0
     */
    private TestResults solve() throws InvocationTargetException, IllegalAccessException {
        // COLLECTION STAGE 1
        collectSolutionClasses();
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

    /**
     * Collect the solution class from the test object.
     */
    private void collectSolutionClasses() {
        List<Class<?>> classes = new ArrayList<>();
        Class<?> currentClass = test.getClass();
        while(currentClass.getSuperclass() != null) {
            classes.add(currentClass);
            currentClass = currentClass.getSuperclass();
        }
        this.solutionClasses = classes.toArray(new Class[0]);
    }

    /**
     * Collect all {@link AnnotatedMethod}s from the solution's class.
     */
    private void collectAnnotatedMethods() {
        for(Class<?> solutionClass : solutionClasses) {
            for(Method method : solutionClass.getDeclaredMethods()) {
                if(!method.isAccessible()) method.setAccessible(true);
                AnnotatedMethod annotatedMethod = new AnnotatedMethod(method);
                annotatedMethod.fillAnnotations();
                if(annotatedMethod.getRefCount() == 0) continue;
                annotatedMethods.add(annotatedMethod);
            }
        }
    }

    /**
     * Collect all {@link IDHolder}s from the <code>AnnotatedMethods</code>.
     */
    private void collectIDs() {
        for(AnnotatedMethod method : annotatedMethods) {
            if(method.isInputs()) {
                List<String> idList = method.getInputsIDs();
                addIDIfNotPresent(method, idList, AnnotatedMethod.Type.INPUTS);
            }
            if(method.isResults()) {
                List<String> idList = method.getResultsIDs();
                addIDIfNotPresent(method, idList, AnnotatedMethod.Type.RESULTS);
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
                        addIDIfNotPresent(method, idArr, AnnotatedMethod.Type.SOLUTION);
                    }
                }
            }
        }
    }

    /**
     * Validate the current state of all annotations.
     *
     * @param method The reference {@link AnnotatedMethod}
     */
    private void validateAnnotations(AnnotatedMethod method) {
        int test = 0;
        if(method.isInputs()) ++test;
        if(method.isResults()) ++test;
        if(method.isSolution()) ++test;
        if(test > 1) {
            StringBuilder message = new StringBuilder();
            message.append(String.format(
                "The method \"%s\" has too many type annotations in class \"%s\"",
                method.getMethod().getName(), solutionClasses[0].getName()));
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

    /**
     * Validate the current state of an {@link AnnotatedMethod}.
     *
     * @param annotatedMethod The {@link AnnotatedMethod} reference
     */
    private void validateMethods(AnnotatedMethod annotatedMethod) {
        Method method = annotatedMethod.getMethod();
        Class<?> returnType = method.getReturnType();
        Class<?>[] parameterTypes = method.getParameterTypes();
        switch(annotatedMethod.getType()) {
            case INPUTS:
                if(parameterTypes.length > 0) {
                    throw new IllegalArgumentException(String.format(
                        "The input method \"%s\" can not take parameters in class \"%s\"",
                        annotatedMethod.getMethod().getName(), solutionClasses[0].getName()
                                                                    ));
                }
                if(!(returnType.isArray() && returnType.getComponentType().isArray())) {
                    throw new IllegalArgumentException(String.format(
                        "The input method \"%s\" does not have the correct return type in class \"%s\"" +
                        "\nInput methods can only return a 2D array of objects (Object[][]), each index being a list of " +
                        "parameters to send to the solution methods",
                        annotatedMethod.getMethod().getName(), solutionClasses[0].getName()
                                                                    ));
                }
                break;
            case RESULTS:
                if(parameterTypes.length > 0) {
                    throw new IllegalArgumentException(String.format(
                        "The result method \"%s\" can not take parameters in class \"%s\"",
                        annotatedMethod.getMethod().getName(), solutionClasses[0].getName()
                                                                    ));
                }
                if(!returnType.isArray()) {
                    throw new IllegalArgumentException(String.format(
                        "The input method \"%s\" does not have the correct return type in class \"%s\"" +
                        "\nResult methods can only return an array of objects (Object[]), each index being an expected " +
                        "result of the solution methods",
                        annotatedMethod.getMethod().getName(), solutionClasses[0].getName()
                                                                    ));
                }
                break;
        }
    }

    /**
     * Validate that IDs exist and the solver isn't attempting to operate on a blank test.
     */
    private void validateIDsFilled() {
        if(ids.isEmpty()) {
            throw new IllegalArgumentException(String.format(
                "No IDs exist in class \"%s\", this is usually caused by attempting to run a unit test on a class with " +
                "no testing methods",
                solutionClasses[0].getName()
                                                            ));
        }
    }

    /**
     * Validate the results of a single {@link IDHolder}.
     *
     * @param holder The reference {@link IDHolder}
     */
    private void validateSingleResults(IDHolder holder) {
        if(holder.getResults().size() > 1) {
            throw new IllegalArgumentException(String.format(
                "The ID \"%s\" has multiple results methods in class \"%s\"" +
                "\nAn ID can only have a single results method linked to it",
                holder.getId(), solutionClasses[0].getName()
                                                            ));
        }
    }

    /**
     * Validate that there are no missing methods or annotations.
     *
     * @param holder The reference {@link IDHolder}
     */
    private void validateNoMissing(IDHolder holder){
        if(holder.getInputsMethods().isEmpty()) {
            throw new IllegalArgumentException(String.format(
                "The ID \"%s\" has no input methods in class \"%s\"",
                holder.getId(), solutionClasses[0].getName()
                                                            ));
        }
        if(holder.getResultsMethods().isEmpty()) {
            throw new IllegalArgumentException(String.format(
                "The ID \"%s\" has no result methods in class \"%s\"",
                holder.getId(), solutionClasses[0].getName()
                                                            ));
        }
        if(holder.getSolutionMethods().isEmpty()) {
            throw new IllegalArgumentException(String.format(
                "The ID \"%s\" has no solution methods in class \"%s\"",
                holder.getId(), solutionClasses[0].getName()
                                                            ));
        }
    }

    /**
     * Collect all inputs of an {@link IDHolder}.
     *
     * @param holder                     The reference {@link IDHolder}
     * @throws InvocationTargetException If a reflected method throws and exception
     * @throws IllegalAccessException    If reflection tries to call an inaccessible method
     */
    private void collectInputs(IDHolder holder) throws InvocationTargetException, IllegalAccessException {
        for(AnnotatedMethod annotatedMethod : holder.getInputsMethods()) {
            Object[][] result = (Object[][]) annotatedMethod.getMethod().invoke(test);
            holder.addInputs(result);
        }
    }

    /**
     * Collect all results of an {@link IDHolder}
     *
     * @param holder                     The reference {@link IDHolder}
     * @throws InvocationTargetException If a reflected method throws and exception
     * @throws IllegalAccessException    If reflection tries to call an inaccessible method
     */
    private void collectResults(IDHolder holder) throws InvocationTargetException, IllegalAccessException {
        for(AnnotatedMethod annotatedMethod : holder.getResultsMethods()) {
            Object[] result = (Object[]) annotatedMethod.getMethod().invoke(test);
            holder.addResults(result);
        }
    }

    /**
     * Validate the array lengths of inputs of an {@link IDHolder}
     *
     * @param holder The reference {@link IDHolder}
     */
    private void validateInputsLength(IDHolder holder) {
        Object[] results = holder.getResults().get(0);
        List<Object[][]> inputs = holder.getInputs();
        int previousLength = -1;
        for(Object[][] input2d : inputs) {
            if(input2d == null) {
                throw new IllegalArgumentException(String.format(
                    "The ID \"%s\" has an input that returned null in class \"%s\"",
                    holder.getId(), solutionClasses[0].getName()
                                                                ));
            }
            if(input2d.length != results.length) {
                throw new IllegalArgumentException(String.format(
                    "The ID \"%s\" has a set of inputs that does not set the size of the results in class \"%s\"",
                    holder.getId(), solutionClasses[0].getName()
                                                                ));
            }
            for(Object[] input1d : input2d) {
                if(input1d == null) {
                    throw new IllegalArgumentException(String.format(
                        "The ID \"%s\" has a sub-input that returned null in class \"%s\"",
                        holder.getId(), solutionClasses[0].getName()
                                                                    ));
                }
                if(previousLength == -1) previousLength = input1d.length;
                if(input1d.length != previousLength) {
                    throw new IllegalArgumentException(String.format(
                        "The ID \"%s\" has a set of inputs that does not set the size of the results in class \"%s\"",
                        holder.getId(), solutionClasses[0].getName()
                                                                    ));
                }
            }
        }
    }

    /**
     * Validate the return data types of a {@link IDHolder}
     *
     * @param holder The reference {@link IDHolder}
     */
    private void validateReturnTypes(IDHolder holder) {
        Object[] results = holder.getResults().get(0);
        Class<?> solutionReturnType = null;
        for(AnnotatedMethod method : holder.getSolutionMethods()) {
            if(solutionReturnType == null) solutionReturnType = method.getMethod().getReturnType();
            if(solutionReturnType != method.getMethod().getReturnType()) {
                throw new IllegalArgumentException(String.format(
                    "The ID \"%s\" has mismatched solution return types in class \"%s\"",
                    holder.getId(), solutionClasses[0].getName()
                                                                ));
            }
            for(Object result : results) {
                if(result == null) {
                    if(solutionReturnType.isPrimitive()) {
                        throw new IllegalArgumentException(String.format(
                            "The ID \"%s\" has a result with an invalid null parameter type in class \"%s\"",
                            holder.getId(), solutionClasses[0].getName()
                                                                        ));
                    }
                    continue;
                }
                Class<?> resultClass = result.getClass();
                if(solutionReturnType.isArray() != resultClass.isArray()) {
                    throw new IllegalArgumentException(String.format(
                        "The ID \"%s\" has mismatched solution and result array return types in class \"%s\"",
                        holder.getId(), solutionClasses[0].getName()
                                                                    ));
                } else if(!SolveUtils.isInstance(solutionReturnType, resultClass)) {
                    throw new IllegalArgumentException(String.format(
                        "The ID \"%s\" has a result type that does not match the return type in class \"%s\"",
                        holder.getId(), solutionClasses[0].getName()
                                                                    ));
                }
            }
        }
    }

    /**
     * Validate the parameter data types of the inputs of an {@link IDHolder}.
     *
     * @param holder The reference {@link IDHolder}
     */
    private void validateParameterTypes(IDHolder holder) {
        Class<?>[] solutionParameterTypes = null;
        for(AnnotatedMethod solutionAnnoMethod : holder.getSolutionMethods()) {
            Method solutionMethod = solutionAnnoMethod.getMethod();
            Class<?>[] curParameterTypes = solutionMethod.getParameterTypes();
            if(solutionParameterTypes == null) solutionParameterTypes = curParameterTypes;
            if(curParameterTypes.length != solutionParameterTypes.length) {
                throw new IllegalArgumentException(String.format(
                    "The ID \"%s\" has solutions with different parameter lengths in class \"%s\"",
                    holder.getId(), solutionClasses[0].getName()
                                                                ));
            }
            for(int i = 0; i < solutionParameterTypes.length; ++i) {
                Class<?> solutionParamType = solutionParameterTypes[i];
                Class<?> curParamType = curParameterTypes[i];
                if(solutionParamType != curParamType) {
                    throw new IllegalArgumentException(String.format(
                        "The ID \"%s\" has mismatched parameter types for solutions in class \"%s\"",
                        holder.getId(), solutionClasses[0].getName()
                                                                    ));
                }
            }
        }
        for(Object[][] input2D : holder.getInputs()) {
            for(Object[] input1D : input2D) {
                if(input1D.length != solutionParameterTypes.length) {
                    throw new IllegalArgumentException(String.format(
                        "The ID \"%s\" has an input with invalid parameter length in class \"%s\"",
                        holder.getId(), solutionClasses[0].getName()
                                                                    ));
                }
                for(int i = 0; i < input1D.length; ++i) {
                    Class<?> expectedType = solutionParameterTypes[i];
                    Object input = input1D[i];
                    if(input == null) {
                        if(expectedType.isPrimitive()) {
                            throw new IllegalArgumentException(String.format(
                                "The ID \"%s\" has an input with invalid null parameter type in class \"%s\"",
                                holder.getId(), solutionClasses[0].getName()
                                                                            ));
                        }
                        continue;
                    }
                    if(!SolveUtils.isInstance(expectedType, input.getClass())) {
                        throw new IllegalArgumentException(String.format(
                            "The ID \"%s\" has an input with invalid parameter types in class \"%s\"",
                            holder.getId(), solutionClasses[0].getName()
                                                                        ));
                    }
                }
            }
        }
    }

    /**
     * Collect the solutions of an {@link IDHolder}.
     *
     * @param holder                     The reference {@link IDHolder}
     * @throws InvocationTargetException If a reflected method throws and exception
     * @throws IllegalAccessException    If reflection tries to call an inaccessible method
     */
    private void collectSolutions(IDHolder holder) throws InvocationTargetException, IllegalAccessException {
        List<AnnotatedMethod> solutionMethods = holder.getSolutionMethods();
        List<Object[][]> inputs = holder.getInputs();
        for(AnnotatedMethod annotatedMethod : solutionMethods) {
            Method method = annotatedMethod.getMethod();
            for(Object[][] input2D : inputs) {
                for(Object[] input1D : input2D) {
                    final long time1 = System.nanoTime();
                    Object solution = method.invoke(test, input1D);
                    final long time2 = System.nanoTime();
                    final long timeDif = time2 - time1;
                    holder.addSolution(solution);
                    holder.addTimeNanos(timeDif);
                }
            }
        }
    }

    /**
     * Convert all collected data to a {@link TestResults} object.
     *
     * @return The generated {@link TestResults}
     */
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

    /**
     * Process whether each test has passed or not.
     *
     * @param hasPassed The list of booleans to add to
     * @param holder    The reference {@link IDHolder}
     */
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

    /**
     * Extract the names of all methods tested.
     *
     * @param methodNames The list of method names to add to
     * @param holder      The reference {@link IDHolder}
     */
    private void extractMethodNames(List<String> methodNames, IDHolder holder) {
        for(AnnotatedMethod solutionMethod : holder.getSolutionMethods()) {
            methodNames.add(solutionMethod.getMethod().getName());
        }
    }

    /**
     * Add an ID to an the <code>idList</code> if not currently present
     *
     * @param method The reference {@link AnnotatedMethod}
     * @param idList The list of IDs to add
     * @param type   The {@link AnnotatedMethod.Type} of the method
     */
    private void addIDIfNotPresent(AnnotatedMethod method, List<String> idList, AnnotatedMethod.Type type) {
        for(String id : idList) {
            addIDIfNotPresent(method, id, type);
        }
    }

    /**
     * Add an ID to an the <code>idList</code> if not currently present
     *
     * @param method The reference {@link AnnotatedMethod}
     * @param idArr  The array of IDs to add
     * @param type   The {@link AnnotatedMethod.Type} of the method
     */
    private void addIDIfNotPresent(AnnotatedMethod method, String[] idArr, AnnotatedMethod.Type type) {
        for(String id : idArr) {
            addIDIfNotPresent(method, id, type);
        }
    }

    /**
     * Add an ID to an the <code>idList</code> if not currently present
     *
     * @param method The reference {@link AnnotatedMethod}
     * @param id     The ID to add
     * @param type   The {@link AnnotatedMethod.Type} of the method
     */
    private void addIDIfNotPresent(AnnotatedMethod method, String id, AnnotatedMethod.Type type) {
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

    /**
     * Ensure that an ID exists in an {@link SolutionTestSolver#ids}. If it doesn't exist, add it.
     *
     * @param id The ID to validate
     */
    private void validateID(String id) {
        if(!ids.containsKey(id)) {
            ids.put(id, new IDHolder(id));
        }
    }
}
