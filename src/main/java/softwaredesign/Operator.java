package main.java.softwaredesign;

import java.util.ArrayList;

public class Operator extends ASTNode {
    public ASTNode left;
    public ASTNode right;
    public String operator;

    public boolean isCalculatable(){
        // If any of the children are uncalculated operators, this node is not calculatable
        return left instanceof Literal && right instanceof Literal;
    }

    public Operator(String operator){
        this.operator = operator;
    }
}
