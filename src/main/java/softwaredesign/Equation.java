package main.java.softwaredesign;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

public class Equation {
    private final String equationString;
    private String error;
    private ASTNode treeHead;

    public static PluginManager pluginManager;

    private boolean isFunction(String operator){
        return operator.length() > 1;
    }

    private void generateAST(){
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
                        return;
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
                return;
            }
            outputQueue.add(operatorStack.pop());
        }

        Stack<ASTNode> nodeStack = new Stack<>();

        // Convert to tree
        while(!outputQueue.isEmpty()){
            String token = outputQueue.remove();

            if(pluginManager.isOperator(token)){
                Operator newNode = new Operator(token);
                newNode.left = nodeStack.pop();

                if(!isFunction(token)) newNode.right = nodeStack.pop();

                nodeStack.push(newNode);
            }else{
                nodeStack.push(new Literal(token));
            }
        }

        treeHead = nodeStack.peek();
    }

    public Equation(String equationString){
        this.equationString = equationString;
        error = "";

        generateAST();
    }

    public String getEquation(){
        return equationString;
    }

    public void computeAnswer(){

    }

    public String getError(){
        return error;
    }
}
