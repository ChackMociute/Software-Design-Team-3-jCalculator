package TrigPlugin;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;


import api.softwaredesign.PluginBase;
import api.softwaredesign.AST.LiteralNode;
import api.softwaredesign.AST.OperatorNode;

import java.util.HashMap;
import java.util.Map;

public class TrigWrapper extends Plugin{

    public TrigWrapper(PluginWrapper wrapper){
        super(wrapper);
    }

    @Extension
    public static class TrigPlugin implements PluginBase {

        @Override
        public String getName(){
            return "Trig Plugin";
        }

        @Override
        public Map<String, Integer> getOperatorPrecedences(){
            var map = new HashMap<String, Integer>();

            map.put("sin", 10);
            map.put("cos", 10);
            map.put("tan", 10);

            return map;
        }

        @Override
        public LiteralNode solveNode(OperatorNode operation){
            LiteralNode leftNode = (LiteralNode)operation.left;

            Double left = Double.parseDouble(leftNode.value);


            double result = 0;
            switch (operation.operator){
                case "cos":
                    result = Math.cos(left);
                    break;
                case "sin":
                    result = Math.sin(left);
                    break;
                case "tan":
                    result = Math.tan(left);
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
            }catch(NumberFormatException e){
                return false;
            }

            boolean operatorKnown = "sincostan".contains(operation.operator);

            return operatorKnown;
        }

    }
}
