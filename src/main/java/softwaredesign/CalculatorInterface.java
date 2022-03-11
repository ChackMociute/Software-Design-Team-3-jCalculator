package softwaredesign;

import java.util.Scanner;

public class CalculatorInterface {
    final Scanner scanner = new Scanner(System.in);

    public void renderEquation(Equation equation){
        
    }

    public void displayAnswer(String answer){
        System.out.println(answer + "\n");
    }

    public void displayError(String error){
        System.out.println(error);
    }

    public String getInput(){
        return scanner.nextLine();

    }
}
