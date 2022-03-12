package api.softwaredesign;

import api.softwaredesign.AST.LiteralNode;
import api.softwaredesign.AST.OperatorNode;
import java.util.Map;

import org.pf4j.ExtensionPoint;

public interface PluginBase extends ExtensionPoint {
    String getName();
    Map<String, Integer> getOperatorPrecedences();
    LiteralNode solveNode(OperatorNode operation);

    boolean canProcess(OperatorNode operation);
}
