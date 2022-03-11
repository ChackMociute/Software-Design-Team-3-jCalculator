package softwaredesign.Equation.AST;

public class LiteralNode extends ASTNode{
    public String value;

    public LiteralNode(String value){
        this.value = value;
    }
}
