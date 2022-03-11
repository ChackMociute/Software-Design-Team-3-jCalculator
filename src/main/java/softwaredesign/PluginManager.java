package main.java.softwaredesign;
import java.util.Dictionary;
import java.util.Map;

public class PluginManager {
    private Map<String, Integer> operatorPrecedences;
    private PluginBase[] plugins;

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
        return 0;
    }

    public boolean isOperator(String operator){
        return operatorPrecedences.containsKey(operator);
    }
}
