package com.mikedeejay2.solutiontester.debug;

import com.mikedeejay2.solutiontester.test.SolutionTester;

public class DebugMain
{
    public static void main(String[] args) {
        SolutionTester tester = new SolutionTester();
        tester.apply(new DebugSolution());
    }
}
