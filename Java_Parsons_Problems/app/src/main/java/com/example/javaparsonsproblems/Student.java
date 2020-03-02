package com.example.javaparsonsproblems;

import java.util.ArrayList;
import java.util.Scanner;

public class Student {

    int ioLVL;
    int varLVL;
    int conLVL;
    int dsLVL;
    int funLVL;
    int oopLVL;

    public Student(ArrayList<String> levels){
        ioLVL = Integer.parseInt(levels.get(0));
        varLVL = Integer.parseInt(levels.get(1));
        conLVL = Integer.parseInt(levels.get(2));
        dsLVL = Integer.parseInt(levels.get(3));
        funLVL = Integer.parseInt(levels.get(4));
        oopLVL = Integer.parseInt(levels.get(5));
    }
}
