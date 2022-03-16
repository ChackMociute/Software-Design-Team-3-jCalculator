package VectorPlugin;

import java.util.ArrayList;

class Vector {
    ArrayList<Double> components;

    private Vector(ArrayList<Double> components){
        this.components = components;
    }

    public int getDim(){
        return components.size();
    }

    @Override
    public String toString(){
        String returnString = "[";
        for(Double component : components){
            returnString += Double.toString(component) + ",";
        }

        // Replace trailing comma with close bracket
        return returnString.substring(0, returnString.length()-1) + "]";
    }

    public static Vector parseString(String str){
        // Remove brackets
        str = str.substring(1, str.length() - 1);

        String[] tokens = str.split(",");

        var components = new ArrayList<Double>();

        for(String token : tokens){
            components.add(Double.parseDouble(token));
        }

        return new Vector(components);
    }

    public static Vector add(Vector a, Vector b){
        if(a.getDim() != b.getDim()) return null;

        var components = new ArrayList<Double>();

        for(int i = 0; i < a.getDim(); i++){
            components.add(a.components.get(i) + b.components.get(i));
        }

        return new Vector(components);
    }

    public static Vector sub(Vector a, Vector b){
        if(a.getDim() != b.getDim()) return null;

        var components = new ArrayList<Double>();

        for(int i = 0; i < a.getDim(); i++){
            components.add(a.components.get(i) - b.components.get(i));
        }

        return new Vector(components);
    }

    public static Vector scale(Vector v, Double s){
        var components = new ArrayList<Double>();

        for(Double component : v.components){
            components.add(component * s);
        }

        return new Vector(components);
    }
}
