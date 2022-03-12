package api.softwaredesign.AST;

public class OpNode extends ASTNode {
    public ASTNode left;
    public ASTNode right;
    public String operator;

    public boolean isCalculatable(){
        // If any of the children are uncalculated operators, this node is not calculatable
        return left instanceof LitNode && right instanceof LitNode;
    }

    public OpNode(String operator){
        this.operator = operator;
    }
}
