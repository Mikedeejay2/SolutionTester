package com.mikedeejay2.solutiontester.internal.test;

import com.mikedeejay2.solutiontester.internal.test.data.TestResults;
import com.mikedeejay2.solutiontester.internal.util.SolveUtils;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Consumer;

public class SolutionPrinter implements Consumer<TestResults> {
    private static final char SEPARATION_CHAR = '│';
    private static final char ARROW_CHAR = '→';
    private static final String RESULT_SUCCESS = "OK";
    private static final String RESULT_FAILED = "X ";
    private static final String EXPECTED_HEADER = "Expected";
    private static final String RUN_HEADER = "Run";
    private static final String CORRECT_MESSAGE = "✓ All Correct";
    private static final String INCORRECT_MESSAGE = "✖ Some tests failed";

    protected PrintStream printer;

    public SolutionPrinter(@NotNull PrintStream printer) {
        Objects.requireNonNull(printer, "PrintStream cannot be null");
        this.printer = printer;
    }

    @Override
    public void accept(@NotNull TestResults testResults) {
        try {
            Objects.requireNonNull(testResults, "TestResults cannot be null");
            String message = toFullMessage(testResults);
            printOut(message);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String toFullMessage(@NotNull TestResults testResults) {
        final StringBuilder str = new StringBuilder();
        final List<String> strings = new ArrayList<>();

        for(TestResults.TestResult testResult : testResults.getResults().values()) {
            strings.add(toMessage(testResult, testResults.getIds().size() > 1));
        }

        for(String string : strings) {
            str.append('\n').append(string);
        }
        return str.toString();
    }

    public String toMessage(@NotNull final TestResults.TestResult testResult, final boolean appendID) {
        final List<String> expected = new ArrayList<>();
        final List<String> run = new ArrayList<>();
        final List<String> lines = new ArrayList<>();
        final List<String> pass = new ArrayList<>();
        int expectedLength = -1;
        int runLength = -1;

        if(appendID) {
            lines.add(String.format("Test ID \"%s\":", testResult.getId()));
        }

        for(int solutionI = 0; solutionI < testResult.getSolutions().size(); ++solutionI) {
            int resultsI = solutionI % testResult.getResults().length;
            Object[] rawInputs = getCorrectInput(testResult, solutionI);
            int methodNameMod = (solutionI / testResult.getResults().length) % testResult.getMethodNames().size();
            Object rawSolution = testResult.getSolutions().get(solutionI);
            Object rawResult = testResult.getResults()[resultsI];
            String methodName = testResult.getMethodNames().get(methodNameMod);
            String inputs = SolveUtils.quotedToString(rawInputs);
            String solution = SolveUtils.quotedToString(rawSolution);
            String result = SolveUtils.quotedToString(rawResult);
            String curExpected = methodName + "(" + inputs + ") " + ARROW_CHAR + " " + result;
            expected.add(curExpected);
            run.add(solution);
            expectedLength = Math.max(expectedLength, curExpected.length());
            runLength = Math.max(runLength, solution.length());
            pass.add(testResult.getHasPassed().get(solutionI) ? RESULT_SUCCESS : RESULT_FAILED);
        }

        adjustSpacing(expected, run, expectedLength, runLength);
        generateHeader(lines, expectedLength, runLength);
        generateLines(expected, run, lines, pass);
        generateEnd(testResult, lines);

        return String.join("\n", lines);
    }

    private void generateEnd(TestResults.TestResult testResult, List<String> lines) {
        lines.add("");
        if(testResult.isSuccess()) {
            lines.add(CORRECT_MESSAGE);
        } else {
            lines.add(INCORRECT_MESSAGE);
        }
        lines.add("");
    }

    private void generateLines(List<String> expected, List<String> run, List<String> lines, List<String> pass) {
        for(int i = 0; i < expected.size(); ++i) {
            String curExpected = expected.get(i);
            String curRun = run.get(i);
            String passedStr = pass.get(i);
            String curLine =
                SEPARATION_CHAR + " " + curExpected + " " +
                SEPARATION_CHAR + " " + curRun + " " +
                SEPARATION_CHAR + " " + passedStr + " " +
                SEPARATION_CHAR;
            lines.add(curLine);
        }
    }

    private void generateHeader(List<String> lines, int expectedLength, int runLength) {
        StringBuilder headerLine = new StringBuilder();
        int expectedSpaceLength = (expectedLength - EXPECTED_HEADER.length()) / 2;
        int runSpaceLength = (runLength - RUN_HEADER.length()) / 2;
        String spaceExpected = new String(new char[Math.max(expectedSpaceLength, 0)]).replace("\0", " ");
        String spaceRun = new String(new char[Math.max(runSpaceLength, 0)]).replace("\0", " ");
        String space1 = "  ";
        if(expectedSpaceLength < 0) {
            space1 = " ";
        }
        String space2 = "   ";
        if(expectedSpaceLength < 0) {
            if(runSpaceLength < 0) {
                space2 = " ";
            } else {
                space2 = "  ";
            }
        } else if(runSpaceLength < 0) {
            space2 = "  ";
        }
        String space3 = "   ";
        if(runSpaceLength < 0) {
            space3 = "  ";
        }

        headerLine
            .append(space1)
            .append(spaceExpected)
            .append(EXPECTED_HEADER)
            .append(spaceExpected)
            .append(expectedLength % 2 != EXPECTED_HEADER.length() % 2 && expectedSpaceLength != 0 ? " " : "")
            .append(space2)
            .append(spaceRun)
            .append(RUN_HEADER)
            .append(spaceRun)
            .append(runLength % 2 != RUN_HEADER.length() % 2 && runSpaceLength != 0 ? " " : "")
            .append(space3);

        lines.add(headerLine.toString());
    }

    private void adjustSpacing(List<String> expected, List<String> run, int expectedLength, int runLength) {
        for(int i = 0; i < expected.size(); ++i) {
            String curExpected = expected.get(i);
            String curRun = run.get(i);
            int exExpected = expectedLength - curExpected.length();
            int exRun = runLength - curRun.length();
            String newExpected = curExpected + new String(new char[exExpected]).replace("\0", " ");
            String newRun = curRun + new String(new char[exRun]).replace("\0", " ");
            expected.set(i, newExpected);
            run.set(i, newRun);
        }
    }

    private Object[] getCorrectInput(TestResults.TestResult testResult, int solutionI) {
        Object[] rawInputs;
        int i1 = 0;
        int i2 = 0;
        for(int i = 0; i < solutionI; ++i) {
            if(i2 < testResult.getInputs().get(i1).length - 1) {
                ++i2;
            } else {
                if(i1 < testResult.getInputs().size() - 1) {
                    ++i1;
                }
                i2 = 0;
            }
        }
        rawInputs = testResult.getInputs().get(i1)[i2];
        return rawInputs;
    }

    public void printOut(String message) {
        printer.println(message);
    }
}
