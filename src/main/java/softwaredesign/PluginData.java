package main.java.softwaredesign;

public class PluginData {
    String name;
    String desc;
    String url;

    public PluginData(String name, String desc, String url){
        this.name = name;
        this.desc = desc;
        this.url = url;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public String getDownloadURL(){
        return url;
    }
}
