package softwaredesign;

import softwaredesign.Equation.Equation;

import java.io.Console;
import java.util.Scanner;

public class CalculatorInterface {
    Scanner scanner = new Scanner(System.in);

    public void renderEquation(Equation equation){
        //System.out.print("\u001B[34m");
        System.out.println(equation);
    }

    public void printIntroduction(){
        System.out.println("Please type expressions. E.g. 2sin(5 + 1) * [1, 2, 3]");
        System.out.println("Please type 'quit' to exit\n");
    }

    public String getInput(){
        //System.out.print("\u001B[36m");
        System.out.print("Enter expression : ");
        return scanner.nextLine();
    }
}
