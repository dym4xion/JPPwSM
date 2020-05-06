package com.example.javaparsonsproblems;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class to handle the delivery of each Parson's problem.
 */
public class ProblemActivity extends AppCompatActivity implements View.OnDragListener, View.OnTouchListener {

    ParsonsProblem instanceProblem;
    int numProbLines;
    int dSkill = 0;
    int[] newScores;
    MediaPlayer upSound;
    MediaPlayer downSound;
    MediaPlayer incorrectSound;
    MediaPlayer clickSound;

    /**
     * Initiates each problem by fetching appropriate content view, tagging all lines as draggable,
     * displaying 'NEXT' button as invisible, initialising media players for sound, and setting the
     * text for each of the problem components.
     * @param savedInstanceState
     */
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

            //Inflate the line_layout xml for every line in the problem
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout giv = findViewById(R.id.given_layout);
            for(int i = 0; i < numProbLines; i++){
                TextView line = (TextView) inflater.inflate(R.layout.line_layout, giv, false);
                giv.addView(line);
            }

            // set barrier text
            TextView tlV = findViewById(R.id.topic_level_view);
            tlV.setText(topic + " Level: " + skill);

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

            //create MediaPlayers once, on create for better efficiency?
            // they are called with soundSound.start(); when needed
            upSound = MediaPlayer.create(ProblemActivity.this,R.raw.up);
            downSound = MediaPlayer.create(ProblemActivity.this,R.raw.down);
            incorrectSound = MediaPlayer.create(ProblemActivity.this,R.raw.incorrect);
            clickSound = MediaPlayer.create(ProblemActivity.this,R.raw.click);
        }
    }

    /**
     * Method to trigger the line dragging mechanism once a line has been touched. Adapted from:
     * https://13mcec21.wordpress.com/2013/10/23/android-drag-and-drop/
     * @param view Line being dragged.
     * @param motionEvent What is the motion event when line is touched. Starts drag when motion
     *                    event is ACTION_DOWN.
     * @return True if drag is initiated.
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to facilitate the dragging of one line from one layout container to another. Adapted
     * from: https://13mcec21.wordpress.com/2013/10/23/android-drag-and-drop/
     * @param layoutview Layout below which a line will be dropped.
     * @param dragevent Describes the drag event occurring as the line is being dragged.
     * @return Should always return true.
     */
    public boolean onDrag(View layoutview, DragEvent dragevent) {
        int action = dragevent.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                upSound.start();
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                downSound.start();
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

    /**
     * Displays the data comprising a Parson's problem onto GUI components.
     * @param pp The Parson's problem to be displayed.
     */
    public void displayProblem(ParsonsProblem pp){
        // set prompt text
        TextView promptV = (TextView) findViewById(R.id.prompt_view);
        promptV.setText(pp.prompt);

        // collate all lines and shuffle them
        ArrayList<String> allLines = new ArrayList<>();
        allLines.addAll(pp.validLines);
        allLines.addAll(pp.distractors);
        Collections.shuffle(allLines);

        // set the text for the lines in the given lines layout as each of the problem lines.
        LinearLayout givLay = findViewById(R.id.given_layout);
        for(int i=0; i<numProbLines; i++){
            TextView l = (TextView) givLay.getChildAt(i);
            l.setText(allLines.get(i));
        }
    }

    /**
     * Method to build the filename for a problem for a given set of problem parameters, then
     * to read that problem from file, from the assets folder in order to produce the Parson's
     * problem data.
     * @param topic The topic of the problem to be fetched.
     * @param skill The skill level of the problem to be fetched.
     * @param numOfProblems The number of variations of problems for that topic/skill combination
     * @return The ParsonProblem object describing a random problem of the given topic/skill
     * combination.
     */
    public ParsonsProblem generateParsonsProblem(String topic, int skill, int numOfProblems){
        String skillLvl;
        if(skill < 10) skillLvl = "0" + Integer.toString(skill);
        else skillLvl = Integer.toString(skill);

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

    /**
     * Method to check the correctness of the solution provided in the answer space.
     * @param v The 'SUBMIT' answer button.
     */
    public void checkAnswer(View v){

        LinearLayout ansLay = findViewById(R.id.answer_layout);
        TextView feedbackV = findViewById(R.id.feedback_view);
        ViewGroup.LayoutParams params = feedbackV.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        feedbackV.setLayoutParams(params);

        //Check correct number of lines are used
        if (ansLay.getChildCount() < instanceProblem.validLines.size()){
            incorrectSound.start();
            dSkill -= 1;
            feedbackV.setText("Feedback: The solution requires the use of more lines.");
        } else if (ansLay.getChildCount() > instanceProblem.validLines.size()) {
            incorrectSound.start();
            dSkill -= 1;
            feedbackV.setText("Feedback: The solution requires fewer lines.");
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
                MediaPlayer correct = MediaPlayer.create(ProblemActivity.this,R.raw.correct); correct.start();
                dSkill += 1;
                feedbackV.setText("Feedback: Congratulations! All lines are correct.");


                Intent thisProb = getIntent();
                Bundle topicExtras = thisProb.getExtras();
                int skill = topicExtras.getInt("TOPIC_LEVEL");
                if (skill + dSkill > 0 && skill + dSkill < 11) newScores = writeNewStudentLevels(skill + dSkill,this);
                else if (skill + dSkill < 1) newScores = writeNewStudentLevels(1,this);
                else newScores = writeNewStudentLevels(skill,this);



                //hide submit and reset and show next
                Button chk = findViewById(R.id.submit_button);
                chk.setVisibility(View.INVISIBLE);
                Button rst = findViewById(R.id.reset_button);
                rst.setVisibility(View.INVISIBLE);
                Button nxt = findViewById(R.id.next_button);
                nxt.setVisibility(View.VISIBLE);

                Button skp = findViewById(R.id.skip_button);
                skp.setVisibility(View.INVISIBLE);
                Button skpB = findViewById(R.id.skip_back_button);
                skpB.setVisibility(View.INVISIBLE);

            } else if (distractorCount > 0){
                incorrectSound.start();
                dSkill -= 1;
                feedbackV.setText("Feedback: A distractor line has been used. Try a different line");
            } else {
                incorrectSound.start();
                dSkill -= 1;
                feedbackV.setText("Feedback: Correct lines used in wrong order.");
            }
        }
    }

    /**
     * Method to send all lines in answer space to given lines space.
     * @param v The 'RESET' button.
     */
    public void resetLines(View v){
        clickSound.start();
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

        TextView fbV = findViewById(R.id.feedback_view);
        fbV.setText("Feedback:");
    }

    /**
     * Method to trigger starting the next problem after successful completion of a problem.
     * @param v The 'NEXT' button.
     */
    public void nextProblem(View v){
        clickSound.start();

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

        if (skill + dSkill > 0 && skill + dSkill < 11) newScores = writeNewStudentLevels(skill + dSkill,this);
        else if (skill + dSkill < 1) newScores = writeNewStudentLevels(1,this);
        else newScores = writeNewStudentLevels(skill,this);

        newProb.putIntArray("ALL_LEVELS", newScores);

        newProblem.putExtras(newProb);
        startActivity(newProblem);
        finish();
    }

    /**
     * Method used to skip through problems by increasing or decreasing topic difficulty. For
     * debugging purposes.
     * @param v Skip forward and skip back buttons.
     */
    public void skipProblem(View v){
        Button b = (Button) v;
        if(b.getText().equals("SKIP Back (debug)")) dSkill = -1;
        else dSkill = 1;

        nextProblem(v);
    }

    /**
     * Method to write to the student levels file the new skill levels. Also attributes bonus
     * skill points to related other topics (as indicated in the problem prompt) if problem is
     * solved on the first attempt.
     * @param newLevel The new skill level for the current problem topic.
     * @param context The context to which the file is written.
     * @return The new student levels array.
     */
    public int[] writeNewStudentLevels(int newLevel, Context context){

        Intent thisProb = getIntent();
        Bundle topicExtras = thisProb.getExtras();
        int[] lvls = topicExtras.getIntArray("ALL_LEVELS");
        String topic = topicExtras.getString("PROB_TOPIC");
        switch (topic) {
            case "IO":
                lvls[0] = newLevel;
                break;
            case "VAR":
                lvls[1] = newLevel;
                break;
            case "CON":
                lvls[2] = newLevel;
                break;
            case "DS":
                lvls[3] = newLevel;
                break;
            case "FUN":
                lvls[4] = newLevel;
                break;
            case "OOP":
                lvls[5] = newLevel;
                break;
        }

        // this procedure adds bonus skill points if problem is solved on first attempt
        if(dSkill == 1){
            if(instanceProblem.prompt.contains("#IO")) lvls[0] += 1;
            if(instanceProblem.prompt.contains("#VAR")) lvls[1] += 1;
            if(instanceProblem.prompt.contains("#CON")) lvls[2] += 1;
            if(instanceProblem.prompt.contains("#DS")) lvls[3] += 1;
            if(instanceProblem.prompt.contains("#FUN")) lvls[4] += 1;
            if(instanceProblem.prompt.contains("#OOP")) lvls[5] += 1;
        }


        String outString = Integer.toString(lvls[0])+
                            ","+Integer.toString(lvls[1])+
                            ","+Integer.toString(lvls[2])+
                            ","+Integer.toString(lvls[3])+
                            ","+Integer.toString(lvls[4])+
                            ","+Integer.toString(lvls[5]);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("studentLVLs.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(outString);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        return lvls;
    }

    @Override
    public void onBackPressed() {
        clickSound.start();
        super.onBackPressed();
    }
}