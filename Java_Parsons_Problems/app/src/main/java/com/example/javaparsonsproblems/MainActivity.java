package com.example.javaparsonsproblems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

/**
 * Class for the 'Home Screen' activity. The screen the user is presented with on launching the app.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Initialises the 'Home Screen' activity by setting the content view and fetching the
     * studentLVLs.txt file or creating it if does not exist.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(getApplicationContext().getFilesDir(),"studentLVLs.txt");
        if(!file.exists()){
            writeStudentLevels("1,1,1,1,1,1", this);
        }
    }

    /**
     * Method which starts a problem for a given topic.
     * @param view The clicked start problem button.
     */
    public void startProblem(View view){
        MediaPlayer click = MediaPlayer.create(MainActivity.this,R.raw.click); click.start();
        Button clicked = (Button) view;
        String clickedText = clicked.getText().toString();
        Bundle ex = new Bundle();

        if (clickedText.equals("INPUT/OUTPUT")) {
            String studentLevels = readStudentLevels(this);
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
            String studentLevels = readStudentLevels(this);
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
            String studentLevels = readStudentLevels(this);
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
            String studentLevels = readStudentLevels(this);
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
            String studentLevels = readStudentLevels(this);
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

        } else if (clickedText.equals("OBJECT-ORIENTED PRINCIPLES")) {
            String studentLevels = readStudentLevels(this);
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

    /**
     * Maintains the number of variations of problems for any given topic and skill level. Needed
     * for the purpose of retrieving a random question for any given topic and skill level
     * combination.
     * @return 2-D matrix describing the number of variations of problems for any given
     * [topic] and [skill level].
     */
    public int[][] getVariantsMatrix(){
        int[][] lvlMatrix = {
   //skill level:1,2,3,4,5,6,7,8,9,10
                {3,3,3,3,3,3,3,3,3,3}, //IO
                {1,0,0,0,0,0,0,0,0,0}, //VAR
                {1,0,0,0,0,0,0,0,0,0}, //CON
                {1,0,0,0,0,0,0,0,0,0}, //DS
                {1,0,0,0,0,0,0,0,0,0}, //FUN
                {1,0,0,0,0,0,0,0,0,0}  //OOP
        };

        return lvlMatrix;
    }

    /**
     * Method to read the student's topic scores from file. Adapted from https://www.dev2qa.com/android-read-write-internal-storage-file-example/
     * @param context The context from which the the file is read.
     * @return The string content of the file containing the student levels.
     */
    public String readStudentLevels(Context context){
        String studentString = "";
        try {
            InputStream in = context.openFileInput("studentLVLs.txt");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            studentString = new String(buffer);
            in.close();
        } catch (Exception e){System.out.println(e);System.out.println("Failed to read student file");}


        return studentString;
    }

    /**
     * Method to write the student's scores to file. Adapted from: https://www.dev2qa.com/android-read-write-internal-storage-file-example/
     * @param data The string representation of the students scores for each topic. Comma (,)
     *             separated values for each topic progressing in the order as in activity scores
     *             layout.
     * @param context The context to which the the file is written.
     */
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

    /**
     * Method to launch the 'Scores' activity.
     * @param view The 'SCORES' button on the home screen.
     */
    public void showScores(View view){
        MediaPlayer click = MediaPlayer.create(MainActivity.this,R.raw.click); click.start();
        Intent scores = new Intent(this, ScoresActivity.class);
        Bundle bun = new Bundle();
        String stuLvls = readStudentLevels(this);
        Student stu = new Student(stuLvls);
        int[] lvls = {stu.ioLVL, stu.varLVL, stu.conLVL, stu.dsLVL, stu.funLVL, stu.oopLVL};
        bun.putIntArray("LEVELS", lvls);
        scores.putExtras(bun);
        startActivity(scores);
    }

    @Override
    public void onBackPressed() {
        MediaPlayer click = MediaPlayer.create(MainActivity.this,R.raw.click); click.start();
        super.onBackPressed();
    }
}