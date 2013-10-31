package com.matejdro.bukkit.jail;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class PrisonerManager {
	
	public static void PrepareJail(JailPrisoner prisoner, Player player)
	{
		if (player == null)
		{
			prisoner.setOfflinePending(true);
			if (prisoner.getJail() != null)
			{
				Util.debug(prisoner, "Searching for requested cell");
				JailCell cell = prisoner.getJail().getRequestedCell(prisoner);
				if (cell != null && (cell.getPlayerName() == null || cell.getPlayerName().trim().equals("")))
				{
					Util.debug(prisoner, "Found requested cell");
					cell.setPlayerName(prisoner.getName());
					cell.update();
				}
			}
			
			InputOutput.InsertPrisoner(prisoner);
			Jail.prisoners.put(prisoner.getName(), prisoner);			
		}
		else
		{
			prisoner.setOfflinePending(false);
			Jail(prisoner, player);			
		}
		
		//Log jailing into console
		if (Settings.getGlobalBoolean(Setting.LogJailingIntoConsole))
		{
			String times;
			if (prisoner.getRemainingTime() < 0) times = "forever"; else times = "for " + String.valueOf(prisoner.getRemainingTime()) + " minutes";
			
			Jail.log.info("Player " + prisoner.getName() + " was jailed by " + prisoner.getJailer() + " " + times);
		}
	}
	
	/**
	 * Performs jailing of specified JailPrisoner. 
	 * If you just want to jail someone, I recommend using JailAPI.jailPlayer, 
	 * because it supports offline jail and it's easier to do.
	 * @param prisoner JailPrisoner class of the new prisoner. Must be already inserted into database
	 * @param player Player that will be teleported
	 */
	@SuppressWarnings("unchecked")
	public static void Jail(JailPrisoner prisoner, Player player)
	{
		if (!prisoner.getName().equals(player.getName().toLowerCase())) return;
		prisoner.SetBeingReleased(true);
		JailZone jail = prisoner.getJail();
		if (jail == null)
		{
			Util.debug(prisoner, "searching for nearest jail");
			jail = JailZoneManager.findNearestJail(player.getLocation());
			prisoner.setJail(jail);
		}
		if (jail == null)
		{
			Util.Message("You are lucky! The server admin was too lazy to set up a jail. Go now!", player);
			Jail.log.info("[Jail] There is no jail to pick! Make sure, you have build at least one jail and at least one jail is set to automatic!");
			return;
		}
		prisoner.setOfflinePending(false);
		if (prisoner.getReason().isEmpty())
			Util.Message(ChatColor.RED + jail.getSettings().getString(Setting.MessageJail), player);
		else
			Util.Message(ChatColor.RED + jail.getSettings().getString(Setting.MessageJailReason).replace("<Reason>", prisoner.getReason()), player);

		if (jail.getSettings().getBoolean(Setting.DeleteInventoryOnJail)) 
		{
			player.getInventory().clear();
			player.getInventory().setArmorContents(new ItemStack[] { null, null, null, null });
			
		}
		
		prisoner.setPreviousPosition(player.getLocation());
		
		JailCell cell = jail.getRequestedCell(prisoner);
		if (cell == null || (cell.getPlayerName() != null && !cell.getPlayerName().equals("") && !cell.getPlayerName().equals(prisoner.getName()))) 
		{
			Util.debug(prisoner, "No requested cell. searching for empty cell");
			cell = null;
			cell = jail.getEmptyCell();
		}
		if (cell != null)
		{
			Util.debug(prisoner, "Found cell!");
			cell.setPlayerName(player.getName());
			prisoner.setCell(cell);
			player.teleport(prisoner.getTeleportLocation());
			prisoner.updateSign();
			if (jail.getSettings().getBoolean(Setting.StoreInventory) && cell.getChest() != null)
			{
				Chest chest = cell.getChest();
				chest.getInventory().clear();
				for (int i = 0;i<40;i++)
				{
					if (chest.getInventory().getSize() <= Util.getNumberOfOccupiedItemSlots(chest.getInventory().getContents())) break;
					if (player.getInventory().getItem(i) == null || player.getInventory().getItem(i).getType() == Material.AIR) continue;
					chest.getInventory().setItem(i, player.getInventory().getItem(i));
					chest.getInventory().getItem(i).setItemMeta(player.getInventory().getItem(i).getItemMeta());
					player.getInventory().clear(i);
				}								
			}
			cell.update();
		}
		else
		{
			player.teleport(prisoner.getTeleportLocation());
		}
		
		if (jail.getSettings().getBoolean(Setting.StoreInventory)) 
		{
			prisoner.storeInventory(player.getName(), player.getInventory());
			for (int i = 0;i<40;i++)
			{
				player.getInventory().clear(i);
			}
			
		}
		
		if (jail.getSettings().getBoolean(Setting.SpoutChangeSkin))
			Util.changeSkin(player, jail.getSettings().getString(Setting.SpoutSkinChangeURL));
		
		if (jail.getSettings().getBoolean(Setting.EnableChangingPermissions))
		{
			prisoner.setOldPermissions(Util.getPermissionsGroups(player.getName(), jail.getTeleportLocation().getWorld().getName()));
			Util.setPermissionsGroups(player.getName(), (ArrayList<String>) jail.getSettings().getList(Setting.PrisonersPermissionsGroups), jail.getTeleportLocation().getWorld().getName());
		}
		
		 if (prisoner.getJail().getSettings().getBoolean(Setting.IgnorePrisonersSleepingState))
			 player.setSleepingIgnored(true);

		
		if (Jail.prisoners.containsKey(prisoner.getName()))
			InputOutput.UpdatePrisoner(prisoner);
		else
			InputOutput.InsertPrisoner(prisoner);
		Jail.prisoners.put(prisoner.getName(), prisoner);
		prisoner.SetBeingReleased(false);
		
		int minFood = jail.getSettings().getInt(Setting.FoodControlMinimumFood);
		int maxFood = jail.getSettings().getInt(Setting.FoodControlMaximumFood);
		if (player.getFoodLevel() <  minFood)
		{
			player.setFoodLevel(minFood);
		}
		else if (player.getFoodLevel() > maxFood)
		{
			player.setFoodLevel(maxFood);
		}
		
		for (Object o : jail.getSettings().getList(Setting.ExecutedCommandsOnJail))
		{
			String s = (String) o;
			Server cs = (Server) Jail.instance.getServer();
			CommandSender coms = Jail.instance.getServer().getConsoleSender();
			cs.dispatchCommand(coms,prisoner.parseTags(s));
		}
		
	}
	
	/**
	 * Performs releasing of specified JailPrisoner. 
	 * If you just want to release someone, I recommend using prisoner.release, 
	 * because it supports offline release and it's easier to do.
	 * @param prisoner prisoner that will be released
	 * @param player Player that will be teleported
	 */
	public static void UnJail(JailPrisoner prisoner, Player player)
	{
		prisoner.SetBeingReleased(true);
		JailZone jail = prisoner.getJail();	
		Util.Message(ChatColor.GREEN + jail.getSettings().getString(Setting.MessageUnJail), player);
		
		if (jail.getSettings().getBoolean(Setting.SpoutChangeSkin))
			Util.changeSkin(player, null);
		
		if (jail.getSettings().getBoolean(Setting.EnableChangingPermissions) && !jail.getSettings().getBoolean(Setting.RestorePermissionsToEscapedPrisoners))
		{
			Util.setPermissionsGroups(player.getName(), prisoner.getOldPermissions(), jail.getTeleportLocation().getWorld().getName());
		}
		
		player.setSleepingIgnored(false);
		
		JailCell cell = prisoner.getCell();
		if (cell != null)
		{
			if (cell.getChest() != null)
			{
				Chest chest = cell.getChest();
				for (int i = 0;i<chest.getInventory().getSize();i++)
				{
					if (chest.getInventory().getItem(i) == null || chest.getInventory().getItem(i).getType() == Material.AIR) continue;
					if (player.getInventory().firstEmpty() == -1)
						player.getWorld().dropItem(player.getLocation(), chest.getInventory().getItem(i));
					else{
						player.getInventory().addItem(chest.getInventory().getItem(i));
					}
				}
				chest.getInventory().clear();				
			}
			for (Sign sign : cell.getSigns())
			{
				sign.setLine(0, "");
				sign.setLine(1, "");
				sign.setLine(2, "");
				sign.setLine(3, "");
				sign.update();

			}
			cell.setPlayerName("");
			cell.update();

		}
		
		if (jail.getSettings().getBoolean(Setting.TeleportPrisonerOnRelease)) player.teleport(prisoner.getReleaseTeleportLocation());
		
		if(prisoner.getPreviousGameMode() != null) player.setGameMode(prisoner.getPreviousGameMode());
		
		prisoner.restoreInventory(player);
		prisoner.delete();
		
		for (Object o : jail.getSettings().getList(Setting.ExecutedCommandsOnRelease))
		{
			String s = (String) o;
			Server cs = (Server) Jail.instance.getServer();
			CommandSender coms = Jail.instance.getServer().getConsoleSender();
			cs.dispatchCommand(coms,prisoner.parseTags(s));
		}
	}
	
	/**
	 * Initiate transfer of every prisoner in specified jail zone to another nearest jail zone
	 */
	public static void PrepareTransferAll(JailZone jail)
	{
		PrepareTransferAll(jail, "find nearest");
	}
	
	/**
	 * Initiate transfer of every prisoner in specified jail zone to another jail zone
	 * @param target Name of the destination jail zone
	 */
	public static void PrepareTransferAll(JailZone zone, String target)
	{
		for (JailPrisoner prisoner : zone.getPrisoners())
		{
			prisoner.setTransferDestination(target);
			Player player = Jail.instance.getServer().getPlayerExact(prisoner.getName());
			if (player == null)
			{
				
				prisoner.setOfflinePending(true);
				InputOutput.UpdatePrisoner(prisoner);
				Jail.prisoners.put(prisoner.getName(), prisoner);
				
			}
			else
			{
				Transfer(prisoner, player);
				
			}
		}
		
	}
	
	/**
	 * Performs transfer of specified JailPrisoner. 
	 * If you just want to transfer someone, I recommend using prisoner.transfer, 
	 * because it supports offline transfer and it's easier to do.
	 * @param prisoner Prisoner that will be transfered
	 * @param player Player that will be teleported
	 */
	public static void Transfer(JailPrisoner prisoner, Player player)
	{
		if ("find nearest".equals(prisoner.getTransferDestination())) prisoner.setTransferDestination(JailZoneManager.findNearestJail(player.getLocation(), prisoner.getJail().getName()).getName());
		
		if (prisoner.getCell() != null)
		{
			Inventory inventory = player.getInventory();
			JailCell cell = prisoner.getCell();
			cell.setPlayerName("");
			for (Sign sign : cell.getSigns())
			{
				sign.setLine(0, "");
				sign.setLine(1, "");
				sign.setLine(2, "");
				sign.setLine(3, "");
				sign.update();
			}
			
			if (cell.getChest() != null) 
			{
				for (ItemStack i: cell.getChest().getInventory().getContents())
				{
					if (i == null || i.getType() == Material.AIR) continue;
					inventory.addItem(i);
				}
				cell.getChest().getInventory().clear();
			}
			prisoner.setCell(null);
		}
						
		prisoner.SetBeingReleased(true);
		
		String targetJail = prisoner.getTransferDestination();
		if (targetJail.contains(":"))
		{
			prisoner.setRequestedCell(targetJail.split(":")[1]);
			targetJail = targetJail.split(":")[0];			
		}
		
		JailZone jail = Jail.zones.get(targetJail);
		prisoner.setJail(jail);
		prisoner.setTransferDestination("");
		prisoner.setOfflinePending(false);
		Util.Message(jail.getSettings().getString(Setting.MessageTransfer), player);
		Jail.prisoners.put(prisoner.getName(),prisoner);

		JailCell cell = jail.getRequestedCell(prisoner);
		if (cell == null || (cell.getPlayerName() != null && !cell.getPlayerName().equals("") && !cell.getPlayerName().equals(prisoner.getName()))) 
		{
			cell = null;
			cell = jail.getEmptyCell();
		}
		if (cell != null)
		{
			cell.setPlayerName(player.getName());
			prisoner.setCell(cell);
			player.teleport(prisoner.getTeleportLocation());
			prisoner.updateSign();
			if (jail.getSettings().getBoolean(Setting.StoreInventory) && cell.getChest() != null)
			{
				Chest chest = cell.getChest();
				chest.getInventory().clear();
				for (int i = 0;i<40;i++)
				{
					if (chest.getInventory().getSize() <= Util.getNumberOfOccupiedItemSlots(chest.getInventory().getContents())) break;
					if (player.getInventory().getItem(i) == null || player.getInventory().getItem(i).getType() == Material.AIR) continue;
					chest.getInventory().addItem(player.getInventory().getItem(i));
					player.getInventory().clear(i);
				}		
			}
			cell.update();
		}
		else
		{
			player.teleport(prisoner.getTeleportLocation());
		}
		
		if (jail.getSettings().getBoolean(Setting.StoreInventory)) 
		{
			prisoner.storeInventory(player.getName(), player.getInventory());
			player.getInventory().clear();
		}
		
		prisoner.SetBeingReleased(false);
		InputOutput.UpdatePrisoner(prisoner);
	}
}
