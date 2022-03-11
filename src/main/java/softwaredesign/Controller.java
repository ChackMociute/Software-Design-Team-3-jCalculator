package softwaredesign;

import softwaredesign.Equation.Equation;
import softwaredesign.Equation.History;
import softwaredesign.Equation.ShuntingYard;
import softwaredesign.Plugin.PluginManager;
import softwaredesign.Plugin.PluginStore.PluginStoreManager;

public class Controller {
    private static Controller instance;

    public static Controller getInstance(){
        if(instance == null){
            instance = new Controller();
        }

        return instance;
    }

    CalculatorInterface calcInterface;
    History history;
    PluginManager pluginManager;
    PluginStoreManager pluginStoreManager;

    private Controller(){
        calcInterface = new CalculatorInterface();
        history = new History();

        pluginManager = new PluginManager();
        pluginStoreManager = new PluginStoreManager(pluginManager);

        ShuntingYard.setPluginManager(pluginManager);
    }

    public PluginManager getPluginManager(){
        return pluginManager;
    }

    public void start(){
        while(true){
            String input = calcInterface.getInput();

            if(input.equals("quit")) {
                break;
            }

            var fullEquation = new Equation(input);
            history.addEquation(fullEquation);

            calcInterface.renderEquation(fullEquation);
        }
    }
}
