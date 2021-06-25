package com.mikedeejay2.solutiontester.internal.debug;

import com.mikedeejay2.solutiontester.internal.test.SolutionTester;

public class DebugMain
{
    public static void main(String[] args) {
        SolutionTester tester = new SolutionTester();
        tester.apply(new DebugSolution());
    }
}
