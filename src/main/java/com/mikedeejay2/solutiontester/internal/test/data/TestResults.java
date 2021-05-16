package com.mikedeejay2.solutiontester.internal.test.data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestResults {
    public final boolean success;
    public final int total;
    public final int passed;
    public final int failed;
    public final List<String> ids;
    public final Map<String, TestResult> results;

    public static class TestResult {
        public final boolean success;
        public final int total;
        public final int passed;
        public final int failed;
        public final String id;
        public final List<Object[][]> inputs;
        public final Object[] results;
        public final List<Object> solutions;
        public final List<String> methodNames;
        public final List<Boolean> hasPassed;

        public TestResult(
            boolean success,
            int total,
            int passed,
            int failed,
            String id,
            List<Object[][]> inputs,
            Object[] results,
            List<Object> solutions,
            List<String> methodNames,
            List<Boolean> hasPassed)
        {
            this.success = success;
            this.total = total;
            this.passed = passed;
            this.failed = failed;
            this.id = id;
            this.inputs = inputs;
            this.results = results;
            this.solutions = solutions;
            this.methodNames = methodNames;
            this.hasPassed = hasPassed;
        }

        @Override
        public String toString()
        {
            return "TestResult{" +
                "\nsuccess=" + success +
                ", \ntotal=" + total +
                ", \npassed=" + passed +
                ", \nfailed=" + failed +
                ", \nid='" + id + '\'' +
                ", \ninputs=" + inputs +
                ", \nresults=" + Arrays.toString(results) +
                ", \nsolutions=" + solutions +
                ", \nmethodNames=" + methodNames +
                ", \nhasPassed=" + hasPassed +
                '}';
        }
    }

    public TestResults(
        boolean success,
        int total,
        int passed,
        int failed,
        List<String> ids,
        Map<String, TestResult> results) {
        this.success = success;
        this.total = total;
        this.passed = passed;
        this.failed = failed;
        this.ids = ids;
        this.results = results;
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

    public Map<String, TestResult> getResults() {
        return results;
    }

    @Override
    public String toString()
    {
        return "TestResults{" +
            "\nsuccess=" + success +
            ", \ntotal=" + total +
            ", \npassed=" + passed +
            ", \nfailed=" + failed +
            ", \nids=" + ids +
            ", \nresults=" + results +
            '}';
    }
}
