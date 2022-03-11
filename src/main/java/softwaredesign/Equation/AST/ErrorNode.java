package softwaredesign.Equation.AST;

public class ErrorNode extends ASTNode {
    public Error error;

    public ErrorNode(Error error){
        this.error = error;
    }
    public ErrorNode(){
        this.error = Error.NONE;
    }

    @Override
    public String toString(){
        return error.toString();
    }
}