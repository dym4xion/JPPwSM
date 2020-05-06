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
        int[] attribArr = new int[6];

        // Default set all levels to 1 if student file does not separate values using 5 commas
        if(levels.size() != 6){
            for(int i = 0; i < 6; i++){
                attribArr[i] = 1;
            }
        } else{
            // This checks that the user levels are neither too high nor too low.
            for(int i = 0; i < 6; i++){
                // Check value can be parsed as an int, default to setting skill to 1 if not.
                try {
                    if (Integer.parseInt(levels.get(i)) > 10) attribArr[i] = 10;
                    else if (Integer.parseInt(levels.get(i)) < 1) attribArr[i] = 1;
                    else attribArr[i] = Integer.parseInt(levels.get(i));
                } catch (NumberFormatException e) {
                    attribArr[i] = 1;
                }
            }
        }

        ioLVL = attribArr[0];
        varLVL = attribArr[1];
        conLVL = attribArr[2];
        dsLVL = attribArr[3];
        funLVL = attribArr[4];
        oopLVL = attribArr[5];
    }
}