package api.softwaredesign.AST;

public class LitNode extends ASTNode{
    public String value;

    public LitNode(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
