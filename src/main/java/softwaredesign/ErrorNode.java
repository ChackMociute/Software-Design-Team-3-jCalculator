package softwaredesign;

public class ErrorNode extends ASTNode {
    public Error error;

    public ErrorNode(Error error){
        this.error = error;
    }

    public ErrorNode(){
        this.error = Error.NONE;
    }
}