package softwaredesign;

import softwaredesign.Equation.Equation;
import softwaredesign.Equation.History;
import softwaredesign.Equation.CalculationDispatcher;
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

        CalculationDispatcher.setPluginManager(pluginManager);
        CalculationDispatcher.setHistory(history);
    }

    public PluginManager getPluginManager(){
        return pluginManager;
    }

    public void start(){
        calcInterface.printIntroduction();

        pluginManager.reloadPlugins();

        pluginStoreManager.loadAvaliablePlugins();

        MenuState menuState = MenuState.MAIN;

        while(true){
            if(menuState == menuState.MAIN)
                calcInterface.printMenu();
            else if(menuState == menuState.CALCULATOR)
                calcInterface.printCalculatorPrompt();
            else if(menuState == menuState.STORE)
                calcInterface.printPluginData(pluginStoreManager.getAvaliablePlugins());

            String input = calcInterface.getInput();
            if(input.equals("quit")) {
                if(menuState == menuState.MAIN) break;
                else menuState = menuState.MAIN;
            }else if (input.equals("undo")){
                history.undo();
                calcInterface.renderEquation(history.getLastEquation());
                continue;
            }

            if(menuState == menuState.MAIN){
                try{
                    int intInput = Integer.parseInt(input);
                    if(0 < intInput && intInput < 3){
                        menuState = MenuState.values()[intInput];
                    }
                }catch(NumberFormatException ignored){

                }
            }
            else if(menuState == menuState.CALCULATOR) {
                var fullEquation = new Equation(input);
                history.addEquation(fullEquation);

                calcInterface.renderEquation(fullEquation);
            }else if(menuState == menuState.STORE){
                boolean success;
                try{
                    int intInput = Integer.parseInt(input);
                    success = pluginStoreManager.downloadPlugin(intInput);
                }catch(NumberFormatException ignored){
                    success = false;
                }

                if(success) System.out.println("Download successful");
                else System.out.println("Download failed");
            }
        }
    }
}
