package com.mikedeejay2.solutiontester.test;

import com.mikedeejay2.solutiontester.SolutionTest;
import com.mikedeejay2.solutiontester.test.data.TestResults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Tester for {@link SolutionTest} tests. Utilizes {@link SolutionTestSolver} and {@link SolutionPrinter} to test and
 * print results.
 * <p>
 * Use {@link SolutionTester#apply(SolutionTest)} for
 * inputting a <code>SolutionTest</code>.
 */
public class SolutionTester implements Function<SolutionTest, CompletableFuture<TestResults>> {
    /**
     * The {@link SolutionPrinter} to use. Can be null if not needed.
     */
    protected @Nullable SolutionPrinter printer;

    /**
     * Construct a new <code>SolutionTester</code> with the default <code>PrintStream</code> of {@link System#out}
     */
    public SolutionTester() {
        this.printer = new SolutionPrinter(System.out::println);
    }

    /**
     * Construct a new <code>SolutionTester</code> with a {@link SolutionPrinter} instance
     *
     * @param printer The {@link SolutionPrinter} to use. Nullable.
     */
    public SolutionTester(@Nullable SolutionPrinter printer) {
        this.printer = printer;
    }

    /**
     * Solve a {@link SolutionTest}
     *
     * @param solutionTest The <code>SolutionTest</code> to solve
     * @return The {@link CompletableFuture} of the {@link TestResults}
     */
    private CompletableFuture<TestResults> solve(@NotNull final SolutionTest solutionTest) {
        final CompletableFuture<TestResults> testFuture = CompletableFuture.supplyAsync(solutionTest._toSolver());
        if(printer != null) testFuture.thenAccept(printer);

        return testFuture;
    }

    /**
     * Solve a {@link SolutionTest}
     *
     * @param solutionTest The <code>SolutionTest</code> to solve
     * @return The {@link CompletableFuture} of the {@link TestResults}
     */
    @Override
    public CompletableFuture<TestResults> apply(@NotNull final SolutionTest solutionTest) {
        try {
            return solve(solutionTest);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the {@link SolutionPrinter} for this tester. Can be null.
     *
     * @return The <code>SolutionPrinter</code>
     */
    public @Nullable SolutionPrinter getPrinter() {
        return printer;
    }

    /**
     * Set the {@link SolutionPrinter} for this tester. Can be null.
     *
     * @param printer The new <code>SolutionPrinter</code>
     */
    public void setPrinter(@Nullable SolutionPrinter printer) {
        this.printer = printer;
    }

    /**
     * Gets result String if all processes have been completed
     *
     * @return Result String given that a printer exists and operations have finished
     */
    @Override
    public String toString() {
        return printer != null ? printer.toString() : super.toString();
    }
}
