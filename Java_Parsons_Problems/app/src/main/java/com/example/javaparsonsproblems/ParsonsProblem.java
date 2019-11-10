package com.example.javaparsonsproblems;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ParsonsProblem {

    String prompt;
    ArrayList<String> validLines = new ArrayList<>();
    ArrayList<String> distractors = new ArrayList<>();

    public ParsonsProblem(String filename){
        File file = new File("problems/" + filename);

        try{
            Scanner input = new Scanner(file);

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

        } catch (Exception e){
            System.out.println(e);
        }

    }
}