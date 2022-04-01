package ArithmeticPlugin;

import api.softwaredesign.AST.ASTNode;
import api.softwaredesign.AST.ErrNode;
import api.softwaredesign.AST.Error;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;


import api.softwaredesign.PluginBase;
import api.softwaredesign.AST.LitNode;
import api.softwaredesign.AST.OpNode;

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
        public ASTNode solveNode(OpNode operation){

            LitNode leftNode = (LitNode)operation.left;
            LitNode rightNode = (LitNode)operation.right;

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
                    if(right == 0) return new ErrNode(Error.PLUGIN_ERROR, "Divide by 0 error");
                    result = left / right;
                    break;
            }

            return new LitNode(Double.toString(result));
        }

        @Override
        public boolean canProcess(OpNode operation){
            LitNode leftNode = (LitNode)operation.left;
            LitNode rightNode = (LitNode)operation.right;
            try{
                Double.parseDouble(leftNode.value);
                Double.parseDouble(rightNode.value);
            }catch(Exception e) {
                return false;
            }

            return "+-*/".contains(operation.operator);
        }
    }
}
