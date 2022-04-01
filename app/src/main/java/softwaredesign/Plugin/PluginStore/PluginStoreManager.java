package softwaredesign.Plugin.PluginStore;

import org.json.JSONArray;
import org.json.JSONObject;
import softwaredesign.Plugin.PluginManager;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class PluginStoreManager {
    private static final String manifestURL = "https://raw.githubusercontent.com/ils510/jcalculator-plugins/main/manifest.json";
    private static final String downloadFolder = "plugins" + File.separator;

    private List<PluginData> avaliablePlugins;

    private PluginManager pluginManager;

    public PluginStoreManager(PluginManager pluginManager){
        this.pluginManager = pluginManager;
        avaliablePlugins = new ArrayList<>();
    }

    public boolean loadAvaliablePlugins(){
        avaliablePlugins.clear();

        InputStream in;
        StringBuilder contents = new StringBuilder();

        // Read the file at the URL
        try{
            URL url = new URL(manifestURL);
            URLConnection con = url.openConnection();
            con.setUseCaches(false);
            in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = reader.readLine()) != null){
                contents.append(line);
            }

            in.close();
        }catch(Exception e){
            return false;
        }

        // Load the read file as json and load into PluginData objects
        JSONArray arr = new JSONArray(contents.toString());

        for(Object obj : arr){
            if(!(obj instanceof JSONObject)) return false;
            JSONObject jsonObj = (JSONObject) obj;
            avaliablePlugins.add(new PluginData(
                    jsonObj.getString("name"),
                    jsonObj.getString("desc"),
                    jsonObj.getString("url")
            ));
        }

        return true;
    }

    public List<PluginData> getAvaliablePlugins(){
        return avaliablePlugins;
    }

    public boolean downloadPlugin(int index){
        if(index >= avaliablePlugins.size()) return false;

        final String downloadURL = avaliablePlugins.get(index).url;
        // Messy, but takes the last part of the URL path as the filename
        final String downloadName = downloadFolder + downloadURL.split("/")[downloadURL.split("/").length-1];
        System.out.printf("Downloading %s%n", downloadURL);

        File outputFile = new File(downloadName);

        if(outputFile.exists()){
            pluginManager.unloadPlugins();
            outputFile.delete();
        }

        try {
            outputFile.getParentFile().mkdirs();
            outputFile.createNewFile();

            BufferedInputStream in = new BufferedInputStream(new URL(downloadURL).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (Exception e) {
           return false;
        }

        pluginManager.reloadPlugins();
        return true;
    }

}
