package com.mikedeejay2.codingbatsolutions.internal;

import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CodingBatRunner {
    private static final Function<Object, Boolean> isArray = o -> {
        return o instanceof Object[] ||
               o instanceof byte[] ||
               o instanceof short[] ||
               o instanceof int[] ||
               o instanceof long[] ||
               o instanceof char[] ||
               o instanceof float[] ||
               o instanceof double[] ||
               o instanceof boolean[];
    };

    private static final BiFunction<Class<?>, Object, Boolean> isInstance = (c, o) -> {
        return (o.getClass() == c) ||
               (o.getClass() == Byte.class && c == byte.class) ||
               (o.getClass() == Short.class && c == short.class) ||
               (o.getClass() == Integer.class && c == int.class) ||
               (o.getClass() == Long.class && c == long.class) ||
               (o.getClass() == Character.class && c == char.class) ||
               (o.getClass() == Float.class && c == float.class) ||
               (o.getClass() == Double.class && c == double.class) ||
               (o.getClass() == Boolean.class && c == boolean.class);
    };

    private final boolean debugMode;

    public CodingBatRunner() {
        this.debugMode = false;
    }

    public CodingBatRunner(boolean debugMode) {
        this.debugMode = debugMode;
    }




    public void run(CodingBatSolution solution) {
        try {
            runInternal(solution);
        }catch(IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private boolean runInternal(CodingBatSolution solution) throws
            IllegalAccessException,
            InvocationTargetException {
        // Get the solution's class
        Class<?> solutionClass = solution.getClass();
        // Get all methods
        Method[] methods = solutionClass.getDeclaredMethods();
        // Initialize needed methods
        Method inputsMethod = null;
        Method resultsMethod = null;
        Method solutionMethod = null;
        // Iterate through all methods to find the ones that we need
        for(Method method : methods) {
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
                    method.getName(), solutionClass.getName()));
            }
            // Set the respective method
            if(inputsAnnotation != null) {
                inputsMethod = method;
            } else if(resultsAnnotation != null) {
                resultsMethod = method;
            } else if(solutionAnnotation != null) {
                solutionMethod = method;
            }
        }

        // If any of the required methods are null thrown an exception
        if(inputsMethod == null || resultsMethod == null || solutionMethod == null) {
            throw new IllegalArgumentException("Missing annotation in class " + solutionClass.getName());
        }

        // The results method doesn't take parameters
        if(resultsMethod.getParameterCount() != 0) {
            throw new IllegalArgumentException("Results method does not accept parameters in class " + solutionClass.getName());
        }

        // The inputs method doesn't take parameters
        if(inputsMethod.getParameterCount() != 0) {
            throw new IllegalArgumentException("Inputs method does not accept paramters in class " + solutionClass.getName());
        }

        // Get the result type class of the solution method
        Class<?> resultType = solutionMethod.getReturnType();
        // Get the parameter types class array of the solution method
        Class<?>[] parameterTypes = solutionMethod.getParameterTypes();

        // Get the result type class of the results method
        Class<?> resultsResultType = resultsMethod.getReturnType();
        // Get the result type class of the inputs method
        Class<?> inputsResultType = inputsMethod.getReturnType();

        if(debugMode) {
            System.out.println("Result Type: " + resultType.getName());
            System.out.println("Parameter Types: " + Arrays.stream(parameterTypes).map(Class::getName).collect(Collectors.joining(", ")));
            System.out.println("Results Result Type: " + resultsResultType.getName());
            System.out.println("Inputs Results Type: " + inputsResultType.getName());
        }

        // The inputs method should always return an array
        if(!inputsResultType.isArray()) {
            throw new IllegalArgumentException("Inputs method must return an array in class " + solutionClass.getName());
        }

        // The results method should always return an array
        if(!resultsResultType.isArray()) {
            throw new IllegalArgumentException("Results method must return an array in class " + solutionClass.getName());
        }

        // Calculate the array depth of the inputs method result
        int inputsResultArrDepth = 0;
        {
            Class<?> curInputsResultType = inputsResultType;
            while(curInputsResultType.isArray()) {
                inputsResultArrDepth++;
                curInputsResultType = curInputsResultType.getComponentType();
            }
        }

        // Calculate the array depth of the results method result
        int resultsResultArrDepth = 0;
        {
            Class<?> curResultsResultType = resultsResultType;
            while(curResultsResultType.isArray()) {
                resultsResultArrDepth++;
                curResultsResultType = curResultsResultType.getComponentType();
            }
        }

        // Calculate the array depth of the solution method result
        int solutionResultArrDepth = 0;
        if(resultType.isArray()) {
            Class<?> curSolutionResultType = resultType;
            while(curSolutionResultType.isArray()) {
                solutionResultArrDepth++;
                curSolutionResultType = curSolutionResultType.getComponentType();
            }
        }

        if(debugMode) {
            System.out.println("Inputs Result Array Depth: " + inputsResultArrDepth);
            System.out.println("Results Result Array Depth: " + resultsResultArrDepth);
            System.out.println("Solution Result Array Depth: " + solutionResultArrDepth);
        }

        // The solution method result array depth should be 1 less than the results method result array depth
        if(solutionResultArrDepth != resultsResultArrDepth - 1) {
            throw new IllegalArgumentException("The array depth of solution method should be 1 less than the array depth of the results method result in class " + solutionClass.getName());
        }

        // Get the inputs from the inputs method
        Object[] inputs = (Object[]) inputsMethod.invoke(solution);
        // Get the expected results from the results method
        Object[] expectedResults = (Object[]) resultsMethod.invoke(solution);

        // The inputs length should be equal to the expected results length
        if(inputs.length != expectedResults.length) {
            throw new IllegalArgumentException("The result of inputs method and the results of results method do not match in length for class " + solutionClass.getName());
        }

        // Input validation begin
        for(int i = 0; i < inputs.length; ++i) {
            Object curInput = inputs[i];
            // Inputs cannot be null
            if(curInput == null) {
                throw new IllegalArgumentException(
                    String.format("Input at index %d was null for class %s",
                    i, solutionClass.getName()));
            }

            // If it's an array we need to go deeper
            if(isArray.apply(curInput)) {
                // Get the array input cast
                Object[] curInputArr = (Object[]) curInput;
                // Parameter types length and input array length need to be the same size
                if(curInputArr.length != parameterTypes.length) {
                    throw new IllegalArgumentException(
                        String.format("The input of index %d is not the same length of the solution's parameters in class %s",
                        i, solutionClass.getName()));
                }
                // Ensure that the data types are matching
                for(int si = 0; si < curInputArr.length; ++si) {
                    // Get the sub-input
                    Object subInput = curInputArr[si];
                    // The sub-input cannot be null
                    if(subInput == null) {
                        throw new IllegalArgumentException(
                            String.format("Input at index %d in sub-index %d was null for class %s",
                            i, si, solutionClass.getName()));
                    }
                    // The parameter types must be matching (ignore primitives, they don't match to objects)
                    if(!isInstance.apply(parameterTypes[si], subInput)) {
                        throw new IllegalArgumentException(
                                String.format("The input in index %d in sub-index %d %s is not assignable to the parameter type of %s in class %s",
                                i, si, subInput.toString(), parameterTypes[si].getSimpleName(), solutionClass.getName()));
                    }
                }
            } else {
                // The parameter types must be matching
                if(!isInstance.apply(parameterTypes[0], curInput)) {
                    throw new IllegalArgumentException(
                        String.format("The input %s is not assignable to the parameter type of %s in class %s",
                        curInput.toString(), parameterTypes[0].getSimpleName(), solutionClass.getName()));
                }
            }
        }
        // Input validation end

        // Get the actual results
        Object[] actualResults = new Object[inputs.length];
        for(int i = 0; i < inputs.length; ++i) {
            Object curInput = inputs[i];
            // Must check if it's an array because reflection is odd
            if(isArray.apply(curInput)) {
                actualResults[i] = solutionMethod.invoke(solution, (Object[]) curInput);
            } else {
                actualResults[i] = solutionMethod.invoke(solution, curInput);
            }
        }

        if(debugMode) {
            System.out.println("Actual Outputs: " + Arrays.toString(actualResults));
        }

        // Compare actual results with expected results, print results
        boolean[] successful = new boolean[actualResults.length];
        int longestExpected = 0;
        int longestActual = 0;
        String[] expectedStrs = new String[actualResults.length];
        String[] actualStrs = new String[actualResults.length];
        String methodName = solutionMethod.getName();

        // Iterate through the actual results
        for(int i = 0; i < actualResults.length; ++i) {
            Object curActualResult = actualResults[i];
            Object curExpectedResult = expectedResults[i];

            // Identify success or failure
            boolean success = true;
            if(curActualResult.getClass() != curExpectedResult.getClass()) {
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
            successful[i] = success;

            // Build the expected String
            StringBuilder mBuilder = new StringBuilder();
            mBuilder.append(methodName);
            mBuilder.append("(");
            // If params length is one no need to travel deeper
            if(parameterTypes.length == 1) {
                Object curInput = inputs[i];
                // Check to make sure it's not itself an array
                if(isArray.apply(curInput)) {
                    mBuilder.append(Arrays.deepToString((Object[]) curInput));
                } else {
                    mBuilder.append(curInput);
                }
            } else if(parameterTypes.length > 1) {
                // Array detected, make sure it prints well
                Object[] inputsArr = (Object[]) inputs[i];
                for(int y = 0; y < inputsArr.length; ++y) {
                    Object subInput = inputsArr[y];
                    if(isArray.apply(subInput)) {
                        mBuilder.append(Arrays.deepToString((Object[]) subInput));
                    } else {
                        mBuilder.append(subInput.toString());
                    }
                    if(y != parameterTypes.length - 1) {
                        mBuilder.append(", ");
                    }
                }
            }
            mBuilder.append(") → ");
            if(isArray.apply(curExpectedResult)) {
                mBuilder.append(Arrays.deepToString((Object[]) curExpectedResult));
            } else {
                mBuilder.append(curExpectedResult.toString());
            }

            String expectedStr = mBuilder.toString();
            longestExpected = Math.max(longestExpected, expectedStr.length());
            expectedStrs[i] = expectedStr;

            // Build to actual String
            String actualStr = curActualResult.toString();
            if(isArray.apply(curActualResult)) {
                actualStr = Arrays.deepToString((Object[]) curActualResult);
            }
            actualStrs[i] = actualStr;
            longestActual = Math.max(longestActual, actualStr.length());
        }

        // Post process the expected and actual Strings for length
        for(int i = 0; i < actualResults.length; ++i) {
            String curExpected = expectedStrs[i];
            int expectedDifference = longestExpected - curExpected.length();
            StringBuilder expectedBuilder = new StringBuilder();
            expectedBuilder.append(curExpected);
            for(int x = 0; x < expectedDifference; ++x) {
                expectedBuilder.append(" ");
            }
            expectedStrs[i] = expectedBuilder.toString();

            String curActual = actualStrs[i];
            int actualDifference = longestActual - curActual.length();
            StringBuilder actualBuilder = new StringBuilder();
            actualBuilder.append(curActual);
            for(int x = 0; x < actualDifference; ++x) {
                actualBuilder.append(" ");
            }
            actualStrs[i] = actualBuilder.toString();
        }

        // Create the output message
        StringBuilder outputBuilder = new StringBuilder();
        String expectedStr = "Expected";
        int expectedDifference = (int) Math.ceil((longestExpected - expectedStr.length()) / 2.0);
        StringBuilder expectedSpacingBuilder = new StringBuilder();
        for(int i = 0; i < expectedDifference; ++i) {
            expectedSpacingBuilder.append(" ");
        }
        String expectedSpacing = expectedSpacingBuilder.toString();
        outputBuilder.append("  ").append(expectedSpacing).append(expectedStr).append(expectedSpacing);
        String runStr = "Run";
        int runDifference = (int) Math.ceil((longestActual - runStr.length()) / 2.0);
        StringBuilder actualSpacingBuilder = new StringBuilder();
        for(int i = 0; i < runDifference; ++i) {
            actualSpacingBuilder.append(" ");
        }
        String actualSpacing = actualSpacingBuilder.toString();
        outputBuilder.append("  ").append(actualSpacing).append(runStr);
        outputBuilder.append("\n");
        for(int i = 0; i < actualResults.length; ++i) {
            String curExpected = expectedStrs[i];
            String curActual = actualStrs[i];
            String curSuccess = successful[i] ? "OK" : "X ";

            outputBuilder.append("| ").append(curExpected).append(" | ").append(curActual).append(" | ").append(curSuccess).append(" |\n");
        }

        // Calculate the amount correct
        int total = actualResults.length;
        int correct = 0;
        for(int i = 0; i < successful.length; ++i) {
            boolean cur = successful[i];
            if(cur) ++correct;
        }

        if(correct == total) outputBuilder.append("All Correct");
        else if(correct > (total / 2)) outputBuilder.append("Correct for more than half the tests");

        String toPrint = outputBuilder.toString();
        // Print the output message
        System.out.println(toPrint);

        return total == correct;
    }
}
