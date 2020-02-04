package com.example.javaparsonsproblems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Student st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get student text and create student file, parsing student file not yet done!
        String studentString = "";
        try {

            InputStream in = getAssets().open("_studentLVLs.txt");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            studentString = new String(buffer);

            in.close();
        } catch (Exception e){System.out.println(e);System.out.println("Failed to read student file");}

        st = new Student(studentString);


    }

    public void startProblem(View view){
        Button clicked = (Button) view;
        String clickedText = clicked.getText().toString();
        Bundle ex = new Bundle();

        if (clickedText.equals("I/O")) {
            ex.putString("PROB_TOPIC", "IO");
            ex.putInt("TOPIC_LEVEL", st.ioLVL);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[0]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("VARIABLES")) {
            ex.putString("PROB_TOPIC", "VAR");
            ex.putInt("TOPIC_LEVEL", st.varLVL);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[1]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("CONTROL STRUCTURES")) {
            ex.putString("PROB_TOPIC", "CON");
            ex.putInt("TOPIC_LEVEL", st.conLVL);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[2]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("DATA STRUCTURES")) {
            ex.putString("PROB_TOPIC", "DS");
            ex.putInt("TOPIC_LEVEL", st.dsLVL);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[3]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("FUNCTIONS")) {
            ex.putString("PROB_TOPIC", "FUN");
            ex.putInt("TOPIC_LEVEL", st.funLVL);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[4]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("OBJECT ORIENTED PRINCIPLES")) {
            ex.putString("PROB_TOPIC", "OOP");
            ex.putInt("TOPIC_LEVEL", st.oopLVL);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[5]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);
        }
    }


    //should return the whole arry instead
    public int[][] getVariantsMatrix(){
        int[][] lvlMatrix = {
   //skill level:1,2,3,4,5,6,7,8,9,10
                {3,1,1,0,0,0,0,0,0,0}, //IO
                {1,0,0,0,0,0,0,0,0,0}, //VAR
                {1,0,0,0,0,0,0,0,0,0}, //CON
                {1,0,0,0,0,0,0,0,0,0}, //DS
                {1,0,0,0,0,0,0,0,0,0}, //FUN
                {1,0,0,0,0,0,0,0,0,0}  //OOP
        };

        return lvlMatrix;
    }
}