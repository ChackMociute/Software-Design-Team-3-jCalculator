package api.softwaredesign.AST;

public class LiteralNode extends ASTNode{
    public String value;

    public LiteralNode(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
