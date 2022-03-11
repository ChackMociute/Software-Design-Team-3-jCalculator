package softwaredesign;

import softwaredesign.Equation.Equation;

import java.util.Scanner;

public class CalculatorInterface {
    final Scanner scanner = new Scanner(System.in);

    public void renderEquation(Equation equation){
        System.out.println(equation);
    }

    public String getInput(){
        return scanner.nextLine();
    }
}
