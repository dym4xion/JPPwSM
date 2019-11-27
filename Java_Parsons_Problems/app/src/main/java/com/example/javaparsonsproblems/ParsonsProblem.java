package com.example.javaparsonsproblems;



import java.util.ArrayList;
import java.util.Scanner;

public class ParsonsProblem {

    String prompt;
    ArrayList<String> validLines = new ArrayList<>();
    ArrayList<String> distractors = new ArrayList<>();


    public ParsonsProblem(String in){
        ParseParsonTXT(in);
        if (distractors.size() > 0) distractors.remove(distractors.size()-1);
    }


    public void ParseParsonTXT(String in){

        //File file = new File("src/main/assets/"+filename);

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
