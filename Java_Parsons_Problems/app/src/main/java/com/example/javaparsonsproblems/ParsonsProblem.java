package com.example.javaparsonsproblems;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class for parsing the marked up Parson's problem text string. Ultimately creating a
 * ParsonsProblem object.
 */
public class ParsonsProblem {

    String prompt;
    ArrayList<String> validLines;
    ArrayList<String> distractors;

    /**
     * Class constructor for the ParsonsProblem class.
     * @param in The string describing the Parson's problem after being read from file.
     */
    public ParsonsProblem(String in){
        try{
            Scanner input = new Scanner(in);

            while (input.hasNextLine()){
                String nxtLine = input.nextLine();
                if(nxtLine.equals("[prompt]")) {
                    String check = input.nextLine();
                    if(check.equals("[valid lines]")) {
                        prompt = "";
                        validLines = new ArrayList<>();
                        boolean disReached = false;
                        while(!disReached){
                            String currLine = input.nextLine();

                            if(currLine.equals("[distractors]")){
                                distractors = new ArrayList<>();
                                boolean endReached = false;
                                while(!endReached){
                                    String distractorLine = input.nextLine();
                                    if(distractorLine.equals("[end]")) endReached = true;
                                    else distractors.add(distractorLine);
                                }
                                disReached = true;
                            }
                            else validLines.add(currLine);
                        }
                    } else prompt = check;
                }

                /* Some messiness and repetition here to handle different cases of how a problem
                 may be incorrectly marked up. Simplification may be possible by looking into
                 different ways problems can be marked up (JSON, XML) but would require overhaul
                 of how problems are currently stored in file.*/

                if(nxtLine.equals("[valid lines]")) {
                    validLines = new ArrayList<>();
                    boolean disReached = false;
                    while(!disReached){
                        String currLine = input.nextLine();

                        if(currLine.equals("[distractors]")){
                            distractors = new ArrayList<>();
                            boolean endReached = false;
                            while(!endReached){
                                String distractorLine = input.nextLine();
                                if(distractorLine.equals("[end]")) endReached = true;
                                else distractors.add(distractorLine);
                            }
                            disReached = true;
                        }
                        else validLines.add(currLine);
                    }
                }
            }
            input.close();

        } catch (Exception e) {System.out.println(e);}
    }
}
