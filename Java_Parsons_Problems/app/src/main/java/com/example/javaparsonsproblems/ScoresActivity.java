package com.example.javaparsonsproblems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        getSupportActionBar().setTitle("Scores");

        Intent thisProb = getIntent();
        Bundle bun = thisProb.getExtras();
        ArrayList<String> lvls = bun.getStringArrayList("LEVELS");
        // set scores text
        TextView io = findViewById(R.id.io_score_view);
        io.setText("I/O: " + lvls.get(0));
        TextView var = findViewById(R.id.var_score_view);
        var.setText("Variables: " + lvls.get(1));
        TextView con = findViewById(R.id.con_score_view);
        con.setText("Control Structures: " + lvls.get(2));
        TextView ds = findViewById(R.id.ds_score_view);
        ds.setText("Data Structures: " + lvls.get(3));
        TextView fun = findViewById(R.id.fun_score_view);
        fun.setText("Functions: " + lvls.get(4));
        TextView oop = findViewById(R.id.oop_score_view);
        oop.setText("Object Oriented Principles: " + lvls.get(5));
    }

    public void resetScores(View view){
        writeStudentLevels("1,1,1,1,1,1",this);
        TextView io = findViewById(R.id.io_score_view);
        io.setText("I/O: 1");
        TextView var = findViewById(R.id.var_score_view);
        var.setText("Variables: 1");
        TextView con = findViewById(R.id.con_score_view);
        con.setText("Control Structures: 1");
        TextView ds = findViewById(R.id.ds_score_view);
        ds.setText("Data Structures: 1");
        TextView fun = findViewById(R.id.fun_score_view);
        fun.setText("Functions: 1");
        TextView oop = findViewById(R.id.oop_score_view);
        oop.setText("Object Oriented Principles: 1");
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
}
