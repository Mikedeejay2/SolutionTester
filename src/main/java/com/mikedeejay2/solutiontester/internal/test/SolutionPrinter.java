package com.mikedeejay2.solutiontester.internal.test;

import com.mikedeejay2.solutiontester.internal.test.data.TestResults;

import java.util.function.Consumer;

public class SolutionPrinter implements Consumer<TestResults> {
    @Override
    public void accept(TestResults testResults) {
        System.out.println("TestResults: " + testResults);
    }
}
