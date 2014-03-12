package com.gson.plugin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

public class PluginManager {
	private Map<String ,PluginClassLoader> pluginMap = new HashMap<String,PluginClassLoader>();
    public PluginManager(){

    }
    
    public void doSome(String pluginName){
        try{
        	PluginClassLoader loader = getLoader(pluginName);
            Class<?> forName = Class.forName(loader.getEntry(), true, loader);//this.pluginMap.get(pluginName).loadClass(packagename);
            Plugin ins = (Plugin)forName.newInstance();
            ins.doSome();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void addLoader(String pluginName,PluginClassLoader loader){
        this.pluginMap.put(pluginName, loader);
    }
    private PluginClassLoader getLoader(String pluginName){
        return this.pluginMap.get(pluginName);
    }
    public void loadPlugin(String pluginName) throws IOException{
        this.pluginMap.remove(pluginName);
        PluginClassLoader loader = new PluginClassLoader();
        String pluginpath = "E:/plugin/"+pluginName+".jar";
        String pluginurl = "jar:file:/E:/plugin/"+pluginName+".jar!/";
        URL url = null;
        try {
            url = new URL(pluginurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        loader.addURLFile(url);
        JarFile jar = new JarFile(new File(pluginpath));
        String entryClass = jar.getManifest().getMainAttributes().getValue("entry-class");
        jar.close();
        loader.setEntry(entryClass);
        addLoader(pluginName, loader);
    }
    
    public void unloadPlugin(String pluginName){
        this.pluginMap.get(pluginName).unloadJarFiles();
        this.pluginMap.remove(pluginName);
    }
}
