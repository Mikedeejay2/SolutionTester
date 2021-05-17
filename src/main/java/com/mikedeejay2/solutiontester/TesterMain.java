package com.mikedeejay2.solutiontester;

import com.mikedeejay2.solutiontester.internal.test.SolutionTester;

public class TesterMain {
    public static void main(String[] args) {
        SolutionTester tester = new SolutionTester();
        tester.apply(new TestSolution());
    }
}
