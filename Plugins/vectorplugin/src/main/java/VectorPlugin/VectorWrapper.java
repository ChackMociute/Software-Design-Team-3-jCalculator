package VectorPlugin;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;


import api.softwaredesign.PluginBase;
import api.softwaredesign.AST.LitNode;
import api.softwaredesign.AST.OpNode;

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
        public LitNode solveNode(OpNode operation){
            LitNode leftNode = (LitNode)operation.left;
            LitNode rightNode = (LitNode)operation.right;


            Vector result = Vector.parseString("[0]");
            switch (operation.operator){
                case "+":
                    result = Vector.add(Vector.parseString(leftNode.value), Vector.parseString(rightNode.value));
                    break;
                case "-":
                    result = Vector.sub(Vector.parseString(leftNode.value), Vector.parseString(rightNode.value));
                    break;
                case "*":
                    LitNode vectorNode = isVector(leftNode) ? leftNode : rightNode;
                    LitNode scalarNode = isDouble(leftNode) ? leftNode : rightNode;

                    Vector v = Vector.parseString(vectorNode.value);
                    Double s = Double.parseDouble(scalarNode.value);

                    result = Vector.scale(v, s);
                    break;
            }


            return new LitNode(result.toString());
        }

        @Override
        public boolean canProcess(OpNode operation){
            LitNode leftNode = (LitNode)operation.left;
            LitNode rightNode = (LitNode)operation.right;

            switch (operation.operator){
                case "*":
                    // One scalar, one vector
                    return (isDouble(leftNode) && isVector(rightNode)) ||
                            (isVector(leftNode) && isDouble(rightNode));

                case "+": // Both have to be vectors
                case "-":
                    boolean areVectors = isVector(leftNode) && isVector(rightNode);

                    if(areVectors){
                        Vector leftVec = Vector.parseString(leftNode.value);
                        Vector rightVec = Vector.parseString(rightNode.value);

                        return leftVec.getDim() == rightVec.getDim();
                    }

                    return areVectors;
            }

            return false;
        }

        private boolean isDouble(LitNode node){
            try{
                Double.parseDouble(node.value);
            }catch(Exception e){
                return false;
            }

            return true;
        }

        private boolean isVector(LitNode node){
            try{
                Vector.parseString(node.value);
            }catch(Exception e){
                return false;
            }

            return true;
        }

    }
}