package com.example.javaparsonsproblems;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ProblemActivity extends AppCompatActivity implements View.OnDragListener, View.OnTouchListener {

    static ParsonsProblem instanceProblem;
    static int numProbLines;
    int dSkill = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
              this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}


        Intent thisProb = getIntent();
        Bundle topicExtras = thisProb.getExtras();
        String topic = topicExtras.getString("PROB_TOPIC");
        int skill = topicExtras.getInt("TOPIC_LEVEL");
        int probVars = topicExtras.getIntArray("VARS_MATRIX")[skill - 1];

        if (probVars == 0){
            setContentView(R.layout.no_problem);
            TextView t = findViewById(R.id.topic_view);
            t.setText(topic);
            TextView l = findViewById(R.id.level_view);
            l.setText(Integer.toString(skill));

        } else {
            ParsonsProblem pp = generateParsonsProblem(topic, skill, probVars);
            instanceProblem = pp;

            numProbLines = instanceProblem.validLines.size() +
                    instanceProblem.distractors.size();

            setContentView(R.layout.problem_layout);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Inflate the line_layout xml for every line in the problem
            LinearLayout giv = findViewById(R.id.given_layout);
            for(int i = 0; i < numProbLines; i++){
                TextView line = (TextView) inflater.inflate(R.layout.line_layout, giv, false);
                giv.addView(line);
            }

            Button nxt = findViewById(R.id.next_button);
            nxt.setVisibility(View.INVISIBLE);

            displayProblem(pp);

            //Tag all lines as draggable and set OCL
            LinearLayout givLay = findViewById(R.id.given_layout);
            for(int i=0; i<numProbLines; i++){
                TextView l = (TextView) givLay.getChildAt(i);
                l.setTag("DRAGGABLE TEXTVIEW");
                l.setOnTouchListener(this);
            }

            //Set drag event listeners for layouts
            findViewById(R.id.answer_layout).setOnDragListener(this);
            findViewById(R.id.given_layout).setOnDragListener(this);
        }
    }

    //Adapted from https://13mcec21.wordpress.com/2013/10/23/android-drag-and-drop/
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            return true;
        } else {
            return false;
        }
    }

    //Adapted from https://13mcec21.wordpress.com/2013/10/23/android-drag-and-drop/
    public boolean onDrag(View layoutview, DragEvent dragevent) {
        int action = dragevent.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                TextView view = (TextView) dragevent.getLocalState();
                LinearLayout owner = (LinearLayout) view.getParent();
                owner.removeView(view);
                LinearLayout container = (LinearLayout) layoutview;

                if(container.getId() != R.id.answer_layout && container.getId() != R.id.given_layout){
                    owner.addView(view);
                } else {
                    double yPos = dragevent.getY();
                    if(container.getChildCount() == 0) container.addView(view); // when target container is empty
                    else if (container.getChildAt(0).getY() > yPos) container.addView(view,0); // when new line is placed above highest line in container
                    else if (container.getChildAt(container.getChildCount()-1).getY() < yPos) container.addView(view); // when new line is placed below lowest line in container
                    else { // when new line is placed somewhere in the middle
                        boolean added = false;
                        int lineCount = 0;
                        while(!added){
                            if(container.getChildAt(lineCount).getY() > yPos) {
                                container.addView(view,lineCount);
                                added = true;
                            } else lineCount++;
                        }
                    }
                }


                LinearLayout ansL = findViewById(R.id.answer_layout);
                LinearLayout vwParent = (LinearLayout) view.getParent();
                if (vwParent == ansL){
                    view.setBackgroundColor(Color.WHITE);
                }
                else view.setBackgroundColor(getResources().getColor(R.color.colorLine));

                view.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            default:
                Log.e("Parson Problem Activity", "Something went wrong with the dragging and dropping");
                break;
        }
        return true;
    }

    public void displayProblem(ParsonsProblem pp){
        //Set prompt text
        TextView promptV = (TextView) findViewById(R.id.prompt_view);
        promptV.setText(pp.prompt);

        ArrayList<String> allLines = new ArrayList<>();
        allLines.addAll(pp.validLines);
        allLines.addAll(pp.distractors);
        Collections.shuffle(allLines);

        LinearLayout givLay = findViewById(R.id.given_layout);
        for(int i=0; i<numProbLines; i++){
            TextView l = (TextView) givLay.getChildAt(i);
            l.setText(allLines.get(i));
        }


        // try to figure out how to add each line dynamically
//        TextView lowestLV = lineV;
//        for (String line : allLines){
//            lowestLV.setText(line);
//
//            TextView newLine = new TextView(this);
//            newLine.setText(line);
//            lowestLV = newLine;
//        }

    }

    //This takes the topic string (IO,VAR,CON,DATA,FUN,OOP), user skill level for topic
    // and the number of problems of that difficulty for that topic.
    public ParsonsProblem generateParsonsProblem(String topic, int skill, int numOfProblems){
        String skillLvl = "0" + Integer.toString(skill);

        Random r = new Random();
        System.out.println(numOfProblems);
        String problemNum = "0" + Integer.toString(r.nextInt(numOfProblems) + 1);
        String filename = topic + "_" + skillLvl + "_" + problemNum + ".txt";

        String problemString = "";
        try {

            InputStream in = getAssets().open(filename);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            problemString = new String(buffer);

            in.close();
        } catch (Exception e){System.out.println(e);System.out.println("Failed to read file");}

        ParsonsProblem pp = new ParsonsProblem(problemString);
        return pp;
    }

    public void checkAnswer(View v){

        LinearLayout ansLay = findViewById(R.id.answer_layout);
        TextView promptV = findViewById(R.id.prompt_view);
        String promptS = instanceProblem.prompt;


        //Check correct number of lines are used
        if (ansLay.getChildCount() < instanceProblem.validLines.size()){
            dSkill -= 1;
            String newPrompt = promptS + "\n\nFEEDBACK: THE SOLUTION REQUIRES THE USE OF MORE LINES.";
            promptV.setText(newPrompt);
        } else if (ansLay.getChildCount() > instanceProblem.validLines.size()) {
            dSkill -= 1;
            String newPrompt = promptS + "\n\nFEEDBACK: THE SOLUTION REQUIRES FEWER LINES.";
            promptV.setText(newPrompt);
        } else{

            int correctCount = 0;
            int distractorCount = 0;
            //Marks each line on line by line basis against lines in the validLines ArrayList
            for (int i=0; i<instanceProblem.validLines.size(); i++){
                TextView l1 = (TextView) ansLay.getChildAt(i);
                CharSequence lineStr = l1.getText();
                if (lineStr.equals(instanceProblem.validLines.get(i))){
                    l1.setBackgroundColor(getResources().getColor(R.color.correctLine));
                    correctCount++;
                } else {
                    l1.setBackgroundColor(getResources().getColor(R.color.colorLine));
                    if (instanceProblem.distractors.contains(l1.getText())){
                        distractorCount++;
                    }
                }
            }

            //Attributes message based on answer attempt with correct number of lines.
            if (correctCount == instanceProblem.validLines.size()){
                dSkill += 1;
                String newPrompt = promptS + "\n\nFEEDBACK: CONGRATULATIONS! ALL LINES ARE CORRECT.";
                promptV.setText(newPrompt);

                // if level is less than 10, increment level and write new value to file


                //hide submit and reset and show next
                Button chk = findViewById(R.id.submit_button);
                chk.setVisibility(View.INVISIBLE);
                Button rst = findViewById(R.id.reset_button);
                rst.setVisibility(View.INVISIBLE);
                Button nxt = findViewById(R.id.next_button);
                nxt.setVisibility(View.VISIBLE);

            } else if (distractorCount > 0){
                dSkill -= 1;
                String newPrompt = promptS + "\n\nFEEDBACK: A DISTRACTOR LINE HAS BEEN USED. TRY A DIFFERENT LINE.";
                promptV.setText(newPrompt);
            } else {
                dSkill -= 1;
                String newPrompt = promptS + "\n\nFEEDBACK: CORRECT LINES USED IN WRONG ORDER.";
                promptV.setText(newPrompt);
            }
        }
    }

    public void resetLines(View v){
        LinearLayout ansLay = findViewById(R.id.answer_layout);
        LinearLayout givLay = findViewById(R.id.given_layout);

        int numAnsLines = ansLay.getChildCount();
        while (!(numAnsLines == 0)){
            TextView l = (TextView) ansLay.getChildAt(0);
            ansLay.removeView(l);
            l.setBackgroundColor(getResources().getColor(R.color.colorLine));
            givLay.addView(l);
            numAnsLines -= 1;
        }
    }

    public void nextProblem(View v){
        Intent newProblem = new Intent(this, ProblemActivity.class);
        newProblem.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        Intent thisProb = getIntent();
        Bundle topicExtras = thisProb.getExtras();
        String topic = topicExtras.getString("PROB_TOPIC");
        int skill = topicExtras.getInt("TOPIC_LEVEL");
        int[] probVars = topicExtras.getIntArray("VARS_MATRIX");

        Bundle newProb = new Bundle();
        newProb.putString("PROB_TOPIC", topic);
        if (skill + dSkill > 0 && skill + dSkill < 11) newProb.putInt("TOPIC_LEVEL", skill + dSkill);
        else if (skill + dSkill < 1) newProb.putInt("TOPIC_LEVEL", 1);
        else newProb.putInt("TOPIC_LEVEL", skill);
        newProb.putIntArray("VARS_MATRIX", probVars);

        newProblem.putExtras(newProb);
        startActivity(newProblem);
        finish();
    }
}