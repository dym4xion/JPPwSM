package com.example.javaparsonsproblems;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to represent the student and their scores.
 */
public class Student {

    int ioLVL;
    int varLVL;
    int conLVL;
    int dsLVL;
    int funLVL;
    int oopLVL;

    /**
     * Constructor. Splits scores string into ArrayList on commas in the student scores string. Then
     * assigns instance variables.
     * @param stuString Student scores string.
     */
    public Student(String stuString){
        ArrayList<String> levels = new ArrayList<String>(Arrays.asList(stuString.split(",")));
        ioLVL = Integer.parseInt(levels.get(0));
        varLVL = Integer.parseInt(levels.get(1));
        conLVL = Integer.parseInt(levels.get(2));
        dsLVL = Integer.parseInt(levels.get(3));
        funLVL = Integer.parseInt(levels.get(4));
        oopLVL = Integer.parseInt(levels.get(5));
    }
}