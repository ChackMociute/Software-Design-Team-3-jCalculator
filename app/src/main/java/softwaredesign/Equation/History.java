package softwaredesign.Equation;

import softwaredesign.Equation.Equation;

import java.util.ArrayList;

public class History {
    private ArrayList<Equation> equations;

    public void undo(){

    }

    public void addEquation(Equation equation){

    }

    public Equation getLastEquation(){
        return new Equation("");
    }

    public ArrayList<Equation> getEquations(){
        return equations;
    }
}
