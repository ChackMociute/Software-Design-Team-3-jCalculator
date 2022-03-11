package softwaredesign.Equation.AST;

public enum Error {
    NONE{
        @Override
        public String toString(){
            return "";
        }
    },
    POSTFIX{
        @Override
        public String toString(){
            return "Error when converting from postfix";
        }
    },
    PARENTHESES{
        @Override
        public String toString(){
            return "Mismatched parentheses error";
        }
    }
}
