package com.mikedeejay2.solutiontester.internal;

import com.mikedeejay2.solutiontester.internal.test.SolutionPrinter;
import com.mikedeejay2.solutiontester.internal.test.SolutionTestSolver;
import com.mikedeejay2.solutiontester.internal.test.SolutionTester;
import com.mikedeejay2.solutiontester.internal.test.data.TestResults;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface SolutionTest
{
    @Test
    default void testJUnit() throws ExecutionException, InterruptedException {
        SolutionTester tester = new SolutionTester();
        CompletableFuture<TestResults> resultsFuture = tester.apply(this);
        TestResults results = resultsFuture.get();
        assertTrue(results.success, "Some tests failed. View above for test results.");

//        DeprecatedSolutionTester tester = new DeprecatedSolutionTester();
//        boolean success = tester.run(this);
//        assertTrue(success, "Some tests failed. View above for test results.");

//        SolutionTester tester = new SolutionTester(null);
//        CompletableFuture<TestResults> resultsFuture = tester.apply(this);
//        TestResults results = resultsFuture.get();
//        new SolutionPrinter(System.out).accept(results);
//        assertTrue(results.success, "Some tests failed. View above for test results.");
    }

    default SolutionTestSolver _toSolver() {
        return new SolutionTestSolver(this);
    }
}
