package com.example.javaparsonsproblems;

import java.util.Scanner;

public class Student {

    int ioLVL;
    int varLVL;
    int conLVL;
    int dsLVL;
    int funLVL;
    int oopLVL;

    public Student(String in){
        try{
            Scanner input = new Scanner(in);

            while (input.hasNextLine()){
                String ioS = input.nextLine();
                ioLVL = Integer.parseInt(ioS);
                String varS = input.nextLine();
                varLVL = Integer.parseInt(varS);
                String conS = input.nextLine();
                conLVL = Integer.parseInt(conS);
                String dsS = input.nextLine();
                dsLVL = Integer.parseInt(dsS);
                String funS = input.nextLine();
                funLVL = Integer.parseInt(funS);
                String oopS = input.nextLine();
                oopLVL = Integer.parseInt(oopS);
            }
            input.close();

        } catch (Exception e) {System.out.println(e);}
    }
}
