package com.example.javaparsonsproblems;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ProblemActivity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {

    static ParsonsProblem instanceProblem;
    static int numProbLines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
              this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}

        //ParsonsProblem pp = generateParsonsProblem("IO", 1, 3);
        ParsonsProblem pp = generateParsonsProblem("IO", 1, 3);
        instanceProblem = pp;

        numProbLines = instanceProblem.validLines.size() +
                       instanceProblem.distractors.size();

        // set layout content view based on number of problem lines
        // simpler than doing any computational xml manipulation (as far as i know)
        if (numProbLines == 3) setContentView(R.layout.activity_problem_03);
        if (numProbLines == 4) setContentView(R.layout.activity_problem_04);
        if (numProbLines == 5) setContentView(R.layout.activity_problem_05);
        if (numProbLines == 6) setContentView(R.layout.activity_problem_06);
        if (numProbLines == 7) setContentView(R.layout.activity_problem_07);
        if (numProbLines == 8) setContentView(R.layout.activity_problem_08);
        if (numProbLines == 9) setContentView(R.layout.activity_problem_09);
        if (numProbLines == 10) setContentView(R.layout.activity_problem_10);
        if (numProbLines == 11) setContentView(R.layout.activity_problem_11);


        //eventually, you should use 'extras' to pass the topic and user skill from main
        // activity to problem activity

        displayProblem(pp);

        //Tag all lines as draggable and set OCL
        LinearLayout givLay = findViewById(R.id.given_layout);
        for(int i=0; i<numProbLines; i++){
            TextView l = (TextView) givLay.getChildAt(i);
            l.setTag("DRAGGABLE TEXTVIEW");
            l.setOnLongClickListener(this);
        }

        //Set drag event listeners for layouts
        findViewById(R.id.answer_layout).setOnDragListener(this);
        findViewById(R.id.given_layout).setOnDragListener(this);
    }


    //https://www.tutlane.com/tutorial/android/android-drag-and-drop-with-examples
    @Override
    public boolean onLongClick(View v){
        ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);
        View.DragShadowBuilder dragshadow = new View.DragShadowBuilder(v);
        v.startDrag(data, dragshadow, v, 0);

        return true;
    }

    //https://www.tutlane.com/tutorial/android/android-drag-and-drop-with-examples
    @Override
    public boolean onDrag(View v, DragEvent event){
        int action = event.getAction();

        switch (action) {

            case DragEvent.ACTION_DRAG_STARTED:
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    v.invalidate();
                    return true;
                }
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                v.getBackground().clearColorFilter();
                v.invalidate();
                return true;

            case DragEvent.ACTION_DROP:
                v.invalidate();

                TextView vw = (TextView) event.getLocalState();
                ViewGroup owner = (ViewGroup) vw.getParent();
                owner.removeView(vw);
                LinearLayout container = (LinearLayout) v;

                // the same approach:
                // if new container: 'container' is 'given_layout', just addView... order doesn't matter
                // if new container: 'container' is 'answer_layout' then
                // - get height of event
                // - loop through lines in container until




                container.addView(vw);







                // hooooooooooooooooooow?
                //Try to figure out how to allow for better sorting.
                //Current system only allows for dropping new line at end.

//                if (container.getChildCount() == 0){
//                    container.addView(vw);
//                } else {
//                    // int draggedY = (int) event.getY();
//
//                    for(int i=0;i<container.getChildCount();i++){
//
//                        int containerLinePos[] = new int[2];
//                        container.getChildAt(i).getLocationOnScreen(containerLinePos);
//
//                        vw.setText(containerLinePos.toString());
//
//                        container.addView(vw);
//
////                        if(draggedY > containerLinePos[1] ){
////                            System.out.println("Dragged line lower than i.");
////                        } else {
////                            container.addView(vw, i);
////                        }
//                    }
//                }

                vw.setVisibility(View.VISIBLE);

                //Change line colour based on parent layout
                LinearLayout ansL = findViewById(R.id.answer_layout);
                LinearLayout vwParent = (LinearLayout) vw.getParent();
                if (vwParent == ansL){
                    vw.setBackgroundColor(Color.WHITE);
                }
                else vw.setBackgroundColor(getResources().getColor(R.color.colorLine));

                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                v.getBackground().clearColorFilter();
                v.invalidate();
                return true;

            default:
                Log.e("Parson Problem Activity", "Something went wrong with the dragging and dropping");
                break;
        }
        return false;
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
            String newPrompt = promptS + "\n\nFEEDBACK: THE SOLUTION REQUIRES THE USE OF MORE LINES.";
            promptV.setText(newPrompt);
        } else if (ansLay.getChildCount() > instanceProblem.validLines.size()) {
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

            //Attributes message based on answer attempt
            if (correctCount == instanceProblem.validLines.size()){
                String newPrompt = promptS + "\n\nFEEDBACK: CONGRATULATIONS! ALL LINES ARE CORRECT.";
                promptV.setText(newPrompt);
            } else if (distractorCount > 0){
                String newPrompt = promptS + "\n\nFEEDBACK: A DISTRACTOR LINE HAS BEEN USED. TRY A DIFFERENT LINE.";
                promptV.setText(newPrompt);
            } else {
                String newPrompt = promptS + "\n\nFEEDBACK: CORRECT LINES USED IN WRONG ORDER.";
                promptV.setText(newPrompt);
            }
        }







    }

}
