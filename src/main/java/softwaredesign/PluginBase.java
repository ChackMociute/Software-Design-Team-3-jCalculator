package main.java.softwaredesign;

import java.util.Dictionary;

//TODO: make it extend pf4j
public interface PluginBase {
    public String getName();
    public Dictionary<String, Integer> getOperatorPrecedences();
    public Literal processNode(ASTNode operation);
    public boolean canProcess(ASTNode operation);
}