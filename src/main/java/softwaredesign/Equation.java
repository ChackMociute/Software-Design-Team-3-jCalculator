package main.java.softwaredesign;

public class Equation {
    private String equationString;
    private String error;
    private ASTNode treeHead;

    public static PluginManager pluginManager;

    private void generateAST(){

    }

    public Equation(String equationString){
        this.equationString = equationString;
        error = "";

        generateAST();
    }

    public String getEquation(){
        return equationString;
    }

    public void computeAnswer(){

    }

    public String getError(){
        return error;
    }
}
