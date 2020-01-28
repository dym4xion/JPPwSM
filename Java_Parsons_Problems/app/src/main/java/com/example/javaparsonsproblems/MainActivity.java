package com.example.javaparsonsproblems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get student text and create student file, parsing student file not yet done!
//        String studentString = "";
//        try {
//
//            InputStream in = getAssets().open("_studentLVLs.txt");
//            int size = in.available();
//            byte[] buffer = new byte[size];
//            in.read(buffer);
//            studentString = new String(buffer);
//
//            in.close();
//        } catch (Exception e){System.out.println(e);System.out.println("Failed to read student file");}
//
//        Student st = new Student(studentString);
    }

    public void startProblem(View view){
        Intent problem = new Intent(this, ProblemActivity.class);
        startActivity(problem);
    }
}