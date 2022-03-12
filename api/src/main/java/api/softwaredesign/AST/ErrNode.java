package api.softwaredesign.AST;

public class ErrNode extends ASTNode {
    public Error error;

    public ErrNode(Error error){
        this.error = error;
    }
    public ErrNode(){
        this.error = Error.NONE;
    }

    @Override
    public String toString(){
        return error.toString();
    }
}