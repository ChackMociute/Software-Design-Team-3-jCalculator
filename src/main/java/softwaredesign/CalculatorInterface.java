package softwaredesign;

public class CalculatorInterface {
    public void renderEquation(Equation equation){
        
    }

    public void displayAnswer(String answer){
        System.out.println(answer + "\n");
    }

    public void displayError(String error){
        System.out.println(error);
    }

    public String getInput(){
        return "";
    }
}
