package softwaredesign.Equation;

public class Equation {
    private final String equationString;
    private final String answer;
    
    public Equation(String equationString){
        this.equationString = equationString;

        answer = CalculationDispatcher.calculateEquation(equationString);
    }

    public String getEquation(){
        return equationString;
    }

    public String getAnswer(){
        return answer;
    }

    @Override
    public String toString(){
        return String.format("%s = %s", getEquation(), getAnswer());
    }
}
