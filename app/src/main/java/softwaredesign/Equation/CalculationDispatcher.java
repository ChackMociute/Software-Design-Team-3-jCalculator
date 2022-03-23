package softwaredesign.Equation;

import softwaredesign.Plugin.PluginManager;

import java.util.*;
import java.util.regex.Pattern;

import api.softwaredesign.AST.ASTNode;
import api.softwaredesign.AST.ErrNode;
import api.softwaredesign.AST.LitNode;
import api.softwaredesign.AST.OpNode;
import api.softwaredesign.AST.Error;

public final class CalculationDispatcher {
    private static PluginManager pluginManager;
    
    public static void setPluginManager(PluginManager newPluginManager){
        pluginManager = newPluginManager;
    }

    public static String calculateEquation(String equationString){

        ASTNode root = generateAST(equationString);
        if(root instanceof ErrNode) return ((ErrNode)root).error.toString();

        return solveAST(root);
    }

    private static String solveAST(ASTNode root){
        if(root instanceof OpNode) root = solveNode((OpNode)root);
        return root.toString();
    }

    // Recursive function to walk the tree and solve each node
    private static ASTNode solveNode(OpNode operator){
        if(operator.left instanceof OpNode)
            operator.left = solveNode((OpNode)operator.left);
        if(operator.right instanceof OpNode)
            operator.right = solveNode((OpNode)operator.right);

        return pluginManager.dispatchToPlugin(operator);
    }

    private static ASTNode generateAST(String equation){
        var errorNode = new ErrNode();
        Queue<String> queue = generatePostFixQueue(equation, errorNode);
        ASTNode node = generateASTRoot(queue);

        if(errorNode.error != Error.NONE) return errorNode;
        return node;
    }

    private static ArrayDeque<String> generatePostFixQueue(String equation, ErrNode outErrorNode){
        String[] tokens = tokenize(equation);

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
                OpNode newNode = new OpNode(token);
                newNode.right = nodeStack.pop();

                if(!isFunction(token)) newNode.left = nodeStack.pop();

                nodeStack.push(newNode);
            }else{
                nodeStack.push(new LitNode(token));
            }
        }

        if(nodeStack.isEmpty()) return new ErrNode(Error.POSTFIX);
        return nodeStack.peek();
    }

    private static String[] tokenize(String equation){
        var tokens = new ArrayList<String>();

        for(int i = 0; i < equation.length(); i++){
            String currentCharacter = equation.substring(i, i+1);
            if(isToken(currentCharacter)){
                tokens.add(currentCharacter);
                if(")]".contains(currentCharacter) & nextTokenImplicitMultiplication(equation.substring(i+1)))
                    tokens.add("*");
            }else if(new ofNumericalType().isOfType(currentCharacter)){
                int j = getSliceSize(equation.substring(i+1), new ofNumericalType());
                tokens.add(equation.substring(i, i+j));
                if(nextTokenImplicitMultiplication(equation.substring(i+j))) tokens.add("*");
                i = i+j-1;
            }else if(new ofAlphabeticalType().isOfType(currentCharacter)){
                int j = getSliceSize(equation.substring(i+1), new ofAlphabeticalType());
                if(pluginManager.isOperator(equation.substring(i, i+j))){
                    tokens.add(equation.substring(i, i+j));
                    i = i+j-1;
                }else{
                    tokens.add(currentCharacter);
                    if(nextTokenImplicitMultiplication(equation.substring(i+1))) tokens.add("*");
                }
            }else if(currentCharacter.equals("[")){
                int j = getSliceSize(equation.substring(i+1), new ofVectorType());
                tokens.add(equation.substring(i, i+j+1));
                i = i+j;
            }
        }

        return tokens.toArray(new String[tokens.size()]);
    }

    private static boolean isToken(String character){
        return "()".contains(character) |
                pluginManager.isOperator(character);
    }

    private static boolean nextTokenImplicitMultiplication(String equation){
        int i = 0;
        while(i < equation.length()){
            String currentCharacter = equation.substring(i, i+1);
            if(!currentCharacter.equals(" ")){
                return "([".contains(currentCharacter) |
                        new ofAlphabeticalType().isOfType(currentCharacter);
            }
            i++;
        }
        return false;
    }

    private static int getSliceSize(String equation, ofType type){
        int i = 0;
        while(i < equation.length()){
            if(!type.isOfType(equation.substring(i, i+1))){
                break;
            }
            i++;
        }
        return i+1;
    }

    private interface ofType{
        boolean isOfType(String character);
    }

    private static class ofNumericalType implements ofType{
        public boolean isOfType(String character) {
            return "0123456789".contains(character);
        }
    }

    private static class ofAlphabeticalType implements ofType{
        public boolean isOfType(String character) {
            return Pattern.compile("[a-zA-Z]").matcher(character).find();
        }
    }

    private static class ofVectorType implements ofType{
        public boolean isOfType(String character) {
            return !character.equals("]");
        }
    }

    private static boolean isFunction(String operator){
        return operator.length() > 1;
    }
}
