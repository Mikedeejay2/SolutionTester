package com.mikedeejay2.solutiontester.internal.test;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.test.data.TestResults;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class SolutionTester implements Function<SolutionTest, CompletableFuture<TestResults>> {
    protected SolutionPrinter printer;

    public SolutionTester() {
        this.printer = new SolutionPrinter(System.out);
    }

    public SolutionTester(@Nullable SolutionPrinter printer) {
        this.printer = printer;
    }

    private CompletableFuture<TestResults> solve(@NotNull final SolutionTest solutionTest) {
        final CompletableFuture<TestResults> testFuture = CompletableFuture.supplyAsync(solutionTest._toSolver());
        if(printer != null) testFuture.thenAccept(printer);

        return testFuture;
    }

    @Override
    public CompletableFuture<TestResults> apply(@NotNull final SolutionTest solutionTest) {
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
