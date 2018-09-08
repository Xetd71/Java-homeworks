/*
* Student: Chuev Ivan
* Group: CE163(2)
* Program: Reactive calculator
* Data: 15.10.2017
*/

package edu.hse.chuev;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.hse.chuev.reactiveCalculator.*;

/**
 * The console reactive calculator program
 */
public class Program {
    /**
     * regular expression for setting variable command
     */
    private static final Pattern setVariable = Pattern.compile("^([ab]:\\s?)(-?\\d+(\\.\\d*)?)$");
    /**
     * regular expression for setting operation command
     */
    private static final Pattern runOperation = Pattern.compile("^[+\\-*:]$");

    /**
     * entry point
     * @param args command line arguments (not supported)
     */
    public static void main(String[] args) {
        ReactiveCalculator rc = new ReactiveCalculator();
        Scanner input = new Scanner(System.in);

        // no needles here try catch, because all wrong commands will be caught by regular expressions
        while(true){
            String inputLine = input.nextLine();
            // if in console printed 'exit' the program will be finished
            if(inputLine.equals("exit"))
                break;

            Matcher m = setVariable.matcher(inputLine);
            // set value of variable
            if(m.find()) {
                rc.setVariable(m.group(1).charAt(0), Double.parseDouble(m.group(2)));
                continue;
            }

            m = runOperation.matcher(inputLine);
            // set operation
            if(m.find()) {
                rc.registerOperation(m.group().charAt(0));
            }
        }
    }
}
