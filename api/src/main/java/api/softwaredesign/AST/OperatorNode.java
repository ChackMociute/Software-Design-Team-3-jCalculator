package api.softwaredesign.AST;

public class OperatorNode extends ASTNode {
    public ASTNode left;
    public ASTNode right;
    public String operator;

    public boolean isCalculatable(){
        // If any of the children are uncalculated operators, this node is not calculatable
        return left instanceof LiteralNode && right instanceof LiteralNode;
    }

    public OperatorNode(String operator){
        this.operator = operator;
    }
}
