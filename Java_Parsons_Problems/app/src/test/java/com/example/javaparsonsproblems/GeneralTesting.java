package com.example.javaparsonsproblems;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeneralTesting {

    @Test // Test ID: 01
    public void getVarsTest(){
        MainActivity ma = new MainActivity();
        int[][] eVM = {
                {3,3,3,3,3,3,3,3,3,3},
                {1,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0,0,0}
        };

        int[][] aVM = ma.getVariantsMatrix();

        assertArrayEquals(eVM,aVM);
    }

    @Test // Test ID: 02
    public void parsonsProblemObjectTestExpected(){
        String problemStr = "[prompt]\nTesting Prompt\n"
                + "[valid lines]\nline1\nline2\nline3\n"
                + "[distractors]\ndist1\ndist2\n[end]";

        ParsonsProblem pp = new ParsonsProblem(problemStr);

        assertEquals("Testing Prompt", pp.prompt);
        assertEquals("line1", pp.validLines.get(0));
        assertEquals("line2", pp.validLines.get(1));
        assertEquals("line3", pp.validLines.get(2));
        assertEquals("dist1", pp.distractors.get(0));
        assertEquals("dist2", pp.distractors.get(1));
    }

    @Test // Test ID: 03
    public void parsonsProblemObjectAnomalous(){
        String problemStr = "";

        ParsonsProblem pp = new ParsonsProblem(problemStr);

        assertNull(pp.prompt);
        assertNull(pp.validLines);
        assertNull(pp.distractors);
    }

    @Test // Test ID: 04
    public void parsonsProblemObjectAnomalous2(){
        String problemStr = "[prompt]\n[valid lines]\n[distractors]\n[end]";

        ParsonsProblem pp = new ParsonsProblem(problemStr);

        assertEquals("", pp.prompt);
        assertEquals(0, pp.validLines.size());
        assertEquals(0, pp.distractors.size());
    }

    @Test // Test ID: 05
    public void studentObjectTestExpected(){
        String stuStr = "1,2,3,4,5,6";
        Student stu = new Student(stuStr);
        assertEquals(1, stu.ioLVL);
        assertEquals(2, stu.varLVL);
        assertEquals(3, stu.conLVL);
        assertEquals(4, stu.dsLVL);
        assertEquals(5, stu.funLVL);
        assertEquals(6, stu.oopLVL);
    }

    @Test // Test ID: 06
    public void studentObjectTestAnomalous(){
        String stuStr = "0,0,0,-1,-2,-3";
        Student stu = new Student(stuStr);
        assertEquals(1, stu.ioLVL);
        assertEquals(1, stu.varLVL);
        assertEquals(1, stu.conLVL);
        assertEquals(1, stu.dsLVL);
        assertEquals(1, stu.funLVL);
        assertEquals(1, stu.oopLVL);
    }

    @Test // Test ID: 07
    public void studentObjectTestAnomalous2(){
        String stuStr = "11,22,33,44,55,66";
        Student stu = new Student(stuStr);
        assertEquals(10, stu.ioLVL);
        assertEquals(10, stu.varLVL);
        assertEquals(10, stu.conLVL);
        assertEquals(10, stu.dsLVL);
        assertEquals(10, stu.funLVL);
        assertEquals(10, stu.oopLVL);
    }

    @Test // Test ID: 08
    public void studentObjectTestAnomalous3(){
        String stuStr = "-1,-2,0,11,22,66";
        Student stu = new Student(stuStr);
        assertEquals(1, stu.ioLVL);
        assertEquals(1, stu.varLVL);
        assertEquals(1, stu.conLVL);
        assertEquals(10, stu.dsLVL);
        assertEquals(10, stu.funLVL);
        assertEquals(10, stu.oopLVL);
    }

    @Test // Test ID: 09
    public void studentObjectTestAnomalous4(){
        String stuStr = "-1,0,1,5,10,11";
        Student stu = new Student(stuStr);
        assertEquals(1, stu.ioLVL);
        assertEquals(1, stu.varLVL);
        assertEquals(1, stu.conLVL);
        assertEquals(5, stu.dsLVL);
        assertEquals(10, stu.funLVL);
        assertEquals(10, stu.oopLVL);
    }

    @Test // Test ID: 10
    public void studentObjectTestAnomalous5(){
        String stuStr = "";
        Student stu = new Student(stuStr);
        assertEquals(1, stu.ioLVL);
        assertEquals(1, stu.varLVL);
        assertEquals(1, stu.conLVL);
        assertEquals(1, stu.dsLVL);
        assertEquals(1, stu.funLVL);
        assertEquals(1, stu.oopLVL);
    }

    @Test // Test ID: 11
    public void studentObjectTestAnomalous6(){
        String stuStr = "1,2,3,4,5";
        Student stu = new Student(stuStr);
        assertEquals(1, stu.ioLVL);
        assertEquals(1, stu.varLVL);
        assertEquals(1, stu.conLVL);
        assertEquals(1, stu.dsLVL);
        assertEquals(1, stu.funLVL);
        assertEquals(1, stu.oopLVL);
    }

    @Test // Test ID: 12
    public void studentObjectTestAnomalous7(){
        String stuStr = ",,,,,";
        Student stu = new Student(stuStr);
        assertEquals(1, stu.ioLVL);
        assertEquals(1, stu.varLVL);
        assertEquals(1, stu.conLVL);
        assertEquals(1, stu.dsLVL);
        assertEquals(1, stu.funLVL);
        assertEquals(1, stu.oopLVL);
    }
}
