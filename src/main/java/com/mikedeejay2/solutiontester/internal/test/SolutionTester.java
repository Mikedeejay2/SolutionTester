package com.mikedeejay2.solutiontester.internal.test;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.test.data.TestResults;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class SolutionTester implements Function<SolutionTest, TestResults> {
    protected SolutionPrinter printer;

    public SolutionTester() {
        this.printer = new SolutionPrinter();
    }

    public SolutionTester(@Nullable SolutionPrinter printer) {
        this.printer = printer;
    }

    private TestResults solve(@NotNull final SolutionTest solutionTest) throws
        ExecutionException, InterruptedException {
        final CompletableFuture<TestResults> testFuture = CompletableFuture.supplyAsync(solutionTest._toSolver());
        final CompletableFuture<?> printFuture = printer == null ? testFuture : testFuture.thenAccept(printer);

        printFuture.get();
        return testFuture.getNow(null);
    }

    @Override
    public TestResults apply(@NotNull final SolutionTest solutionTest) {
        try {
            return solve(solutionTest);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SolutionPrinter getPrinter() {
        return printer;
    }

    public void setPrinter(SolutionPrinter printer) {
        this.printer = printer;
    }
}
