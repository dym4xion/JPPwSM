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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
              this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}

        setContentView(R.layout.activity_problem);

        //eventually, you should use extras to pass the topic and user skill from main
        // activity to problem activity
        ParsonsProblem pp = generateProblem("IO", 1, 1);
        instanceProblem = pp;
        displayProblem(pp);

        //Tag all lines as draggable
        TextView dragLine1 = (TextView) findViewById(R.id.line_view);
        dragLine1.setTag("DRAGGABLE TEXTVIEW");
        dragLine1.setOnLongClickListener(this);
        TextView dragLine2 = (TextView) findViewById(R.id.line_view2);
        dragLine2.setTag("DRAGGABLE TEXTVIEW");
        dragLine2.setOnLongClickListener(this);
        TextView dragLine3 = (TextView) findViewById(R.id.line_view3);
        dragLine3.setTag("DRAGGABLE TEXTVIEW");
        dragLine3.setOnLongClickListener(this);
        TextView dragLine4 = (TextView) findViewById(R.id.line_view4);
        dragLine4.setTag("DRAGGABLE TEXTVIEW");
        dragLine4.setOnLongClickListener(this);
        TextView dragLine5 = (TextView) findViewById(R.id.line_view5);
        dragLine5.setTag("DRAGGABLE TEXTVIEW");
        dragLine5.setOnLongClickListener(this);
        TextView dragLine6 = (TextView) findViewById(R.id.line_view6);
        dragLine6.setTag("DRAGGABLE TEXTVIEW");
        dragLine6.setOnLongClickListener(this);

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

                container.addView(vw);

                //Try to figure out how to allow for better sorting.
                //Current system only allows for dropping new line at end.

//                if (container.getChildCount() == 0){
//                    container.addView(vw);
//                } else {
//                    int draggedX = (int) event.getX();
//
//                    for(int i=0;i<container.getChildCount();i++){
//                        int containerLinePos[] = new int[2];
//                        container.getChildAt(i).getLocationOnScreen(containerLinePos);
//                        if(draggedX > containerLinePos[1] ){
//                            System.out.println("Dragged line lower than i.");
//                        } else {
//                            container.addView(vw, i);
//                        }
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
        TextView promptV = (TextView) findViewById(R.id.prompt_view);
        promptV.setText(pp.prompt);



        ArrayList<String> allLines = new ArrayList<>();
        allLines.addAll(pp.validLines);
        allLines.addAll(pp.distractors);
        Collections.shuffle(allLines);

        //This should be refactored, this is bad...., or at least not very good.
        TextView line1 = findViewById(R.id.line_view);
        line1.setText(allLines.get(0));
        TextView line2 = findViewById(R.id.line_view2);
        line2.setText(allLines.get(1));
        TextView line3 = findViewById(R.id.line_view3);
        line3.setText(allLines.get(2));
        TextView line4 = findViewById(R.id.line_view4);
        line4.setText(allLines.get(3));
        TextView line5 = findViewById(R.id.line_view5);
        line5.setText(allLines.get(4));
        TextView line6 = findViewById(R.id.line_view6);
        line6.setText(allLines.get(5));

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
    public ParsonsProblem generateProblem(String topic, int skill, int numOfProblems){
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
        // if no. lines in answer > no. lines in pp.validLines then too many lines used
        // if no. lines in answer < no. lines in pp.validLines then not enough lines used
        // for every line in answer layout, if answer[i] == pp.validLines.get(i)
        // then line i is correct, else line i is wrong

        // if num lines ==, and each line correct, then answer is correct

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
