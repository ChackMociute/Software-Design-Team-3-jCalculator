package AthrithmeticPlugin;

import org.pf4j.Extension;
import softwaredesign.Equation.AST.OperatorNode;
import softwaredesign.Plugin.PluginBase;
import softwaredesign.Equation.AST.LiteralNode;

import java.util.HashMap;
import java.util.Map;

@Extension
public class AthrithmeticPlugin implements PluginBase {
    public String getName(){
        return "Athrithmetic Plugin";
    }
    public Map<String, Integer> getOperatorPrecedences(){
        var map = new HashMap<String, Integer>();

        map.put("+", 4);
        map.put("-", 4);
        map.put("*", 3);
        map.put("/", 3);

        return map;
    }

    public LiteralNode solveNode(OperatorNode operation){
        LiteralNode leftNode = (LiteralNode)operation.left;
        LiteralNode rightNode = (LiteralNode)operation.right;

        Integer left = Integer.parseInt(leftNode.value);
        Integer right = Integer.parseInt(rightNode.value);


        int result = 0;
        switch (operation.operator){
            case "+":
                result = left + right;
                break;
            case "-":
                result = left - right;
                break;
            case "*":
                result = left * right;
                break;
            case "/":
                result = left / right;
                break;
        }

        return new LiteralNode(Integer.toString(result));
    }

    public boolean canProcess(OperatorNode operation){
        LiteralNode leftNode = (LiteralNode)operation.left;
        LiteralNode rightNode = (LiteralNode)operation.right;
        try{
            Integer.parseInt(leftNode.value);
            Integer.parseInt(rightNode.value);
        }catch(NumberFormatException e){
            return false;
        }

        boolean operatorKnown = "+-*/".contains(operation.operator);

        return operatorKnown;
    }
}
