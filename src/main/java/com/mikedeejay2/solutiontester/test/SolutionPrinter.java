package com.mikedeejay2.solutiontester.test;

import com.mikedeejay2.solutiontester.test.data.TestResults;
import com.mikedeejay2.solutiontester.util.SolveUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

/**
 * Printer class for printing {@link TestResults} to a <code>Consumer&lt;String&gt;</code>.
 * <p>
 * The text formatting and layout of this printer class is inspired by the visuals of CodingBat's testing system.
 */
public class SolutionPrinter implements Consumer<TestResults> {
    /**
     * The character used for separating test results
     */
    private static final char SEPARATION_CHAR = '│';

    /**
     * The arrow character used for decorative purposes
     */
    private static final char ARROW_CHAR = '→';

    /**
     * String for when a result is successful
     */
    private static final String RESULT_SUCCESS = "OK";

    /**
     * String for when a result has failed
     */
    private static final String RESULT_FAILED = "X ";

    /**
     * The header text for the expected column
     */
    private static final String EXPECTED_HEADER = "Expected";

    /**
     * The header text for the run column
     */
    private static final String RUN_HEADER = "Run";

    /**
     * The final message for when the entire test is correct
     */
    private static final String CORRECT_MESSAGE = "✓ All Correct";

    /**
     * The final message for when the entire test is incorrect
     */
    private static final String INCORRECT_MESSAGE = "✖ Some tests failed";

    /**
     * Message used for printing the execution time.
     * <p>
     * {@link String#format(String, Object...)} is used to insert the time in ms, therefore the String must include
     * a {@code %s} once in the String.
     */
    private static final String TIME_MESSAGE = "Tests ran in %sms";

    /**
     * The String <code>Consumer</code> for printing the {@link SolutionPrinter#currentMessage}
     */
    protected @Nullable Consumer<String> printer;

    /**
     * The current message of the previously generated String
     */
    protected @Nullable String currentMessage;

    /**
     * The current {@link TestResults} of the previously generated message
     */
    protected @Nullable TestResults currentResults;

    /**
     * Construct a new <code>SolutionPrinter</code>
     *
     * @param printer The printing <code>Consumer</code>. Can be null.
     */
    public SolutionPrinter(@Nullable Consumer<String> printer) {
        this.printer = printer;
    }

    /**
     * Accept {@link TestResults} for generating a String and printing if {@link SolutionPrinter#printer} is not null.
     *
     * @param testResults The {@link TestResults} to process and print
     */
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

    /**
     * Generate a full message from {@link TestResults}
     *
     * @param testResults The <code>TestResults</code> to generate a String for
     * @return The generated String
     */
    public String toFullMessage(@NotNull TestResults testResults) {
        final StringBuilder str = new StringBuilder();
        final List<String> strings = new ArrayList<>();

        for(TestResults.TestResult testResult : testResults.getResults().values()) {
            strings.add(toMessage(testResult, testResults.getIds().size() > 1));
        }

        for(String string : strings) {
            str.append('\n').append(string);
        }
        currentMessage = str.toString();
        currentResults = testResults;
        return currentMessage;
    }

    /**
     * Generate a message from a single {@link TestResults.TestResult}
     *
     * @param testResult The <code>TestResult</code> to generate a String for
     * @param appendID Whether to append the ID to the message or not. ID does not matter if IDs are not being used.
     * @return The generated String
     */
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

        lines.add(String.format(TIME_MESSAGE, testResult.getTimeMS()));
        lines.add("");

        return String.join("\n", lines);
    }

    private void generateEnd(TestResults.TestResult testResult, List<String> lines) {
        lines.add("");
        if(testResult.isSuccess()) {
            lines.add(CORRECT_MESSAGE);
        } else {
            lines.add(INCORRECT_MESSAGE);
        }
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

    private void printOut(@NotNull final String message) {
        if(printer != null) {
            printer.accept(message);
        }
    }

    @Override
    public String toString() {
        return currentMessage;
    }

    public @Nullable String getCurrentMessage() {
        return currentMessage;
    }
}
