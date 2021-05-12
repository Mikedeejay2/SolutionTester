package com.mikedeejay2.solutiontester.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface SolutionTest
{
    @Test
    default void testJUnit() {
        DeprecatedSolutionTester runner = new DeprecatedSolutionTester();
        boolean success = runner.run(this);
        assertTrue(success, "Some tests failed. View above for test results.");
    }
}
