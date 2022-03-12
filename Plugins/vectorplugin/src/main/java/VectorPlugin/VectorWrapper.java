package VectorPlugin;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;


import api.softwaredesign.PluginBase;
import api.softwaredesign.AST.LiteralNode;
import api.softwaredesign.AST.OperatorNode;
import api.softwaredesign.AST.ErrorNode;
import api.softwaredesign.AST.Error;

import java.util.HashMap;
import java.util.Map;



public class VectorWrapper extends Plugin{

    public VectorWrapper(PluginWrapper wrapper){
        super(wrapper);
    }

    @Extension
    public static class VectorPlugin implements PluginBase {

        @Override
        public String getName(){
            return "Vector Plugin";
        }

        @Override
        public Map<String, Integer> getOperatorPrecedences(){
            var map = new HashMap<String, Integer>();

            map.put("+", 4);
            map.put("-", 4);
            map.put("*", 3);

            return map;
        }

        @Override
        public LiteralNode solveNode(OperatorNode operation){
            LiteralNode leftNode = (LiteralNode)operation.left;
            LiteralNode rightNode = (LiteralNode)operation.right;


            Vector result = Vector.parseString("[0]");
            switch (operation.operator){
                case "+":
                    result = Vector.add(Vector.parseString(leftNode.value), Vector.parseString(rightNode.value));
                    break;
                case "-":
                    result = Vector.sub(Vector.parseString(leftNode.value), Vector.parseString(rightNode.value));
                    break;
                case "*":
                    LiteralNode vectorNode = isVector(leftNode) ? leftNode : rightNode;
                    LiteralNode scalarNode = isDouble(leftNode) ? leftNode : rightNode;

                    Vector v = Vector.parseString(vectorNode.value);
                    Double s = Double.parseDouble(scalarNode.value);

                    result = Vector.scale(v, s);
                    break;
            }

            return new LiteralNode(result.toString());
        }

        @Override
        public boolean canProcess(OperatorNode operation){
            LiteralNode leftNode = (LiteralNode)operation.left;
            LiteralNode rightNode = (LiteralNode)operation.right;

            switch (operation.operator){
                case "*":
                    // One scalar, one vector
                    return (isDouble(leftNode) && isVector(rightNode)) ||
                            (isVector(leftNode) && isDouble(rightNode));

                case "+": // Both have to be vectors
                case "-":
                    return isVector(leftNode) && isVector(rightNode);
            }

            return false;
        }

        private boolean isDouble(LiteralNode node){
            try{
                Double.parseDouble(node.value);
            }catch(Exception e){
                return false;
            }

            return true;
        }

        private boolean isVector(LiteralNode node){
            try{
                Vector.parseString(node.value);
            }catch(Exception e){
                return false;
            }

            return true;
        }

    }
}