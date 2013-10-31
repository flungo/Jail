package com.matejdro.bukkit.jail.commands;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.matejdro.bukkit.jail.Jail;

public class JailRecordCommand extends BaseCommand{

	private int messageNumber = 0;
	
	public JailRecordCommand(){
		needPlayer = true;
		adminCommand = true;
		permission = "jail.command.jailrecord";
	}
	
	@SuppressWarnings({ "resource", "unused" })
	@Override
	public Boolean run(CommandSender sender, String[] args) {
		if(args.length < 1){
			sender.sendMessage(ChatColor.RED + "You must specify a player!");
		}else{
			try{
				File dataFolder = Jail.instance.getDataFolder();
				if(!dataFolder.exists())
	            {
	                dataFolder.mkdir();
	            }
				
				File jailLogFile = new File(Jail.instance.getDataFolder(), "jailLog.txt");
	            if(!jailLogFile.exists())
	            {
	                jailLogFile.createNewFile();
	            }
	            
	            Scanner scanner = new Scanner(jailLogFile);
	            
	            int lineNum = 0;
	            int timesJailed = 0;
	            
	            while(scanner.hasNextLine()){
	            	String line = scanner.nextLine();
	            	lineNum++;
	            	if(line.contains(args[0] + " ")){
	            		timesJailed ++;
	            		sender.sendMessage(line);
	            		messageNumber ++;
	            	}
	            }
	            
	            sender.sendMessage(ChatColor.RED + "This player has been jailed " + ChatColor.GREEN + timesJailed + ChatColor.RED + " times");
	            
	            if(messageNumber == 0){
	            	sender.sendMessage(ChatColor.RED + "This player has not been jailed!");
	            }else{
	            	messageNumber = 0;
	            }
	            
	            if(timesJailed !=  0){
	            	timesJailed = 0;
	            }
	            
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return true;
	}

}
