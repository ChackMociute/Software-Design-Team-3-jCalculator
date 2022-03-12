package softwaredesign;

import softwaredesign.Equation.Equation;

import java.io.Console;
import java.util.Scanner;

public class CalculatorInterface {
    Scanner scanner = new Scanner(System.in);

    public void renderEquation(Equation equation){
        System.out.print("\u001B[34m");
        System.out.println(equation);
    }

    public String getInput(){
        System.out.print("\u001B[36m");
        System.out.println("Enter equation : ");
        return scanner.nextLine();
    }
}
