package com.matejdro.bukkit.jail;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * User: Luke
 * Date: 05/07/13
 * Time: 13:36
 */
public class JailPlayer {

    public static HashMap<String,Integer> players = new HashMap<String,Integer>();

    public static void start(Player jailer, Player target){
        if(!players.containsKey(jailer.getName())){
            players.put(jailer.getName(), 0);
        }
        Util.Message(ChatColor.AQUA + "---------- Jail " + target.getName() + " ----------", jailer);
        Util.Message(ChatColor.GREEN + "How long do you want to jail " + ChatColor.RED + target.getName() + ChatColor.GREEN + " for? Type the number of minutes in chat!", jailer);
        Util.Message(ChatColor.AQUA + "-----------------------------------------------", jailer);
    }

}
