package com.mikedeejay2.codingbatsolutions.internal;

import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CodingBatRunner {
    private static final Function<Object, Boolean> isArray = o ->
        o instanceof Object[] ||
        o instanceof byte[] ||
        o instanceof short[] ||
        o instanceof int[] ||
        o instanceof long[] ||
        o instanceof char[] ||
        o instanceof float[] ||
        o instanceof double[] ||
        o instanceof boolean[];

    private static final BiFunction<Class<?>, Object, Boolean> isInstance = (c, o) ->
        (o.getClass() == c) ||
        (o.getClass() == Byte.class && c == byte.class) ||
        (o.getClass() == Short.class && c == short.class) ||
        (o.getClass() == Integer.class && c == int.class) ||
        (o.getClass() == Long.class && c == long.class) ||
        (o.getClass() == Character.class && c == char.class) ||
        (o.getClass() == Float.class && c == float.class) ||
        (o.getClass() == Double.class && c == double.class) ||
        (o.getClass() == Boolean.class && c == boolean.class);

    private static final Function<Class<?>, Class<?>> toArray = (c) -> Array.newInstance(c, 0).getClass();

    private static final Function<Class<?>, Class<?>> toPrimitive = (c) -> {
        try
        {
            return (Class<?>) c.getField("TYPE").get(null);
        }
        catch(NoSuchFieldException | IllegalAccessException ignored) {}
        return c;
    };

    private static final Function<Object[], Object[]> fixArr = (o) -> {
        if(o.length == 0) return o;
        Class<?>[] classes = new Class[o.length];
        for(int i = 0; i < o.length; ++i) {
            Object cur = o[i];
            if(cur == null) {
                classes[i] = null;
                continue;
            }
            classes[i] = cur.getClass();
        }
        Class<?> type = null;
        for(int i = 0; i < classes.length; ++i) {
            Class<?> cur = classes[i];
            if(cur == null) continue;
            if(type == null) {
                type = cur;
            }
            if(type != cur) {
                throw new IllegalArgumentException(
                    String.format("%s is not of type %s",
                                  cur.getSimpleName(), type.getSimpleName()));
            }
        }
        Object[] newArr = (Object[]) Array.newInstance(type, o.length);
        System.arraycopy(o, 0, newArr, 0, o.length);
        return newArr;
    };

    private static final BiFunction<Class<?>, Object[], Object[]> classArrCast = (c, o) -> {
        Object newArr = Array.newInstance(c, o.length);
        System.arraycopy(o, 0, newArr, 0, o.length);
        return (Object[]) newArr;
    };

    private static final class SolutionData {
        public Class<?> solutionClass;
        public Method[] methods;
        public Method inputsMethod;
        public Method resultsMethod;
        public Method solutionMethod;
        public Class<?> resultType;
        public Class<?>[] parameterTypes;
        public Class<?> resultsResultType;
        public Class<?> inputsResultType;
        public int inputsResultArrDepth;
        public int resultsResultArrDepth;
        public int solutionResultArrDepth;
        public Object[] inputs;
        public Object[] expectedResults;
        public Object[] actualResults;
        public boolean[] successful;
        public int longestExpected;
        public int longestActual;
        public String[] expectedStrs;
        public String[] actualStrs;
        public String methodName;
        public int total;
        public int correct;
        public boolean passed;
    }

    private final boolean debugMode;

    public CodingBatRunner() {
        this.debugMode = false;
    }

    public CodingBatRunner(boolean debugMode) {
        this.debugMode = debugMode;
    }




    public boolean run(CodingBatSolution solution) {
        try {
            return runInternal(solution);
        }catch(IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean runInternal(CodingBatSolution solution) throws
            IllegalAccessException,
            InvocationTargetException {
        // Create a new SolutionData structure
        SolutionData data = new SolutionData();
        generateData(solution, data);
        printResult(data);
        return data.passed;
    }

    private void generateData(CodingBatSolution solution, SolutionData data) throws
        IllegalAccessException,
        InvocationTargetException {
        setupData(solution, data);
        validateSetup(data);
        getData(solution, data);
        processData(data);

        // Get whether the entire answer is correct or not
        data.passed = data.total == data.correct;
    }

    private void setupData(CodingBatSolution solution, SolutionData data) throws
        IllegalAccessException,
        InvocationTargetException {
        // Get the solution's class
        data.solutionClass = solution.getClass();
        // Get all methods
        data.methods = data.solutionClass.getDeclaredMethods();
        // Initialize needed methods
        data.inputsMethod = null;
        data.resultsMethod = null;
        data.solutionMethod = null;
        // Iterate through all methods to find the ones that we need
        for(Method method : data.methods) {
            // Set the method as accessible, it might be private
            method.setAccessible(true);
            // Attempt to get all annotations on the method
            Annotation inputsAnnotation = method.getAnnotation(Inputs.class);
            Annotation resultsAnnotation = method.getAnnotation(Results.class);
            Annotation solutionAnnotation = method.getAnnotation(Solution.class);
            // Find out how many annotations are on this method
            int test = 0;
            if(inputsAnnotation != null) ++test;
            if(resultsAnnotation != null) ++test;
            if(solutionAnnotation != null) ++test;
            // If there are no annotations here continue
            if(test == 0) continue;
            // If there is more than one (invalid) throw an exception
            if(test > 1) {
                throw new IllegalArgumentException(
                    String.format("Too many annotations on method %s in class %s",
                    method.getName(), data.solutionClass.getName()));
            }
            // Set the respective method
            if(inputsAnnotation != null) {
                data.inputsMethod = method;
            } else if(resultsAnnotation != null) {
                data.resultsMethod = method;
            } else if(solutionAnnotation != null) {
                data.solutionMethod = method;
            }
        }

        // If any of the required methods are null thrown an exception
        if(data.inputsMethod == null || data.resultsMethod == null || data.solutionMethod == null) {
            throw new IllegalArgumentException("Missing annotation in class " + data.solutionClass.getName());
        }

        // The results method doesn't take parameters
        if(data.resultsMethod.getParameterCount() != 0) {
            throw new IllegalArgumentException("Results method does not accept parameters in class " + data.solutionClass.getName());
        }

        // The inputs method doesn't take parameters
        if(data.inputsMethod.getParameterCount() != 0) {
            throw new IllegalArgumentException("Inputs method does not accept parameters in class " + data.solutionClass.getName());
        }

        // Get the result type class of the solution method
        data.resultType = data.solutionMethod.getReturnType();
        // Get the parameter types class array of the solution method
        data.parameterTypes = data.solutionMethod.getParameterTypes();

        // Get the result type class of the results method
        data.resultsResultType = data.resultsMethod.getReturnType();
        // Get the result type class of the inputs method
        data.inputsResultType = data.inputsMethod.getReturnType();

        if(debugMode) {
            System.out.println("Result Type: " + data.resultType.getName());
            System.out.println("Parameter Types: " + Arrays.stream(data.parameterTypes).map(Class::getName).collect(Collectors.joining(", ")));
            System.out.println("Results Result Type: " + data.resultsResultType.getName());
            System.out.println("Inputs Results Type: " + data.inputsResultType.getName());
        }

        // The inputs method should always return an array
        if(!data.inputsResultType.isArray()) {
            throw new IllegalArgumentException("Inputs method must return an array in class " + data.solutionClass.getName());
        }

        // The results method should always return an array
        if(!data.resultsResultType.isArray()) {
            throw new IllegalArgumentException("Results method must return an array in class " + data.solutionClass.getName());
        }

        // Calculate the array depth of the inputs method result
        data.inputsResultArrDepth = 0;
        {
            Class<?> curInputsResultType = data.inputsResultType;
            while(curInputsResultType.isArray()) {
                data.inputsResultArrDepth++;
                curInputsResultType = curInputsResultType.getComponentType();
            }
        }

        // Calculate the array depth of the results method result
        data.resultsResultArrDepth = 0;
        {
            Class<?> curResultsResultType = data.resultsResultType;
            while(curResultsResultType.isArray()) {
                data.resultsResultArrDepth++;
                curResultsResultType = curResultsResultType.getComponentType();
            }
        }

        // Calculate the array depth of the solution method result
        data.solutionResultArrDepth = 0;
        if(data.resultType.isArray()) {
            Class<?> curSolutionResultType = data.resultType;
            while(curSolutionResultType.isArray()) {
                data.solutionResultArrDepth++;
                curSolutionResultType = curSolutionResultType.getComponentType();
            }
        }

        if(debugMode) {
            System.out.println("Inputs Result Array Depth: " + data.inputsResultArrDepth);
            System.out.println("Results Result Array Depth: " + data.resultsResultArrDepth);
            System.out.println("Solution Result Array Depth: " + data.solutionResultArrDepth);
        }

        // The solution method result array depth should be 1 less than the results method result array depth
        if(data.solutionResultArrDepth != data.resultsResultArrDepth - 1) {
            throw new IllegalArgumentException("The array depth of solution method should be 1 less than the array depth of the results method result in class " + data.solutionClass.getName());
        }

        // Get the inputs from the inputs method
        data.inputs = (Object[]) data.inputsMethod.invoke(solution);
        // Get the expected results from the results method
        data.expectedResults = (Object[]) data.resultsMethod.invoke(solution);

        // The inputs length should be equal to the expected results length
        if(data.inputs.length != data.expectedResults.length) {
            throw new IllegalArgumentException("The result of inputs method and the results of results method do not match in length for class " + data.solutionClass.getName());
        }

        for(int i = 0; i < data.inputs.length; ++i) {
            Object curInput = data.inputs[i];
            if(!curInput.getClass().isArray()) continue;
            Object[] curInputArr = (Object[]) curInput;
            if(data.parameterTypes.length == 1 && data.parameterTypes[0].isArray()) {
                if(curInputArr.length == 0) continue;
                data.inputs[i] = classArrCast.apply(data.parameterTypes[0].getComponentType(), curInputArr);
                continue;
            }
        }
    }

    private void validateSetup(SolutionData data) {
        // Input validation begin
        for(int i = 0; i < data.inputs.length; ++i) {
            Object curInput = data.inputs[i];
            // Inputs cannot be null
            if(curInput == null) {
                throw new IllegalArgumentException(
                    String.format("Input at index %d was null for class %s",
                                  i, data.solutionClass.getName()));
            }

            int curInputArrLength = 0;
            Class<?> inputArrType = curInput.getClass();
            while(inputArrType.isArray()) {
                curInputArrLength++;
                inputArrType = inputArrType.getComponentType();
            }
            int curParamArrLength = 0;
            Class<?> paramArrType = data.parameterTypes[0];
            while(paramArrType.isArray()) {
                curParamArrLength++;
                paramArrType = paramArrType.getComponentType();
            }

            if(debugMode) {
                System.out.println("Current input array length: " + curInputArrLength);
                System.out.println("Current parameter array length: " + curParamArrLength);
            }

            if(curInputArrLength != curParamArrLength) {
                throw new IllegalArgumentException(
                    String.format("The input of index %d is not the correct array length as specified by the parameter in class %s",
                                  i, data.solutionClass.getName()));
            }

            // If it's an array we need to go deeper
            if(isArray.apply(curInput) && !(data.parameterTypes.length == 1 && data.parameterTypes[0].isArray())) {
                // Get the array input cast
                Object[] curInputArr = (Object[]) curInput;
                // Parameter types length and input array length need to be the same size
                if(curInputArr.length != data.parameterTypes.length) {
                    throw new IllegalArgumentException(
                        String.format("The input of index %d is not the same length of the solution's parameters in class %s",
                                      i, data.solutionClass.getName()));
                }
                // Ensure that the data types are matching
                for(int si = 0; si < curInputArr.length; ++si) {
                    // Get the sub-input
                    Object subInput = curInputArr[si];
                    // The sub-input cannot be null
                    if(subInput == null) {
                        throw new IllegalArgumentException(
                            String.format("Input at index %d in sub-index %d was null for class %s",
                                          i, si, data.solutionClass.getName()));
                    }
                    // The parameter types must be matching
                    if(!isInstance.apply(data.parameterTypes[si], subInput)) {
                        throw new IllegalArgumentException(
                            String.format("The input in index %d in sub-index %d %s is not assignable to the parameter type of %s in class %s",
                                          i, si, subInput, data.parameterTypes[si].getSimpleName(), data.solutionClass.getName()));
                    }
                }
            } else if(!(data.parameterTypes.length == 1 && data.parameterTypes[0].isArray())) {
                // The parameter types must be matching
                if(!isInstance.apply(data.parameterTypes[0], curInput)) {
                    throw new IllegalArgumentException(
                        String.format("The input %s is not assignable to the parameter type of %s in class %s",
                                      curInput, data.parameterTypes[0].getSimpleName(), data.solutionClass.getName()));
                }
            }
        }
        // Input validation end
    }

    private void getData(CodingBatSolution solution, SolutionData data) throws
        IllegalAccessException,
        InvocationTargetException {
        // Get the actual results
        data.actualResults = new Object[data.inputs.length];
        for(int i = 0; i < data.inputs.length; ++i) {
            Object curInput = data.inputs[i];
            // Must check if it's an array because reflection is odd
            if(isArray.apply(curInput) && !(data.parameterTypes.length == 1 && data.parameterTypes[0].isArray())) {
                data.actualResults[i] = data.solutionMethod.invoke(solution, (Object[]) curInput);
            } else {
                System.out.println("CurInput: " + curInput.getClass().getSimpleName());
                System.out.println("ExpectedInput: " + data.parameterTypes[0].getSimpleName());
                data.actualResults[i] = data.solutionMethod.invoke(solution, curInput);
            }
        }

        if(debugMode) {
            System.out.println("Actual Outputs: " + Arrays.toString(data.actualResults));
        }
    }

    private void processData(SolutionData data) {
        // Compare actual results with expected results, print results
        data.successful = new boolean[data.actualResults.length];
        data.longestExpected = 0;
        data.longestActual = 0;
        data.expectedStrs = new String[data.actualResults.length];
        data.actualStrs = new String[data.actualResults.length];
        data.methodName = data.solutionMethod.getName();

        // Iterate through the actual results
        for(int i = 0; i < data.actualResults.length; ++i) {
            Object curActualResult = data.actualResults[i];
            Object curExpectedResult = data.expectedResults[i];

            // Identify success or failure
            boolean success = true;
            if(curActualResult == null && curExpectedResult == null) {
                success = true;
            } else if(curActualResult == null ^ curExpectedResult == null) {
                success = false;
            } else if(curActualResult.getClass() != curExpectedResult.getClass()) {
                success = false;
            } else if(isArray.apply(curActualResult)) {
                if(!(isArray.apply(curExpectedResult))) {
                    success = false;
                } else if(!Arrays.deepEquals((Object[]) curActualResult, (Object[]) curExpectedResult)) {
                    success = false;
                }
            } else if(!curActualResult.equals(curExpectedResult)) {
                success = false;
            }
            data.successful[i] = success;

            // Build the expected String
            StringBuilder mBuilder = new StringBuilder();
            mBuilder.append(data.methodName);
            mBuilder.append("(");
            // If params length is one no need to travel deeper
            if(data.parameterTypes.length == 1) {
                Object curInput = data.inputs[i];
                // Check to make sure it's not itself an array
                if(isArray.apply(curInput)) {
                    mBuilder.append(Arrays.deepToString((Object[]) curInput));
                } else {
                    mBuilder.append(curInput);
                }
            } else if(data.parameterTypes.length > 1) {
                // Array detected, make sure it prints well
                Object[] inputsArr = (Object[]) data.inputs[i];
                for(int y = 0; y < inputsArr.length; ++y) {
                    Object subInput = inputsArr[y];
                    if(isArray.apply(subInput)) {
                        mBuilder.append(Arrays.deepToString((Object[]) subInput));
                    } else {
                        if(subInput instanceof String) mBuilder.append("\"");
                        mBuilder.append(subInput.toString());
                        if(subInput instanceof String) mBuilder.append("\"");
                    }
                    if(y != data.parameterTypes.length - 1) {
                        mBuilder.append(", ");
                    }
                }
            }
            mBuilder.append(") → ");
            if(isArray.apply(curExpectedResult)) {
                mBuilder.append(Arrays.deepToString((Object[]) curExpectedResult));
            } else {
                if(curExpectedResult instanceof String) mBuilder.append("\"");
                mBuilder.append(curExpectedResult);
                if(curExpectedResult instanceof String) mBuilder.append("\"");
            }

            String expectedStr = mBuilder.toString();
            data.longestExpected = Math.max(data.longestExpected, expectedStr.length());
            data.expectedStrs[i] = expectedStr;

            // Build to actual String
            StringBuilder actualStrBuilder = new StringBuilder();
            if(isArray.apply(curActualResult)) {
                actualStrBuilder.append(Arrays.deepToString((Object[]) curActualResult));
            } else {
                if(curActualResult instanceof String) actualStrBuilder.append("\"");
                actualStrBuilder.append(curActualResult);
                if(curActualResult instanceof String) actualStrBuilder.append("\"");
            }
            String actualStr = actualStrBuilder.toString();
            data.actualStrs[i] = actualStr;
            data.longestActual = Math.max(data.longestActual, actualStr.length());
        }

        // Calculate the amount correct
        data.total = data.actualResults.length;
        data.correct = 0;
        for(int i = 0; i < data.successful.length; ++i) {
            boolean cur = data.successful[i];
            if(cur) ++data.correct;
        }

        // Post process the expected and actual Strings for length
        for(int i = 0; i < data.actualResults.length; ++i) {
            String curExpected = data.expectedStrs[i];
            int expectedDifference = data.longestExpected - curExpected.length();
            StringBuilder expectedBuilder = new StringBuilder();
            expectedBuilder.append(curExpected);
            for(int x = 0; x < expectedDifference; ++x) {
                expectedBuilder.append(" ");
            }
            data.expectedStrs[i] = expectedBuilder.toString();

            String curActual = data.actualStrs[i];
            int actualDifference = data.longestActual - curActual.length();
            StringBuilder actualBuilder = new StringBuilder();
            actualBuilder.append(curActual);
            for(int x = 0; x < actualDifference; ++x) {
                actualBuilder.append(" ");
            }
            data.actualStrs[i] = actualBuilder.toString();
        }
    }

    private void printResult(SolutionData data) {
        // Create the output message
        StringBuilder outputBuilder = new StringBuilder();
        String expectedStr = "Expected";
        int expectedDifference = (int) Math.ceil((data.longestExpected - expectedStr.length()) / 2.0);
        StringBuilder expectedSpacingBuilder = new StringBuilder();
        for(int i = 0; i < expectedDifference; ++i) {
            expectedSpacingBuilder.append(" ");
        }
        String expectedSpacing = expectedSpacingBuilder.toString();
        outputBuilder.append("  ").append(expectedSpacing).append(expectedStr).append(expectedSpacing);
        String runStr = "Run";
        int runDifference = (int) Math.ceil((data.longestActual - runStr.length()) / 2.0);
        StringBuilder actualSpacingBuilder = new StringBuilder();
        for(int i = 0; i < runDifference; ++i) {
            actualSpacingBuilder.append(" ");
        }
        String actualSpacing = actualSpacingBuilder.toString();
        outputBuilder.append("  ").append(actualSpacing).append(runStr);
        outputBuilder.append("\n");
        for(int i = 0; i < data.actualResults.length; ++i) {
            String curExpected = data.expectedStrs[i];
            String curActual = data.actualStrs[i];
            String curSuccess = data.successful[i] ? "OK" : "X ";

            outputBuilder.append("│ ").append(curExpected).append(" │ ").append(curActual).append(" │ ").append(curSuccess).append(" │\n");
        }

        outputBuilder.append("\n");
        if(data.correct == data.total) outputBuilder.append("✓ All Correct");
        else if(data.correct > (data.total / 2)) outputBuilder.append("Correct for more than half the tests");

        String toPrint = outputBuilder.toString();
        // Print the output message
        System.out.println(toPrint);
    }
}
