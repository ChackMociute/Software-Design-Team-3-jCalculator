package softwaredesign.Plugin;

import softwaredesign.Equation.AST.ASTNode;
import softwaredesign.Equation.AST.LiteralNode;

import java.util.Dictionary;

//TODO: make it extend pf4j
public interface PluginBase {
    public String getName();
    public Dictionary<String, Integer> getOperatorPrecedences();
    public LiteralNode processNode(ASTNode operation);
    public boolean canProcess(ASTNode operation);
}
