package softwaredesign.Equation;

import java.util.Stack;

public class History {
    private final Stack<Equation> history = new Stack<Equation>();

    public void undo(){
        if(!history.empty()) history.pop();
    }

    public void addEquation(Equation equation){
        history.add(equation);
    }

    public Equation getLastEquation(){
        if(history.empty()) return new Equation("");
        return history.peek();
    }

    public String getANS(){
        if(history.empty()) return "";
        return history.peek().getAnswer();
    }

    public Stack<Equation> getHistory(){
        return history;
    }
}
