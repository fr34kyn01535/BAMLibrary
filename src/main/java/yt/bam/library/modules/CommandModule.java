package yt.bam.library.modules;

import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import yt.bam.library.*;
import yt.bam.library.commands.*;

/**
 * CommandModule
 * @author FR34KYN01535
 * @version 1.1
 */

public class CommandModule {
    public static final Logger logger = Bukkit.getLogger();
    
    public Plugin Plugin;
    public static TranslationModule TranslationManager;
    public static ArrayList<String> RootCommands; 
    public static ArrayList<ICommand> Commands;
    
    public CommandModule(Plugin plugin,TranslationModule translationManager,String[] rootCommands,ArrayList<ICommand> commands){
        Plugin = plugin;
        TranslationManager = translationManager;
        RootCommands = new ArrayList<String>();
        for(String rootCommand :rootCommands) {
            RootCommands.add(rootCommand.toLowerCase());
        }
        Commands = commands;
    }
    
    public static boolean onCommand(CommandSender sender, org.bukkit.command.Command root, String commandLabel, String[] args) {
      if (RootCommands.contains(root.getName().toLowerCase())) {
            ICommand command=null;
            if(args.length==0){
                command = new CmdHelp();
            }else{
                 for(ICommand cmd : Commands){
                    String[] cmdargs = cmd.getName();
                    if(args.length >= cmdargs.length){
                        int index = 0;
                        boolean found = true; 
                        for(String cmdarg : cmdargs){
                            if(!cmdarg.equals(args[index])){
                                found =false;
                            }
                        }
                        if(found){
                            command=cmd;
                            break;
                        }
                    }
                }
            }
            if (command == null) {
                Helpers.sendMessage(sender, ChatColor.RED + TranslationManager.getTranslation("COMMAND_MANAGER_UNKNOWN_COMMAND"));
                return true;
            }

            try {
                Permission permission = command.getPermissions();
                if(permission == null || hasPermission(sender,permission)){
                    if(!command.allowedInConsole() && !(sender instanceof Player)){
                        Helpers.sendMessage(sender, ChatColor.RED + TranslationManager.getTranslation("COMMAND_MANAGER_ONLY_CHAT"));
                    }else{
                        command.execute(sender, commandLabel, args); // Execute
                    }
                    return true;
                }
            } catch (Exception e) {
                sender.sendMessage(commandLabel);
                logger.info(e.getMessage());
            } finally {
                return true;
            }
        }

        return false;
    }
    
    public static boolean hasPermission(CommandSender player, Permission permission){
    if(player.hasPermission(permission)){
        return true;
    }else{    
        Helpers.sendMessage(player, ChatColor.RED + TranslationManager.getTranslation("COMMAND_MANAGER_NO_PERMISSION")+ " ("+permission.getName()+")");                 
        return false;
        }
    }
    
    public void onEnable() {
    }

    public void onDisable() {
    //
    }
}
