package yt.bam.library.modules;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * TranslationModule
 * @author FR34KYN01535
 * @version 1.1
 */

public class TranslationModule {
    private static final Logger logger = Bukkit.getLogger();
    private Plugin Plugin;
    private String Language;
    private Map<String,String> defaultTranslation; // NEEDS TO BE SET
    private Map<String,String> translation;
    private FileConfiguration loadedlanguage;
    
    public TranslationModule(Plugin plugin,String language,Map<String,String> defaultTranslation){
        Plugin = plugin;
        this.defaultTranslation = defaultTranslation;
        Language = language;
    }
    
    public void reloadTranslation(){
        if(Language.equals("en")){
            translation = defaultTranslation;
        }else{
            File f = new File(Plugin.getDataFolder()+File.separator+ Language+".yml");
            if(f.exists()) {
                File languageFile = new File(Plugin.getDataFolder()+File.separator+Language+".yml");
                loadedlanguage = YamlConfiguration.loadConfiguration(languageFile);
                loadTranslation();
            }else{
                logger.warning("Languagefile "+Language+".yml not found, falling back to english language!");
            }
        }
    }
    
    public String getTranslation(String key){
        if(translation!=null){
            String value = translation.get("translation."+key);
            if(value==null || value.isEmpty()){
                return "Translation missing: "+key;
            }else{
                return value;
            }
        }
        else{
            return defaultTranslation.get(key);
        }
    }
    
    private void loadTranslation(){
        translation = new HashMap<String, String>();
        for(Map.Entry<String,Object> entry : loadedlanguage.getValues(true).entrySet()){
            translation.put(entry.getKey(),entry.getValue().toString());
        }
    }
    

    public void onEnable() {
        reloadTranslation();
    }

    public void onDisable() {
    }
    
}