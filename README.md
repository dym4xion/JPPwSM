# JPPwSM   
## Java Parsons Problems with Student Modelling   

Repository for Final Year Project that uses Parsons Problems in the context of the Java programming language as a learning aid in developing a knowledge of how to effectively solve programming problems in Java. An attempt to model the student's undertanding is made in order to present the user pedagogically appropriate problems.

## Conventions   

### Defining Problems   

Problems should be marked up in a file using the filename format XX\_YY\_ZZ.txt where XX is the topic abbriviation, YY is the problem level (between 1 and 10 inclusive) and ZZ is the number of that variation of problem for the topic/level combination. See the assets folder for examples.

In the file marking up the problem, the problem prompt should follow the [prompt] tag on a singular line, on the immediate next line after the tag. Following the [valid lines] tag the lines that form the solution to the problem should be written each on a new line as they wish to be displayed, in the correct order. Any distractor lines should be marked up similarly after the [distractors] tag but the order is not important here. IT IS IMPORTANT THAT THERE ARE NO CHARACTERS THAT FOLLOW THE LAST CHARACTER OF THE [end] TAG, INCLUDING ANY EMPTY LINES OR SPACES.

When a new problem is added to the assets folder the getVariantsMatrix method should be modified such that the new problem is accounted for. If unchanged the system will still function correctly but the new problem will never be presented to the user.
