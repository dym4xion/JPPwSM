package com.example.javaparsonsproblems;

import org.junit.Test;

import java.io.InputStream;

public class StudentTest {
    @Test
    public void TestStudent(){
        String studentString = "1\n2\n3\n4\n5\n6";


        Student st = new Student(studentString);
        System.out.println(st.ioLVL);
        System.out.println(st.varLVL);
        System.out.println(st.conLVL);
        System.out.println(st.dsLVL);
        System.out.println(st.funLVL);
        System.out.println(st.oopLVL);

    }
}
