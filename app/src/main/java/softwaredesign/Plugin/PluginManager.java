package softwaredesign.Plugin;

import org.pf4j.DefaultPluginManager;
import api.softwaredesign.AST.ASTNode;
import api.softwaredesign.AST.Error;
import api.softwaredesign.AST.ErrNode;
import api.softwaredesign.AST.OpNode;
import api.softwaredesign.PluginBase;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class PluginManager {
    private Map<String, Integer> operatorPrecedences;
    private List<PluginBase> plugins;

    org.pf4j.DefaultPluginManager pluginManager;

    public PluginManager(){
        operatorPrecedences = new HashMap<>();
        pluginManager = new DefaultPluginManager();
    }

    public boolean reloadPlugins(){
        operatorPrecedences.clear();

        /*pluginManager.stopPlugins();
        pluginManager.unloadPlugins();*/

        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        plugins = pluginManager.getExtensions(PluginBase.class);

        for(PluginBase plugin : plugins){
            System.out.println("Loaded " + plugin.getName());
            operatorPrecedences.putAll(plugin.getOperatorPrecedences());
        }

        return true;
    }

    public ASTNode dispatchToPlugin(OpNode operation){

        // Can't be dispatched if it's an error, so the error consumes the operation
        if(operation.left instanceof ErrNode) return operation.left;
        if(operation.right instanceof ErrNode) return operation.right;

        for(PluginBase plugin : plugins){
            if(plugin.canProcess(operation)){
                return plugin.solveNode(operation);
            }
        }
        return new ErrNode(Error.UNKNOWN_OP);
    }

    public int getOperatorPrecedence(String operator){
        return operatorPrecedences.get(operator);
    }

    public boolean isOperator(String operator){
        return operatorPrecedences.containsKey(operator);
    }
}
