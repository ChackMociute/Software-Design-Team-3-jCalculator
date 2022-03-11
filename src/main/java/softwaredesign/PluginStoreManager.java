package main.java.softwaredesign;

public class PluginStoreManager {
    private PluginData[] avaliablePlugins;

    private PluginManager pluginManager;

    public PluginStoreManager(PluginManager pluginManager){
        this.pluginManager = pluginManager;
        avaliablePlugins = new PluginData[0];
    }

    public boolean loadAvaliablePlugins(){
        return true;
    }

    public PluginData[] getAvaliablePlugins(){
        return avaliablePlugins;
    }

    public boolean downloadPlugin(String url){
        return true;
    }
}
