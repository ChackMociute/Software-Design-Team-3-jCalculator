package TrigPlugin;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;


import api.softwaredesign.PluginBase;
import api.softwaredesign.AST.LitNode;
import api.softwaredesign.AST.OpNode;

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
        public LitNode solveNode(OpNode operation){
            LitNode leftNode = (LitNode)operation.left;

            double left = Double.parseDouble(leftNode.value);


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

            return new LitNode(Double.toString(result));
        }

        @Override
        public boolean canProcess(OpNode operation){
            LitNode leftNode = (LitNode)operation.left;
            LitNode rightNode = (LitNode)operation.right;
            try{
                Double.parseDouble(leftNode.value);
            }catch(NumberFormatException e){
                return false;
            }

            return "sincostan".contains(operation.operator);
        }

    }
}
