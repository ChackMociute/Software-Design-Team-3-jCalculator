package softwaredesign.Equation;

import softwaredesign.Plugin.PluginManager;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

import api.softwaredesign.AST.ASTNode;
import api.softwaredesign.AST.ErrorNode;
import api.softwaredesign.AST.LiteralNode;
import api.softwaredesign.AST.OperatorNode;
import api.softwaredesign.AST.Error;

public final class CalculationDispatcher {
    private static PluginManager pluginManager;
    
    public static void setPluginManager(PluginManager newPluginManager){
        pluginManager = newPluginManager;
    }

    public static String calculateEquation(String equationString){

        ASTNode root = generateAST(equationString);
        if(root instanceof ErrorNode) return ((ErrorNode)root).error.toString();

        return solveAST(root);
    }

    private static String solveAST(ASTNode root){
        if(root instanceof OperatorNode) root = solveNode((OperatorNode)root);
        return root.toString();
    }

    // Recursive function to walk the tree and solve each node
    private static ASTNode solveNode(OperatorNode operator){
        if(operator.left instanceof OperatorNode)
            operator.left = solveNode((OperatorNode)operator.left);
        if(operator.right instanceof OperatorNode)
            operator.right = solveNode((OperatorNode)operator.right);

        return pluginManager.dispatchToPlugin(operator);
    }

    private static ASTNode generateAST(String equation){
        var errorNode = new ErrorNode();
        Queue<String> queue = generatePostFixQueue(equation, errorNode);
        ASTNode node = generateASTRoot(queue);

        if(errorNode.error != Error.NONE) return errorNode;
        return node;
    }

    private static ArrayDeque<String> generatePostFixQueue(String equation, ErrorNode outErrorNode){
        String[] tokens = equation.split(" ");

        ArrayDeque<String> outputQueue = new ArrayDeque<>();
        Stack<String> operatorStack = new Stack<>();

        for(String token : tokens){
            if(token.equals("(")) {
                operatorStack.push(token);
            }else if(token.equals(")")){
                while(!operatorStack.peek().equals("(")){
                    outputQueue.add(operatorStack.pop());

                    if(operatorStack.empty()){
                        outErrorNode.error = Error.PARENTHESES;
                        return new ArrayDeque<>();
                    }
                }
                operatorStack.pop();
                // Handle function before (
                if(!operatorStack.isEmpty() && isFunction(operatorStack.peek())){
                    outputQueue.add(operatorStack.pop());
                }
            }else if(pluginManager.isOperator(token)){

                if(isFunction(token)){
                    operatorStack.add(token);
                    continue;
                }

                while (!operatorStack.empty() && !operatorStack.peek().equals("(") &&
                        pluginManager.getOperatorPrecedence(operatorStack.peek()) <= pluginManager.getOperatorPrecedence(token)) {
                    outputQueue.add(operatorStack.pop());
                }

                operatorStack.push(token);
            }else{
                outputQueue.add(token);
            }
        }

        while(!operatorStack.empty()){
            if(operatorStack.peek().equals("(")){
                outErrorNode.error = Error.PARENTHESES;
                return new ArrayDeque<>();
            }
            outputQueue.add(operatorStack.pop());
        }

        return outputQueue;
    }

    private static ASTNode generateASTRoot(Queue<String> queue){
        Stack<ASTNode> nodeStack = new Stack<>();

        while(!queue.isEmpty()){
            String token = queue.remove();

            if(pluginManager.isOperator(token)){
                OperatorNode newNode = new OperatorNode(token);
                newNode.left = nodeStack.pop();

                if(!isFunction(token)) newNode.right = nodeStack.pop();

                nodeStack.push(newNode);
            }else{
                nodeStack.push(new LiteralNode(token));
            }
        }

        if(nodeStack.isEmpty()) return new ErrorNode(Error.POSTFIX);
        return nodeStack.peek();
    }

    private static boolean isFunction(String operator){
        return operator.length() > 1;
    }
}
