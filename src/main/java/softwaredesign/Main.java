package softwaredesign;

import java.util.Scanner;

public class Main {
    public static void main (String[] args){
        Controller controller = Controller.getInstance();
        var scanner = new Scanner(System.in);

        while(true){
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("stop")){
                scanner.close();
                break;
            }

            controller.start(input);
        }
    }
}
