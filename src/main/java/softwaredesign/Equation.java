package softwaredesign;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

import softwaredesign.ASTNode;

public class Equation {
    private final String equationString;
    private String answer;
    private String error;
    private ASTNode treeHead;

    public static PluginManager pluginManager;
    
    public Equation(String equationString){
        this.equationString = equationString;
        error = "";

        generateAST();
    }

    public String getEquation(){
        return equationString;
    }

    public void computeAnswer(){
        answer = "5";
    }

    public String getError(){
        return error;
    }

    public String getAnswer(){
        return answer;
    }

    private boolean isFunction(String operator){
        return operator.length() > 1;
    }

    private void generateAST(){
        Queue<String> queue = generateArrayDeque();
        Stack<ASTNode> nodeStack = generateNodeStack(queue);

        if(!nodeStack.empty()) treeHead = nodeStack.peek();
    }

    private ArrayDeque<String> generateArrayDeque(){
        String[] tokens = equationString.split(" ");

        Queue<String> outputQueue = new ArrayDeque<>();
        Stack<String> operatorStack = new Stack<>();

        for(String token : tokens){
            if(token.equals("(")) {
                operatorStack.push(token);
            }else if(token.equals(")")){
                while(!operatorStack.peek().equals("(")){
                    outputQueue.add(operatorStack.pop());

                    if(operatorStack.empty()){
                        error = "Mismatched parentheses";
                        return new ArrayDeque<>();
                    }
                }
                operatorStack.pop();
                // Handle function before (
                if(!operatorStack.isEmpty() && isFunction(operatorStack.peek())){
                    outputQueue.add(operatorStack.pop());
                }
            }else if(pluginManager.isOperator(token)){

                // Handle functions
                if(isFunction(token)){
                    operatorStack.add(token);
                    continue;
                }

                while (!operatorStack.empty() && !operatorStack.peek().equals("(") &&
                        pluginManager.getOperatorPrecedence(operatorStack.peek()) >= pluginManager.getOperatorPrecedence(token)) {
                    outputQueue.add(operatorStack.pop());
                }

                operatorStack.push(token);
            }else{
                outputQueue.add(token);
            }
        }

        while(!operatorStack.empty()){
            if(operatorStack.peek().equals("(")){
                error = "Mismatched parentheses";
                return new ArrayDeque<>();
            }
            outputQueue.add(operatorStack.pop());
        }

        return (ArrayDeque)outputQueue;
    }

    private Stack<ASTNode> generateNodeStack(Queue<String> queue){
        Stack<ASTNode> nodeStack = new Stack<>();

        while(!queue.isEmpty()){
            String token = queue.remove();

            if(pluginManager.isOperator(token)){
                Operator newNode = new Operator(token);
                newNode.left = nodeStack.pop();

                if(!isFunction(token)) newNode.right = nodeStack.pop();

                nodeStack.push(newNode);
            }else{
                nodeStack.push(new Literal(token));
            }
        }

        return nodeStack;
    }
}
