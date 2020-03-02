package com.example.javaparsonsproblems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(getApplicationContext().getFilesDir(),"studentLVLs.txt");
        if(!file.exists()){
            writeStudentLevels("1,1,1,1,1,1", this);
        } else writeStudentLevels("1,1,1,1,1,1", this);
    }

    public void startProblem(View view){
        Button clicked = (Button) view;
        String clickedText = clicked.getText().toString();
        Bundle ex = new Bundle();

        if (clickedText.equals("I/O")) {
            ArrayList<String> studentLevels = readStudentLevels(this);
            Student st = new Student(studentLevels);
            ex.putString("PROB_TOPIC", "IO");
            ex.putInt("TOPIC_LEVEL", st.ioLVL);
            int[] allLvls = {st.ioLVL,st.varLVL,st.conLVL,st.dsLVL,st.funLVL,st.oopLVL};
            ex.putIntArray("ALL_LEVELS", allLvls);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[0]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("VARIABLES")) {
            ArrayList<String> studentLevels = readStudentLevels(this);
            Student st = new Student(studentLevels);
            ex.putString("PROB_TOPIC", "VAR");
            ex.putInt("TOPIC_LEVEL", st.varLVL);
            int[] allLvls = {st.ioLVL,st.varLVL,st.conLVL,st.dsLVL,st.funLVL,st.oopLVL};
            ex.putIntArray("ALL_LEVELS", allLvls);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[1]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("CONTROL STRUCTURES")) {
            ArrayList<String> studentLevels = readStudentLevels(this);
            Student st = new Student(studentLevels);
            ex.putString("PROB_TOPIC", "CON");
            ex.putInt("TOPIC_LEVEL", st.conLVL);
            int[] allLvls = {st.ioLVL,st.varLVL,st.conLVL,st.dsLVL,st.funLVL,st.oopLVL};
            ex.putIntArray("ALL_LEVELS", allLvls);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[2]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("DATA STRUCTURES")) {
            ArrayList<String> studentLevels = readStudentLevels(this);
            Student st = new Student(studentLevels);
            ex.putString("PROB_TOPIC", "DS");
            ex.putInt("TOPIC_LEVEL", st.dsLVL);
            int[] allLvls = {st.ioLVL,st.varLVL,st.conLVL,st.dsLVL,st.funLVL,st.oopLVL};
            ex.putIntArray("ALL_LEVELS", allLvls);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[3]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("FUNCTIONS")) {
            ArrayList<String> studentLevels = readStudentLevels(this);
            Student st = new Student(studentLevels);
            ex.putString("PROB_TOPIC", "FUN");
            ex.putInt("TOPIC_LEVEL", st.funLVL);
            int[] allLvls = {st.ioLVL,st.varLVL,st.conLVL,st.dsLVL,st.funLVL,st.oopLVL};
            ex.putIntArray("ALL_LEVELS", allLvls);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[4]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);

        } else if (clickedText.equals("OBJECT ORIENTED PRINCIPLES")) {
            ArrayList<String> studentLevels = readStudentLevels(this);
            Student st = new Student(studentLevels);
            ex.putString("PROB_TOPIC", "OOP");
            ex.putInt("TOPIC_LEVEL", st.oopLVL);
            int[] allLvls = {st.ioLVL,st.varLVL,st.conLVL,st.dsLVL,st.funLVL,st.oopLVL};
            ex.putIntArray("ALL_LEVELS", allLvls);
            int[][] varMat = getVariantsMatrix();
            ex.putIntArray("VARS_MATRIX", varMat[5]);

            Intent problem = new Intent(this, ProblemActivity.class);
            problem.putExtras(ex);
            startActivity(problem);
        }
    }

    //should return the whole array instead
    public int[][] getVariantsMatrix(){
        int[][] lvlMatrix = {
   //skill level:1,2,3,4,5,6,7,8,9,10
                {3,2,1,2,1,1,1,1,1,1}, //IO
                {1,0,0,0,0,0,0,0,0,0}, //VAR
                {1,0,0,0,0,0,0,0,0,0}, //CON
                {1,0,0,0,0,0,0,0,0,0}, //DS
                {1,0,0,0,0,0,0,0,0,0}, //FUN
                {1,0,0,0,0,0,0,0,0,0}  //OOP
        };

        return lvlMatrix;
    }

    public ArrayList<String> readStudentLevels(Context context){


        String studentString = "";
        try {
            InputStream in = context.openFileInput("studentLVLs.txt");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            studentString = new String(buffer);
            in.close();
        } catch (Exception e){System.out.println(e);System.out.println("Failed to read student file");}

        ArrayList<String> levelsList = new ArrayList<String>(Arrays.asList(studentString.split(",")));
        return levelsList;
    }

    public void writeStudentLevels(String data, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("studentLVLs.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void showScores(View view){
        Intent scores = new Intent(this, ScoresActivity.class);
        Bundle bun = new Bundle();
        ArrayList<String> lvls = readStudentLevels(this);
        bun.putStringArrayList("LEVELS", lvls);
        scores.putExtras(bun);
        startActivity(scores);
    }
}