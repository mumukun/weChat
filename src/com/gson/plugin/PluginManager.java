/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2014 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson.plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang.StringUtils;

import com.gson.util.ConfKit;

/**
 * 插件管理器
 * @author ____′↘夏悸
 *
 */
public class PluginManager {
	private static PluginManager pm;
	private PluginContainer pluginContainer = new PluginContainer();
	private Properties pluginProperties;
	private String pluginDir = ConfKit.get("pluginDir");
	private final String PROPERTIES_FILE_NAME = "plugin.properties";
	
	private PluginManager(){}
	/**
	 * 获取Manager对象
	 * @return
	 */
	public static synchronized PluginManager getInstance(){
		if(pm == null){
			pm = new PluginManager();
		}
		return pm;
	}
    
	/**
	 * 运行插件
	 * @param pluginName
	 * @throws Exception
	 */
    public void run(String pluginName,Object... obj) throws Exception{
    	PluginInstance instance = getLoader(pluginName);
        Class<?> forName = Class.forName(instance.getEntryClass(), true, instance.getLoader());
        Plugin ins = (Plugin)forName.newInstance();
        ins.run(obj);
    }
    
    /**
     * 加载插件
     * @param pluginName
     * @throws Exception
     */
    public void loadPlugin(String pluginName) throws Exception{
        PluginClassLoader loader = new PluginClassLoader();
        PluginInstance instance = new PluginInstance();
        String pluginurl = "jar:file:/" + pluginDir + "/" + pluginName + ".jar!/";
        loader.addURLFile(new URL(pluginurl));
        
        InputStream is = loader.getResourceAsStream(PROPERTIES_FILE_NAME);
        if(is == null){
        	extracted(pluginName,"未找到plugin.properties文件！");
        }
        
        pluginProperties = new Properties();
        pluginProperties.load(is);
        
        String entryClass = pluginProperties.getProperty("entryClass");
        if(StringUtils.isEmpty(entryClass)){
        	extracted(pluginName,"未找到entryClass类入口配置");
        }
        
        instance.setEntryClass(entryClass);
        instance.setLoader(loader);
        instance.setAuthor(pluginProperties.getProperty("author"));
        instance.setDescription(pluginProperties.getProperty("description"));
        instance.setName(pluginProperties.getProperty("name"));
        instance.setVersion(pluginProperties.getProperty("version"));
        
        addLoader(pluginName, instance);
    }
    
    /**
     * 插件是否已经加载
     * @param pluginName
     * @return
     */
    public Boolean isLoaded(String pluginName){
    	return this.pluginContainer.containsKey(pluginName);
    }
    
    /**
     * 获取所有插件信息
     * @return
     * @throws IOException
     */
    public List<PluginInstance> fetchAllPlugins() throws IOException{
    	File dir = new File(pluginDir);
    	if(!dir.exists()){
    		throw new RuntimeException("插件目录不存在!");
    	}
    	
    	List<PluginInstance> list = new ArrayList<PluginInstance>();
    	
    	File[] jars = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
    	
    	for (File file : jars) {
    		 JarFile jar = new JarFile(file);
    		 Enumeration<JarEntry> enumer = jar.entries();
    		 while (enumer.hasMoreElements()) {
				JarEntry jarEntry = (JarEntry) enumer.nextElement();
				if(jarEntry.getName().equals(PROPERTIES_FILE_NAME)){
					PluginInstance instance = new PluginInstance();
					pluginProperties = new Properties();
			        pluginProperties.load(jar.getInputStream(jarEntry));
			        instance.setEntryClass(pluginProperties.getProperty("entryClass"));
			        instance.setAuthor(pluginProperties.getProperty("author"));
			        instance.setDescription(pluginProperties.getProperty("description"));
			        instance.setName(pluginProperties.getProperty("name"));
			        instance.setVersion(pluginProperties.getProperty("version"));
			        instance.setFileName(file.getName());
			        list.add(instance);
			        jar.close();
					break;
				}
			}
		}
    	return list;
    }
    
    /**
     * 获得插件运行容器
     * @return
     */
    public PluginContainer getPluginContainer() {
		return pluginContainer;
	}

	private void extracted(String pluginName,String message) {
		throw new RuntimeException("插件【"+pluginName+ "】：" + message);
	}
    
    public void unloadPlugin(String pluginName) throws IOException{
        this.pluginContainer.get(pluginName).getLoader().unloadJarFiles();
        this.pluginContainer.remove(pluginName);
    }
    
    private void addLoader(String pluginName,PluginInstance instance){
        this.pluginContainer.put(pluginName, instance);
    }
    
    private PluginInstance getLoader(String pluginName){
        return this.pluginContainer.get(pluginName);
    }
}
