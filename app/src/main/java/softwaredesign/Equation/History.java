package softwaredesign.Equation;

import java.util.Stack;

public class History {
    private final Stack<Equation> history = new Stack<Equation>();

    public void undo(){
        history.pop();
    }

    public void addEquation(Equation equation){
        history.add(equation);
    }

    public Equation getLastEquation(){
        return history.peek();
    }

    public String getANS(){
        return history.peek().getAnswer();
    }

    public Stack<Equation> getHistory(){
        return history;
    }
}
