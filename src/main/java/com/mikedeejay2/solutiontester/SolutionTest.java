package com.mikedeejay2.solutiontester;

import com.mikedeejay2.solutiontester.test.SolutionTestSolver;
import com.mikedeejay2.solutiontester.test.SolutionTester;
import com.mikedeejay2.solutiontester.test.data.TestResults;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface SolutionTest
{
    @Test
    default void testJUnit() throws ExecutionException, InterruptedException {
        CompletableFuture<TestResults> resultsFuture = _toTester().apply(this);
        TestResults results = resultsFuture.get();
        assertTrue(results.success, "Some tests failed. View above for test results.");
    }

    default SolutionTestSolver _toSolver() {
        return new SolutionTestSolver(this);
    }

    default SolutionTester _toTester() {
        return new SolutionTester();
    }
}
