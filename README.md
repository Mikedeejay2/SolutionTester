# SolutionTester
A Java solution tester for easy testing of practice problems or algorithms.

### Uses
SolutionTester was developed for use with online code practice websites such as CodingBat or LeetCode wherein a set 
of inputs are given with the objective to be solved by a method. However, any situation where a set of inputs and 
expected results are given is applicable for use with this
solution tester.

### How to use
All testing classes need to implement the `SolutionTest` interface. The `SolutionTest` interface is the base interface 
of all tests, including base JUnit testing code and helper methods. No methods in `SolutionTest` need to be implemented.

There are 3 important annotations that must be individually put on separate methods:
* `Inputs` - The inputs annotation for specifying inputs to the solution. The inputs method must return an `Object[][]`,
  depth 1 being each individual array of parameters (aka 1 test), depth 2 being each individual argument that will be 
  sent to the `Solution` annotation.

* `Results` - The results annotation for specifying the results to the solution. The results method must return an 
  `Object[]`, each array index being the expected result of the index of the inputs.

* `Solution` - The solution annotation for specifying the methods that will solve the inputs to match the results. The 
  amount of arguments of the solution should match the length of all the depth 2 input arrays, since `Inputs[n/a][X]` 
  is the input of argument #`X`.

An example class correctly implementing `SolutionTest` can be seen 
[here](https://github.com/Mikedeejay2/SolutionTesterExample/blob/master/src/main/java/com/mikedeejay2/example/ExampleSolution.java).


All technical information and details are further described in Javadocs.

### Setup
When using SolutionTester, the recommended option of use is through [Apache Maven](https://maven.apache.org/).

#### Template
A template project can be found [here](https://github.com/Mikedeejay2/SolutionTesterExample). After cloning or 
downloading, everything should be setup and ready for use.

#### Manual
Create a new Maven Project, then add the repository and dependency for SolutionTester.

The Maven repository for SolutionTester is:
```
<repository>
    <id>Mikedeejay2-Maven-Repo</id>
    <url>https://github.com/Mikedeejay2/Mikedeejay2-Maven-Repo/raw/master/</url>
</repository>
```

The dependency for SolutionTester is:
```
<dependency>
    <groupId>com.mikedeejay2</groupId>
    <artifactId>solutiontester</artifactId>
    <version>1.1.3</version>
</dependency>
```
### Notes
Javadocs are included with the source code. If you're unsure about something, the Javadocs could clear things up. If 
Javadocs are not of help, my Discord information can be found on my [Github profile](https://github.com/Mikedeejay2).

Developed in Intellij IDEA, compatibility with other IDEs is not ensured.

### Changelog
<table style="width: 100%">
  <thead>
    <th style="width: 20%">Date Released</th>
    <th style="width: 20%">Version</th>
    <th style="width: 60%">Changes</th>
  </thead>
  <tbody>
    <tr>
      <td style="vertical-align: top">9/14/2023</td>
      <td style="vertical-align: top"><b>1.1.4</b></td>
      <td style="vertical-align: top">
        <ul>
          <li>Updated to JUnit 5.10.0</li>
          <li>Updated to Jetbrains Annotations 24.0.1</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td style="vertical-align: top">10/9/2021</td>
      <td style="vertical-align: top"><b>1.1.3</b></td>
      <td style="vertical-align: top">
        <ul>
          <li>Fix ClassCastException when handling primitive result arrays</li>
          <li>Updated to JUnit 5.8.1</li>
          <li>Fix extra set of square brackets in expected results printing</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td style="vertical-align: top">9/17/2021</td>
      <td style="vertical-align: top"><b>1.1.2</b></td>
      <td style="vertical-align: top">
        <ul>
          <li>Fix NullPointerException during the scoring process</li>
          <li>Simplify printed stack trace when errors occur</li>
          <li>Updated to JUnit 5.8.0</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td style="vertical-align: top">8/19/2021</td>
      <td style="vertical-align: top"><b>1.1.1-SNAPSHOT</b></td>
      <td style="vertical-align: top">
        <ul>
          <li>Fix NullPointerException with null object during the printing process</li>
          <li>Updated to JUnit 5.7.2</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td style="vertical-align: top">8/18/2021</td>
      <td style="vertical-align: top"><b>1.1.0-SNAPSHOT</b></td>
      <td style="vertical-align: top">
        <ul>
          <li>Allow superclasses to declare testing methods</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td style="vertical-align: top">7/11/2021</td>
      <td style="vertical-align: top"><b>1.0.0-SNAPSHOT</b></td>
      <td style="vertical-align: top">
        <ul>
          <li>Initial release. Completely new system from previous code</li>
        </ul>
      </td>
    </tr>
  </tbody>
</table>