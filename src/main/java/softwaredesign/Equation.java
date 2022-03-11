package softwaredesign;

import softwaredesign.ASTNode;

public class Equation {
    private final String equationString;
    private String answer;
    private ASTNode treeHead;
    
    public Equation(String equationString){
        this.equationString = equationString;

        treeHead = ShuntingYard.generateAST(equationString);
    }

    public String getEquation(){
        return equationString;
    }

    public void computeAnswer(){
        answer = "5";
    }

    public String getError(){
        if(treeHead instanceof ErrorNode) return ((ErrorNode)treeHead).error.toString();
        return "";
    }

    public String getAnswer(){
        return answer;
    }
}
