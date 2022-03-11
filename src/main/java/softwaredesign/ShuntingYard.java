package softwaredesign;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

public final class ShuntingYard {
    private static PluginManager pluginManager;
    
    public static void setPluginManager(PluginManager NewPluginManager){
        pluginManager = NewPluginManager;
    }

    public static ASTNode generateAST(String equation){
        var errorNode = new ErrorNode();
        Queue<String> queue = generatePostFixQueue(equation, errorNode);
        ASTNode node = generateNodeStack(queue);

        if(errorNode.error != Error.NONE) return errorNode;
        return node;
    }

    private static ArrayDeque<String> generatePostFixQueue(String equation, ErrorNode outErrorNode){
        String[] tokens = equation.split(" ");

        Queue<String> outputQueue = new ArrayDeque<>();
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
                outErrorNode.error = Error.PARENTHESES;
                return new ArrayDeque<>();
            }
            outputQueue.add(operatorStack.pop());
        }

        return (ArrayDeque)outputQueue;
    }

    private static ASTNode generateNodeStack(Queue<String> queue){
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

        if(nodeStack.isEmpty()) return new ErrorNode(Error.POSTFIX);
        return nodeStack.peek();
    }

    private static boolean isFunction(String operator){
        return operator.length() > 1;
    }
}
