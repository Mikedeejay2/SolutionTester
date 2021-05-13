package com.mikedeejay2.solutiontester.internal;

import com.mikedeejay2.solutiontester.internal.test.SolutionTestSolver;
import com.mikedeejay2.solutiontester.internal.test.SolutionTester;
import com.mikedeejay2.solutiontester.internal.test.data.TestResults;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface SolutionTest
{
    @Test
    default void testJUnit() {
        SolutionTester tester = new SolutionTester();
        TestResults results = tester.apply(this);
        assertTrue(false, "Some tests failed. View above for test results.");
    }

    default SolutionTestSolver _toSolver() {
        return new SolutionTestSolver(this);
    }
}
