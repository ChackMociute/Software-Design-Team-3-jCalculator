package softwaredesign;

import softwaredesign.Equation.Equation;

import java.util.Scanner;

public class CalculatorInterface {
    final Scanner scanner = new Scanner(System.in);

    public void renderEquation(Equation equation){
        System.out.print(equation.getEquation());

        if(!equation.isError()){
            displayError(equation);
        }else{
            displayAnswer(equation);
        }
    }

    private void displayAnswer(Equation equation){
        System.out.println(" : " + equation.getError());
    }

    private void displayError(Equation equation){
        System.out.println(" = " + equation.getAnswer());
    }

    public String getInput(){
        return scanner.nextLine();

    }
}
