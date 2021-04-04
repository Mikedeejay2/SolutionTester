package com.mikedeejay2.codingbatsolutions.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface CodingBatSolution {
    @Test
    default void testJUnit() {
        CodingBatRunner runner = new CodingBatRunner();
        boolean success = runner.run(this);
        assertTrue(success);
    }
}
