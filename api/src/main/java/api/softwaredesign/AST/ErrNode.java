package api.softwaredesign.AST;

public class ErrNode extends ASTNode {
    public Error error;
    public String message;

    public ErrNode(Error error){
        this.error = error;
        message = "";
    }

    public ErrNode(Error error, String msg){
        this.error = error;
        message = msg;
    }
    public ErrNode(){
        this.error = Error.NONE;
    }

    @Override
    public String toString(){
        return error.toString() + ": " + message;
    }
}