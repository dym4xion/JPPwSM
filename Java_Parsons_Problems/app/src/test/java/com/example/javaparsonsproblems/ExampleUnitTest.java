package com.example.javaparsonsproblems;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void ParsonsProblemPrompt(){
        ParsonsProblem pp = new ParsonsProblem("IO_01_01.txt");

        String expectedPrompt = "Arrange the necessary lines to define a method which outputs the strings \"Hello\" and \"World!\" on separate lines.";

        assertEquals(expectedPrompt, pp.prompt);
    }

    @Test
    public void ParsonsProblemValidLines(){
        ParsonsProblem pp = new ParsonsProblem("IO_01_01.txt");

        List<String> expectedVLs = new ArrayList<>();
        expectedVLs.add("public static void main(String[] args){");
        expectedVLs.add("    System.out.println(\"Hello\");");
        expectedVLs.add("    System.out.println(\"World!\");");
        expectedVLs.add("}");

        assertEquals(expectedVLs.get(0), pp.validLines.get(0));
        assertEquals(expectedVLs.get(1), pp.validLines.get(1));
        assertEquals(expectedVLs.get(2), pp.validLines.get(2));
        assertEquals(expectedVLs.get(3), pp.validLines.get(3));
    }

    @Test
    public void ParsonsProblemDistractors(){
        ParsonsProblem pp = new ParsonsProblem("IO_01_01.txt");

        List<String> expectedDistractors = new ArrayList<>();
        expectedDistractors.add("    System.out.println(\"World\")");
        expectedDistractors.add("    System.out.print(\"Hello\");");

        assertEquals(expectedDistractors.get(0), pp.distractors.get(0));
        assertEquals(expectedDistractors.get(1), pp.distractors.get(1));
    }
}