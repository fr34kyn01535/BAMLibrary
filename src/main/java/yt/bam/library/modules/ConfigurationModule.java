package yt.bam.library.modules;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * ConfigurationModule
 * @author FR34KYN01535
 * @version 1.1
 */

public class ConfigurationModule{
    public Plugin Plugin;
    
    public ConfigurationModule(Plugin plugin){
        Plugin=plugin;
    }
    
    public boolean getBoolean(String key,boolean def){
        if (Plugin.getConfig().get(key) == null){
            return def;
        }else{
            return Plugin.getConfig().getBoolean(key);
        }
    }
    
    public String getString(String key,String def){
        if (Plugin.getConfig().get(key) == null){
            return def;
        }else{
            return Plugin.getConfig().getString(key);
        }
    }
    
    public int getInt(String key,int def){
        if (Plugin.getConfig().get(key) == null){
            return def;
        }else{
            return Plugin.getConfig().getInt(key);
        }
    }
    
    public void onEnable() {
        Plugin.saveDefaultConfig();
        Plugin.reloadConfig();
    }

    public void onDisable() {
    }
}
