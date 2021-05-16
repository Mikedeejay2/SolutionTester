package com.mikedeejay2.solutiontester.internal.test.data;

import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.Map;

public class TestResults {
    public final boolean success;
    public final int total;
    public final int passed;
    public final int failed;
    public final List<String> ids;
    public final Map<String, List<Object[][]>> inputs;
    public final Map<String, Object[]> results;
    public final Map<String, List<Object>> solutions;
    public final Map<String, List<String>> methodNames;
    public final Map<String, List<Boolean>> hasPassed;

    public TestResults(
        boolean success,
        int total,
        int passed,
        int failed,
        @NotNull List<String> ids,
        @NotNull Map<String, List<Object[][]>> inputs,
        @NotNull Map<String, Object[]> results,
        @NotNull Map<String, List<Object>> solutions,
        @NotNull Map<String, List<String>> methodNames,
        @NotNull Map<String, List<Boolean>> hasPassed) {
        this.success = success;
        this.total = total;
        this.passed = passed;
        this.failed = failed;
        this.ids = ids;
        this.inputs = inputs;
        this.results = results;
        this.solutions = solutions;
        this.methodNames = methodNames;
        this.hasPassed = hasPassed;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getTotal() {
        return total;
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return failed;
    }

    public List<String> getIds() {
        return ids;
    }

    public Map<String, List<Object[][]>> getInputs() {
        return inputs;
    }

    public Map<String, Object[]> getResults() {
        return results;
    }

    public Map<String, List<Object>> getSolutions() {
        return solutions;
    }

    public Map<String, List<String>> getMethodNames() {
        return methodNames;
    }

    public Map<String, List<Boolean>> getHasPassed() {
        return hasPassed;
    }
}
