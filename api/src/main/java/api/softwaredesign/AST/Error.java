package api.softwaredesign.AST;

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
    },
    UNKNOWN_OP{
        @Override
        public String toString() { return "Unknown operator";}
    },
    PLUGIN_ERROR{
        @Override
        public String toString() { return "Plugin error";}
    }
}
