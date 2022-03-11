package softwaredesign.Equation;

import softwaredesign.Equation.AST.ASTNode;
import softwaredesign.Equation.AST.ErrorNode;

public class Equation {
    private final String equationString;
    private String answer;
    private ASTNode treeHead;
    
    public Equation(String equationString){
        this.equationString = equationString;

        treeHead = ShuntingYard.generateAST(equationString);
        computeAnswer();
    }

    public String getEquation(){
        return equationString;
    }

    public void computeAnswer(){
        if(!isError()){

        }
    }

    public String getError(){
        if(treeHead instanceof ErrorNode) return ((ErrorNode)treeHead).error.toString();
        return "";
    }

    public boolean isError(){
        return !getError().equals("");
    }

    public String getAnswer(){
        return answer;
    }
}
