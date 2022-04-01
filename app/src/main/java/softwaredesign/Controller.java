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

        // Should be enum
        // 0 = main menu
        // 1 = calculator
        // 2 = plugin store
        int menuState = 0;

        while(true){
            if(menuState == 0) calcInterface.printMenu();
            else if(menuState == 1) calcInterface.printCalculatorPrompt();
            else if(menuState == 2) calcInterface.printPluginData(pluginStoreManager.getAvaliablePlugins());

            String input = calcInterface.getInput();
            if(input.equals("quit")) {
                if(menuState == 0) break;
                else menuState = 0;
            }else if (input.equals("undo")){
                history.undo();
                calcInterface.renderEquation(history.getLastEquation());
                continue;
            }

            if(menuState == 0){
                try{
                    int intInput = Integer.parseInt(input);
                    if(0 < intInput && intInput < 3){
                        menuState = intInput;
                    }
                }catch(NumberFormatException e){

                }
            }
            else if(menuState == 1) {
                var fullEquation = new Equation(input);
                history.addEquation(fullEquation);

                calcInterface.renderEquation(fullEquation);
            }else if(menuState == 2){
                boolean success = true;
                try{
                    int intInput = Integer.parseInt(input);
                    success = pluginStoreManager.downloadPlugin(intInput);
                }catch(NumberFormatException e){
                    success = false;
                }

                if(success) System.out.println("Download successful");
                else System.out.println("Download failed");
            }
        }
    }
}
