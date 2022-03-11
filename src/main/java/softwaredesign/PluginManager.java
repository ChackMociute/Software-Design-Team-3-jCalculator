package softwaredesign;

import java.util.HashMap;
import java.util.Map;

public class PluginManager {
    private Map<String, Integer> operatorPrecedences;
    private PluginBase[] plugins;

    public PluginManager(){
        operatorPrecedences = new HashMap<>();
    }

    public boolean reloadPlugins(){
        return true;
    }

    public String[] getAvaliablePlugins(){
        return new String[0];
    }

    public Literal dispatchToplugin(ASTNode operation){
        return new Literal("NULL");
    }

    public int getOperatorPrecedence(String operator){
        return operatorPrecedences.get(operator);
    }

    public boolean isOperator(String operator){
        return operatorPrecedences.containsKey(operator);
    }
}
