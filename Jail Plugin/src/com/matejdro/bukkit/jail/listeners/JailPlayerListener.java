package com.matejdro.bukkit.jail.listeners;

import com.matejdro.bukkit.jail.*;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionDefault;

import com.matejdro.bukkit.jail.commands.JailSetCommand;

@SuppressWarnings("deprecation")
public class JailPlayerListener implements Listener {
	
	@EventHandler()
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getItemInHand().getTypeId() == Settings.getGlobalInt(Setting.SelectionTool))
		{
			if ( JailZoneCreation.players.containsKey(event.getPlayer().getName()))
			{
				JailZoneCreation.select(event.getPlayer(), event.getClickedBlock());
				event.setCancelled(true);
			}
			else if ( JailCellCreation.players.containsKey(event.getPlayer().getName()))
			{
				JailCellCreation.select(event.getPlayer(), event.getClickedBlock());
				event.setCancelled(true);
			}
			else if ( JailSetCommand.players.containsKey(event.getPlayer().getName()))
			{
				JailSetCommand.RightClick(event.getClickedBlock(), event.getPlayer());
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent event){
		if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
		
		Player damager = (Player) event.getDamager();
		Player player = (Player) event.getEntity();
		
		String[] param = InputOutput.jailStickParameters.get(damager.getItemInHand().getTypeId());
		
		if(!Jail.jailStickToggle.containsKey(damager)){
			Jail.jailStickToggle.put(damager, false);
		}

		if(Util.permission(damager, "jail.usejailstick." + String.valueOf(damager.getItemInHand().getTypeId()), PermissionDefault.OP) && Jail.jailStickToggle.get(damager)){
            if(player != null){
                JailPrisoner prisoner = new JailPrisoner(player.getName(), Integer.parseInt(param[2]) * 6, param[3], "", false, "", param[4], false, "", "", "", player.getGameMode());
                PrisonerManager.PrepareJail(prisoner, player);
                PrisonerManager.Jail(prisoner, player);
                JailLog logger = new JailLog();
                damager.sendMessage(ChatColor.RED + "You jailed " + ChatColor.GREEN + player.getName() + ChatColor.RED +  " for " + ChatColor.GREEN + Integer.parseInt(param[2]) + ChatColor.RED + " minutes");
                if(Settings.getGlobalBoolean(Setting.EnableLogging)){
                    logger.logToFile(player, Integer.parseInt(param[2]), param[4], damager.getName(), player.getName());
                }
            }else{
                Util.Message(ChatColor.RED + "That Player does not exist?!?!?!", damager);
            }
		}
	}

	@EventHandler()
	public void onPlayerChat(PlayerChatEvent event) {
		if ( JailCellCreation.players.containsKey(event.getPlayer().getName()))
		{
			if (JailCellCreation.chatmessage(event.getPlayer(), event.getMessage()));
				event.setCancelled(true);
		}

        for(Object o : Settings.getGlobalList(Setting.BannedWords)){
        	String word = (String) o;
            if(event.getMessage().toLowerCase().contains(word + " ") && Settings.getGlobalBoolean(Setting.EnableJailSwear)){
                event.setCancelled(true);
                JailPrisoner prisoner = new JailPrisoner(event.getPlayer().getName(), Settings.getGlobalInt(Setting.JailSwearTime) * 6, "", "", false, "", "Swearing", true, "", "", "", event.getPlayer().getGameMode());
                PrisonerManager.PrepareJail(prisoner, event.getPlayer());
                PrisonerManager.Jail(prisoner, event.getPlayer());
                JailLog logger = new JailLog();
                if(Settings.getGlobalBoolean(Setting.EnableLogging)){
                        logger.logToFile(event.getPlayer(), Settings.getGlobalInt(Setting.JailSwearTime), "Swearing", "Console", event.getPlayer().getName());
                }
             }
        }
	}

	@EventHandler()
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(event.getPlayer().isOp() && Jail.updateNeeded){
			event.getPlayer().sendMessage(ChatColor.BLUE + "There is an update for the jail plugin!");
		}
		
		 if (Jail.prisoners.containsKey(event.getPlayer().getName().toLowerCase()))
		 {
			 JailPrisoner prisoner = Jail.prisoners.get(event.getPlayer().getName().toLowerCase());
			 if(prisoner != null){
				 if(prisoner.getCell() == null){
					 JailZone jail = JailZoneManager.findNearestJail(event.getPlayer().getLocation());
					 prisoner.setCell(jail.getNearestCell(event.getPlayer().getLocation()));
				 }
				 
				 //Add a ternary null check, check again since we tried to set their cell above, and if it is still null then just teleport them to the jail's teleport location
				 event.getPlayer().teleport(prisoner.getCell() != null ? prisoner.getCell().getTeleportLocation() : prisoner.getJail().getTeleportLocation());
				 event.getPlayer().setGameMode(GameMode.SURVIVAL);
		}
			 
			 if (prisoner.offlinePending())
			 {
				 if (prisoner.getTransferDestination().isEmpty())
				 {
					 if (prisoner.getRemainingTime() != 0)
					 {
						 PrisonerManager.Jail(prisoner, event.getPlayer());
					 }
					 else if (prisoner.getJail() != null)
					 {
						 PrisonerManager.UnJail(prisoner, event.getPlayer());
						 return;
					 } 
					 else
					 {
						 prisoner.delete();
						 return;
					 }
				 }
				 else
				 {
					 PrisonerManager.Transfer(prisoner, event.getPlayer());
				 }
				 
			 }
			 
			 if (prisoner.getJail().getSettings().getBoolean(Setting.IgnorePrisonersSleepingState))
				 event.getPlayer().setSleepingIgnored(true);
		 }
	 }
	 
	@EventHandler()
	public void onPlayerQuit(PlayerQuitEvent event) {
		JailPrisoner prisoner = Jail.prisoners.get(event.getPlayer().getName().toLowerCase());
		if (prisoner == null) return;

		prisoner.killGuards();
	 }
}
