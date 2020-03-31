package com.example.javaparsonsproblems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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
        String ioS = String.format("%1$-" + 28 + "s", "Input/Output_").replace(' ', '=').replace('_',' ');
        io.setText(ioS + " " + lvls.get(0));
        TextView var = findViewById(R.id.var_score_view);
        String vS = String.format("%1$-" + 28 + "s", "Variables_").replace(' ', '=').replace('_',' ');
        var.setText(vS + " " + lvls.get(1));
        TextView con = findViewById(R.id.con_score_view);
        String cS = String.format("%1$-" + 28 + "s", "Control_Structures_").replace(' ', '=').replace('_',' ');
        con.setText(cS + " " + lvls.get(2));
        TextView ds = findViewById(R.id.ds_score_view);
        String dS = String.format("%1$-" + 28 + "s", "Data_Structures_").replace(' ', '=').replace('_',' ');
        ds.setText(dS +" " + lvls.get(3));
        TextView fun = findViewById(R.id.fun_score_view);
        String fS = String.format("%1$-" + 28 + "s", "Functions_").replace(' ', '=').replace('_',' ');
        fun.setText(fS + " " + lvls.get(4));
        TextView oop = findViewById(R.id.oop_score_view);
        String oS = String.format("%1$-" + 28 + "s", "Object-Oriented_Principles_").replace(' ', '=').replace('_',' ');
        oop.setText(oS + " " + lvls.get(5));

    }

    public void resetScores(View view){
        MediaPlayer woosh = MediaPlayer.create(ScoresActivity.this,R.raw.woosh); woosh.start();
        writeStudentLevels("1,1,1,1,1,1",this);
        TextView io = findViewById(R.id.io_score_view);
        io.setText(String.format("%1$-" + 28 + "s", "Input/Output_").replace(' ', '=').replace('_',' ') + " 1");
        TextView var = findViewById(R.id.var_score_view);
        var.setText(String.format("%1$-" + 28 + "s", "Variables_").replace(' ', '=').replace('_',' ') + " 1");
        TextView con = findViewById(R.id.con_score_view);
        con.setText(String.format("%1$-" + 28 + "s", "Control_Structures_").replace(' ', '=').replace('_',' ') + " 1");
        TextView ds = findViewById(R.id.ds_score_view);
        ds.setText(String.format("%1$-" + 28 + "s", "Data_Structures_").replace(' ', '=').replace('_',' ') + " 1");
        TextView fun = findViewById(R.id.fun_score_view);
        fun.setText(String.format("%1$-" + 28 + "s", "Functions_").replace(' ', '=').replace('_',' ') + " 1");
        TextView oop = findViewById(R.id.oop_score_view);
        oop.setText(String.format("%1$-" + 28 + "s", "Object-Oriented_Principles_").replace(' ', '=').replace('_',' ') + " 1");
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

    @Override
    public void onBackPressed() {
        MediaPlayer click = MediaPlayer.create(ScoresActivity.this,R.raw.click); click.start();
        super.onBackPressed();
    }
}
