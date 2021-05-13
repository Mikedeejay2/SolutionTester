package com.mikedeejay2.solutiontester.internal.test;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.test.data.TestResults;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class SolutionTester implements Function<SolutionTest, TestResults> {
    protected SolutionPrinter printer;

    public SolutionTester() {
        this.printer = new SolutionPrinter();
    }

    public SolutionTester(SolutionPrinter printer) {
        this.printer = printer;
    }

    private TestResults solve(SolutionTest solutionTest) throws ExecutionException, InterruptedException {
        CompletableFuture<TestResults> testFuture = CompletableFuture.supplyAsync(solutionTest._toSolver());
        CompletableFuture<Void> printFuture = testFuture.thenAccept(printer);

        printFuture.get();
        return testFuture.getNow(null);
    }

    @Override
    public TestResults apply(SolutionTest solutionTest) {
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
