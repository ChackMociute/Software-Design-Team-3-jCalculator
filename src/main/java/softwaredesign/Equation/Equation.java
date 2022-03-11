package softwaredesign.Equation;

import softwaredesign.Equation.AST.ASTNode;
import softwaredesign.Equation.AST.ErrorNode;

public class Equation {
    private final String equationString;
    private String answer;
    
    public Equation(String equationString){
        this.equationString = equationString;

        answer = ShuntingYard.calculateEquation(equationString);
    }

    public String getEquation(){
        return equationString;
    }

    public String getAnswer(){
        return answer;
    }

    @Override
    public String toString(){
        return String.format("%s = %s", getEquation(), getAnswer());
    }
}
