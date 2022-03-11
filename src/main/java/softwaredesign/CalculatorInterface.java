package softwaredesign;

import softwaredesign.Equation.Equation;

import java.io.Console;

public class CalculatorInterface {
    Console cnsl = System.console();
    public void renderEquation(Equation equation){
        System.out.println(equation);
    }

    public String getInput(){
        return "2 + 2";//cnsl.readLine("");
    }
}
