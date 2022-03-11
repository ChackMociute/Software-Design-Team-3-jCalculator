package main.java.softwaredesign;

public class Controller {
    private static Controller instance;

    public static Controller getInstance(){
        return instance;
    }

    CalculatorInterface calcInterface;
    History history;
    PluginManager pluginManager;
    PluginStoreManager pluginStoreManager;

    public Controller(){
        instance = this;

        calcInterface = new CalculatorInterface();
        history = new History();

        pluginManager = new PluginManager();
        pluginStoreManager = new PluginStoreManager(pluginManager);
    }

    public PluginManager getPluginManager(){
        return pluginManager;
    }

    public void start(){

    }
}
