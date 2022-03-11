package softwaredesign.Plugin;

import org.pf4j.ExtensionPoint;
import softwaredesign.Equation.AST.ASTNode;
import softwaredesign.Equation.AST.LiteralNode;

import java.util.Map;

import org.pf4j.Plugin;
import softwaredesign.Equation.AST.OperatorNode;

public interface PluginBase extends ExtensionPoint {
    public String getName();
    public Map<String, Integer> getOperatorPrecedences();
    public LiteralNode solveNode(OperatorNode operation);
    public boolean canProcess(OperatorNode operation);
}
