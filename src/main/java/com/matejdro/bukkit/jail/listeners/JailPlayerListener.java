package com.matejdro.bukkit.jail.listeners;

import com.matejdro.bukkit.jail.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.matejdro.bukkit.jail.commands.JailSetCommand;

@SuppressWarnings("deprecation")
public class JailPlayerListener implements Listener {

    @EventHandler()
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getItemInHand().getTypeId() == Settings.getGlobalInt(Setting.SelectionTool)) {
            if (JailZoneCreation.players.containsKey(event.getPlayer().getName())) {
                JailZoneCreation.select(event.getPlayer(), event.getClickedBlock());
                event.setCancelled(true);
            } else if (JailCellCreation.players.containsKey(event.getPlayer().getName())) {
                JailCellCreation.select(event.getPlayer(), event.getClickedBlock());
                event.setCancelled(true);
            } else if (JailSetCommand.players.containsKey(event.getPlayer().getName())) {
                JailSetCommand.RightClick(event.getClickedBlock(), event.getPlayer());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player damager = (Player) event.getDamager();
        Player player = (Player) event.getEntity();

        JailStick stick = JailStick.getJailStick(damager);

        if (stick != null && stick.checkRequirements(damager, player)) {
            stick.invokeActions(damager, player);
            JailPrisoner prisoner = new JailPrisoner(player.getName(), stick.getTime(), stick.getJail(), "", false, "", stick.getReason(), false, "", "", "", player.getGameMode());
            PrisonerManager.PrepareJail(prisoner, player);
            JailLog logger = new JailLog();
            damager.sendMessage(ChatColor.RED + "You jailed " + ChatColor.GREEN + player.getName() + ChatColor.RED + " for " + ChatColor.GREEN + stick.getTime() + ChatColor.RED + " minutes");
            player.sendMessage(ChatColor.RED + "You have been jailed by " + ChatColor.GREEN + damager.getName() + ChatColor.RED + " for " + ChatColor.GREEN + stick.getTime() + ChatColor.RED + " minutes");
            if (Settings.getGlobalBoolean(Setting.EnableLogging)) {
                logger.logToFile(player, stick.getTime(), stick.getReason(), damager.getName(), player.getName());
            }
        }

    }

    @EventHandler()
    public void onPlayerChat(PlayerChatEvent event) {
        if (JailCellCreation.players.containsKey(event.getPlayer().getName())) {
            if (JailCellCreation.chatmessage(event.getPlayer(), event.getMessage()));
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().isOp() && Jail.updateNeeded) {
            event.getPlayer().sendMessage(ChatColor.BLUE + "There is an update for the jail plugin!");
        }

        if (Jail.prisoners.containsKey(event.getPlayer().getName().toLowerCase())) {
            JailPrisoner prisoner = Jail.prisoners.get(event.getPlayer().getName().toLowerCase());
            event.getPlayer().teleport(prisoner.getJail().getTeleportLocation());

            if (prisoner.offlinePending()) {
                if (prisoner.getTransferDestination().isEmpty()) {
                    if (prisoner.getRemainingTime() != 0) {
                        PrisonerManager.Jail(prisoner, event.getPlayer());
                    } else if (prisoner.getJail() != null) {
                        PrisonerManager.UnJail(prisoner, event.getPlayer());
                        return;
                    } else {
                        prisoner.delete();
                        return;
                    }
                } else {
                    PrisonerManager.Transfer(prisoner, event.getPlayer());
                }

            }

            if (prisoner.getJail().getSettings().getBoolean(Setting.IgnorePrisonersSleepingState)) {
                event.getPlayer().setSleepingIgnored(true);
            }
        }
    }

    @EventHandler()
    public void onPlayerQuit(PlayerQuitEvent event) {
        JailPrisoner prisoner = Jail.prisoners.get(event.getPlayer().getName().toLowerCase());
        if (prisoner != null) {
            prisoner.killGuards();
        }
    }
}
