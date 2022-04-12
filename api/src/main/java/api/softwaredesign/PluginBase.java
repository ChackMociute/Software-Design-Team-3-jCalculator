package api.softwaredesign;

import api.softwaredesign.AST.ASTNode;
import api.softwaredesign.AST.OpNode;
import java.util.Map;

import org.pf4j.ExtensionPoint;

public interface PluginBase extends ExtensionPoint {
    String getName();
    Map<String, Integer> getOperatorPrecedences();
    ASTNode solveNode(OpNode operation);

    boolean canProcess(OpNode operation);
}
