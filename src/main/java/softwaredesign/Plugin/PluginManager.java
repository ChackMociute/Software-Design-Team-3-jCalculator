package softwaredesign.Plugin;

import org.pf4j.JarPluginManager;
import softwaredesign.Equation.AST.ASTNode;
import softwaredesign.Equation.AST.Error;
import softwaredesign.Equation.AST.ErrorNode;
import softwaredesign.Equation.AST.LiteralNode;
import softwaredesign.Equation.AST.OperatorNode;
import softwaredesign.Plugin.PluginBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class PluginManager {
    private Map<String, Integer> operatorPrecedences;
    private List<PluginBase> plugins;

    org.pf4j.PluginManager pluginManager;

    public PluginManager(){
        operatorPrecedences = new HashMap<>();
        pluginManager = new JarPluginManager();
    }

    public boolean reloadPlugins(){
        operatorPrecedences.clear();

        pluginManager.stopPlugins();
        pluginManager.unloadPlugins();

        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        plugins = pluginManager.getExtensions(PluginBase.class);

        // Load precedences
        // TODO: Handle duplicate operators
        for(PluginBase plugin : plugins){
            operatorPrecedences.putAll(plugin.getOperatorPrecedences());
        }

        return true;
    }

    public List<String> getAvaliablePlugins(){
        return new ArrayList<>();
    }

    public ASTNode dispatchToPlugin(OperatorNode operation){

        // Can't be dispatched if it's an error, so the error consumes the operation
        if(operation.left instanceof ErrorNode) return operation.left;
        if(operation.right instanceof ErrorNode) return operation.right;

        for(PluginBase plugin : plugins){
            if(plugin.canProcess(operation)){
                return plugin.solveNode(operation);
            }
        }
        return new ErrorNode(Error.UNKNOWN_OP);
    }

    public int getOperatorPrecedence(String operator){
        return operatorPrecedences.get(operator);
    }

    public boolean isOperator(String operator){
        return operatorPrecedences.containsKey(operator);
    }
}
