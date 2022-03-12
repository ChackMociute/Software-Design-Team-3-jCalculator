package ArithmeticPlugin;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;


import api.softwaredesign.PluginBase;
import api.softwaredesign.AST.LiteralNode;
import api.softwaredesign.AST.OperatorNode;

import java.util.HashMap;
import java.util.Map;

public class ArithmeticWrapper extends Plugin{

    public ArithmeticWrapper(PluginWrapper wrapper){
        super(wrapper);
    }

    @Extension
    public static class ArithmeticPlugin implements PluginBase {
        @Override
        public String getName(){
            return "Athrithmetic Plugin";
        }

        @Override
        public Map<String, Integer> getOperatorPrecedences(){
            var map = new HashMap<String, Integer>();

            map.put("+", 4);
            map.put("-", 4);
            map.put("*", 3);
            map.put("/", 3);

            return map;
        }

        @Override
        public LiteralNode solveNode(OperatorNode operation){

            LiteralNode leftNode = (LiteralNode)operation.left;
            LiteralNode rightNode = (LiteralNode)operation.right;

            double left = Double.parseDouble(leftNode.value);
            double right = Double.parseDouble(rightNode.value);


            double result = 0;
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

            return new LiteralNode(Double.toString(result));
        }

        @Override
        public boolean canProcess(OperatorNode operation){
            LiteralNode leftNode = (LiteralNode)operation.left;
            LiteralNode rightNode = (LiteralNode)operation.right;
            try{
                Double.parseDouble(leftNode.value);
                Double.parseDouble(rightNode.value);
            }catch(Exception e) {
                return false;
            }

            boolean operatorKnown = "+-*/".contains(operation.operator);

            return operatorKnown;
        }
    }
}
