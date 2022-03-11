package main.java.softwaredesign;

public class Operator extends ASTNode {
    public ASTNode[] children;
    public String operator;

    public boolean isCalculatable(){
        // If any of the children are uncalculated operators, this node is not calculatable
        for(ASTNode child : children) if(child instanceof Operator) return false;

        return true;
    }

    public Operator(String operator){
        this.operator = operator;
    }
}
