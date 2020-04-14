package com.example.javaparsonsproblems;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class for parsing the marked up Parson's problem text sting. Ultimately creating a
 * ParsonsProblem object.
 */
public class ParsonsProblem {

    String prompt;
    ArrayList<String> validLines = new ArrayList<>();
    ArrayList<String> distractors = new ArrayList<>();

    /**
     * Class constructor for the ParsonsProblem class.
     * @param in The string describing the Parson's problem after being read from file.
     */
    public ParsonsProblem(String in){
        ParseParsonTXT(in);
    }

    /**
     * Method to parse the text describing Parson's problems which also sets instance variables.
     * @param in
     */
    public void ParseParsonTXT(String in){

        try{
            Scanner input = new Scanner(in);

            while (input.hasNextLine()){
                String nxtLine = input.nextLine();
                if(nxtLine.equals("[prompt]")) prompt = input.nextLine();

                if(nxtLine.equals("[valid lines]")) {
                    boolean disReached = false;
                    while(!disReached){
                        String currLine = input.nextLine();

                        if(currLine.equals("[distractors]")){

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
