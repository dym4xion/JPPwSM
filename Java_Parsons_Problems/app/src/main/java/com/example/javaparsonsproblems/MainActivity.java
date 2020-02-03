package com.example.javaparsonsproblems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Integer> stdLVLs = new ArrayList<>();

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

        Student st = new Student(studentString);
        stdLVLs.add(st.ioLVL);
        stdLVLs.add(st.varLVL);
        stdLVLs.add(st.conLVL);
        stdLVLs.add(st.dsLVL);
        stdLVLs.add(st.funLVL);
        stdLVLs.add(st.oopLVL);

    }

    public void startProblem(View view){
        Button clicked = (Button) view;
        String clickedText = clicked.getText().toString();
        Bundle ex = new Bundle();

        if (clickedText.equals("I/O")) {
            ex.putString("PROB_TOPIC", "IO");
            ex.putInt("TOPIC_LEVEL", stdLVLs.get(0));
            ex.putInt("PROB_VARS", getNumProbVariants(0, stdLVLs.get(0)));

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("VARIABLES")) {
            ex.putString("PROB_TOPIC", "VAR");
            ex.putInt("TOPIC_LEVEL", stdLVLs.get(1));
            ex.putInt("PROB_VARS", getNumProbVariants(1, stdLVLs.get(1)));

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("CONTROL STRUCTURES")) {
            ex.putString("PROB_TOPIC", "CON");
            ex.putInt("TOPIC_LEVEL", stdLVLs.get(2));
            ex.putInt("PROB_VARS", getNumProbVariants(2, stdLVLs.get(2)));

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("DATA STRUCTURES")) {
            ex.putString("PROB_TOPIC", "DS");
            ex.putInt("TOPIC_LEVEL", stdLVLs.get(3));
            ex.putInt("PROB_VARS", getNumProbVariants(3, stdLVLs.get(3)));

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("FUNCTIONS")) {
            ex.putString("PROB_TOPIC", "FUN");
            ex.putInt("TOPIC_LEVEL", stdLVLs.get(4));
            ex.putInt("PROB_VARS", getNumProbVariants(4, stdLVLs.get(4)));

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("OBJECT ORIENTED PRINCIPLES")) {
            ex.putString("PROB_TOPIC", "OOP");
            ex.putInt("TOPIC_LEVEL", stdLVLs.get(5));
            ex.putInt("PROB_VARS", getNumProbVariants(5, stdLVLs.get(5)));

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);
        }
    }

    public int getNumProbVariants(int topicNum, int skill){
        int[][] lvlMatrix = {
                {3,0,0,0,0,0,0,0,0,0}, //IO
                {1,0,0,0,0,0,0,0,0,0}, //VAR
                {1,0,0,0,0,0,0,0,0,0}, //CON
                {1,0,0,0,0,0,0,0,0,0}, //DS
                {1,0,0,0,0,0,0,0,0,0}, //FUN
                {1,0,0,0,0,0,0,0,0,0}  //OOP
        };

        return lvlMatrix[topicNum][skill - 1];
    }
}