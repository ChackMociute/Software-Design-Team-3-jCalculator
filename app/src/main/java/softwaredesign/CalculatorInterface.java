package softwaredesign;

import softwaredesign.Equation.Equation;
import softwaredesign.Plugin.PluginStore.PluginData;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class CalculatorInterface {
    Scanner scanner = new Scanner(System.in);

    public void renderEquation(Equation equation){
        //System.out.print("\u001B[34m");
        System.out.println(equation);
    }

    public void printIntroduction(){
        System.out.println("Please type expressions. E.g. 2sin(5 + 1) * [1, 2, 3]");
        System.out.println("Use ans in your expressions to substitute for the result of the last equation");
        System.out.println("Use undo to revert the last equation");
        System.out.println("Please type 'quit' to exit\n");
    }

    public void printPluginData(List<PluginData> pluginData){
        System.out.println("Type quit to go back");
        for(int i = 0; i < pluginData.size(); i++){
            PluginData data = pluginData.get(i);
            System.out.printf("%d. %s: %s%n", i, data.getName(), data.getDesc());
        }
    }

    public void printMenu(){
        System.out.println("1. Calculator");
        System.out.println("2. Plugin Store");
    }

    public void printCalculatorPrompt(){
        System.out.print("Enter expression : ");
    }

    public String getInput(){
        //System.out.print("\u001B[36m");
        return scanner.nextLine();
    }
}
