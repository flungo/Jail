package com.matejdro.bukkit.jail.commands;

import org.bukkit.command.CommandSender;

import com.matejdro.bukkit.jail.Jail;

public class JailVersionCommand extends BaseCommand{
	
	@Override
	public Boolean run(CommandSender sender, String[] args) {
		sender.sendMessage("Jail Version: " + Jail.instance.getDescription().getVersion());
		return true;
	}

}
