/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matejdro.bukkit.jail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Wool;
import org.bukkit.permissions.PermissionDefault;

/**
 *
 * @author Fabrizio
 */
public class JailStick {
    
    private final int id;
    private int range;
    private int time;
    private JailZone jail;
    private String reason;
    private List<Integer> requiredItems;
    private boolean requireAllItems;
    private int requiredHealth;
    private int requiredExp;
    private int requiredMoney;
    private int requiredHelmet;
    private int requiredChestplate;
    private int requiredLeggings;
    private int requiredBoots;
    private int requiredClothingDye;
    private boolean requireAllClothing;
    private boolean requireAllRequirements;
    private int penaltyHealth;
    private int penaltyExp;
    private int penaltyMoney;
    private List<Integer> takeItems;
    private List<Integer> friskItems;
    private int uniformHelmet;
    private int uniformChestplate;
    private int uniformLeggings;
    private int uniformBoots;
    private int uniformDye;

    public JailStick(int id, int range, int time, JailZone jail, String reason, List<Integer> requiredItems, boolean requireAllItems, int requiredHealth, int requiredExp, int requiredMoney, int requiredHelmet, int requiredChestplate, int requiredLeggings, int requiredBoots, int requiredClothingDye, boolean requireAllClothing, boolean requireAllRequirements, int penaltyHealth, int penaltyExp, int penaltyMoney, List<Integer> takeItems, List<Integer> friskItems, int uniformHelmet, int uniformChestplate, int uniformLeggings, int uniformBoots, int uniformDye) {
        this.id = id;
        this.range = range;
        this.time = time;
        this.jail = jail;
        this.reason = reason;
        this.requiredItems = requiredItems;
        this.requireAllItems = requireAllItems;
        this.requiredHealth = requiredHealth;
        this.requiredExp = requiredExp;
        this.requiredMoney = requiredMoney;
        this.requiredHelmet = requiredHelmet;
        this.requiredChestplate = requiredChestplate;
        this.requiredLeggings = requiredLeggings;
        this.requiredBoots = requiredBoots;
        this.requiredClothingDye = requiredClothingDye;
        this.requireAllClothing = requireAllClothing;
        this.requireAllRequirements = requireAllRequirements;
        this.penaltyHealth = penaltyHealth;
        this.penaltyExp = penaltyExp;
        this.penaltyMoney = penaltyMoney;
        this.takeItems = takeItems;
        this.friskItems = friskItems;
        this.uniformHelmet = uniformHelmet;
        this.uniformChestplate = uniformChestplate;
        this.uniformLeggings = uniformLeggings;
        this.uniformBoots = uniformBoots;
        this.uniformDye = uniformDye;
    }

    public int getId() {
        return id;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".range", range);
        InputOutput.saveGlobalConfig();
        this.range = range;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".time", time);
        InputOutput.saveGlobalConfig();
        this.time = time;
    }

    public JailZone getJail() {
        return jail;
    }

    public void setJail(JailZone jail) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".jail", jail.getName());
        InputOutput.saveGlobalConfig();
        this.jail = jail;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".reason", reason);
        InputOutput.saveGlobalConfig();
        this.reason = reason;
    }

    public List<Integer> getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(List<Integer> requiredItems) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".required.items", requiredItems);
        InputOutput.saveGlobalConfig();
        this.requiredItems = requiredItems;
    }

    public boolean isRequireAllItems() {
        return requireAllItems;
    }

    public void setRequireAllItems(boolean requireAllItems) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".required.requireAllItems", requireAllItems);
        InputOutput.saveGlobalConfig();
        this.requireAllItems = requireAllItems;
    }

    public int getRequiredHealth() {
        return requiredHealth;
    }

    public void setRequiredHealth(int requiredHealth) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".requirements.health", requiredHealth);
        InputOutput.saveGlobalConfig();
        this.requiredHealth = requiredHealth;
    }

    public int getRequiredExp() {
        return requiredExp;
    }

    public void setRequiredExp(int requiredExp) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".requirements.exp", requiredExp);
        InputOutput.saveGlobalConfig();
        this.requiredExp = requiredExp;
    }

    public int getRequiredMoney() {
        return requiredMoney;
    }

    public void setRequiredMoney(int requiredMoney) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".requirements.money", requiredMoney);
        InputOutput.saveGlobalConfig();
        this.requiredMoney = requiredMoney;
    }

    public int getRequiredHelmet() {
        return requiredHelmet;
    }

    public void setRequiredHelmet(int requiredHelmet) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".requirements.clothing.helmet", requiredHelmet);
        InputOutput.saveGlobalConfig();
        this.requiredHelmet = requiredHelmet;
    }

    public int getRequiredChestplate() {
        return requiredChestplate;
    }

    public void setRequiredChestplate(int requiredChestplate) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".requirements.clothing.chestplate", requiredChestplate);
        InputOutput.saveGlobalConfig();
        this.requiredChestplate = requiredChestplate;
    }

    public int getRequiredLeggings() {
        return requiredLeggings;
    }

    public void setRequiredLeggings(int requiredLeggings) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".requirements.clothing.leggings", requiredLeggings);
        InputOutput.saveGlobalConfig();
        this.requiredLeggings = requiredLeggings;
    }

    public int getRequiredBoots() {
        return requiredBoots;
    }

    public void setRequiredBoots(int requiredBoots) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".requirements.clothing.boots", requiredBoots);
        InputOutput.saveGlobalConfig();
        this.requiredBoots = requiredBoots;
    }

    public int getRequiredClothingDye() {
        return requiredClothingDye;
    }

    public void setRequiredClothingDye(int requiredClothingDye) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".requirements.clothing.dye", requiredClothingDye);
        InputOutput.saveGlobalConfig();
        this.requiredClothingDye = requiredClothingDye;
    }

    public boolean isRequireAllClothing() {
        return requireAllClothing;
    }

    public void setRequireAllClothing(boolean requireAllClothing) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".requirements.clothing.requireAll", requireAllClothing);
        InputOutput.saveGlobalConfig();
        this.requireAllClothing = requireAllClothing;
    }

    public boolean isRequireAllRequirements() {
        return requireAllRequirements;
    }

    public void setRequireAllRequirements(boolean requireAllRequirements) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".requirements.requireAll", requireAllRequirements);
        InputOutput.saveGlobalConfig();
        this.requireAllRequirements = requireAllRequirements;
    }

    public int getPenaltyHealth() {
        return penaltyHealth;
    }

    public void setPenaltyHealth(int penaltyHealth) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".penalties.health", penaltyHealth);
        InputOutput.saveGlobalConfig();
        this.penaltyHealth = penaltyHealth;
    }

    public int getPenaltyExp() {
        return penaltyExp;
    }

    public void setPenaltyExp(int penaltyExp) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".penalties.exp", penaltyExp);
        InputOutput.saveGlobalConfig();
        this.penaltyExp = penaltyExp;
    }

    public int getPenaltyMoney() {
        return penaltyMoney;
    }

    public void setPenaltyMoney(int penaltyMoney) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".penalties.money", penaltyHealth);
        InputOutput.saveGlobalConfig();
        this.penaltyMoney = penaltyMoney;
    }

    public List<Integer> getTakeItems() {
        return takeItems;
    }

    public void setTakeItems(List<Integer> takeItems) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".take", takeItems);
        InputOutput.saveGlobalConfig();
        this.takeItems = takeItems;
    }

    public List<Integer> getFriskItems() {
        return friskItems;
    }

    public void setFriskItems(List<Integer> friskItems) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".frisk", friskItems);
        InputOutput.saveGlobalConfig();
        this.friskItems = friskItems;
    }

    public int getUniformHelmet() {
        return uniformHelmet;
    }

    public void setUniformHelmet(int uniformHelmet) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".uniform.helmet", uniformHelmet);
        InputOutput.saveGlobalConfig();
        this.uniformHelmet = uniformHelmet;
    }

    public int getUniformChestplate() {
        return uniformChestplate;
    }

    public void setUniformChestplate(int uniformChestplate) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".uniform.chestplate", uniformChestplate);
        InputOutput.saveGlobalConfig();
        this.uniformChestplate = uniformChestplate;
    }

    public int getUniformLeggings() {
        return uniformLeggings;
    }

    public void setUniformLeggings(int uniformLeggings) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".uniform.leggings", uniformLeggings);
        InputOutput.saveGlobalConfig();
        this.uniformLeggings = uniformLeggings;
    }

    public int getUniformBoots() {
        return uniformBoots;
    }

    public void setUniformBoots(int uniformBoots) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".uniform.boots", uniformBoots);
        InputOutput.saveGlobalConfig();
        this.uniformBoots = uniformBoots;
    }

    public int getUniformDye() {
        return uniformDye;
    }

    public void setUniformDye(int uniformDye) {
        InputOutput.getGlobalConfig().set("JailSticks." + getId() + ".uniform.dye", uniformDye);
        InputOutput.saveGlobalConfig();
        this.uniformDye = uniformDye;
    }
    
    public static JailStick loadJailStick(int itemId) {
        ConfigurationSection stick = InputOutput.getGlobalConfig().getConfigurationSection("JailSticks." + itemId);
        int range = stick.getInt("range", 5);
        int time = stick.getInt("time", 10);
        JailZone jail = Jail.zones.get(stick.getString("jail", null)); // Does this return null when jail is undefined or defined blank?
        String reason = stick.getString("reason", "");
        List<Integer> requiredItems = stick.getIntegerList("required.items");
        boolean requireAllItems = stick.getBoolean("required.requireAllItems", false);
        int requiredHealth = stick.getInt("required.health", 0);
        int requiredExp = stick.getInt("required.exp", 0);
        int requiredMoney = stick.getInt("required.money", 0);
        int requiredHelmet = stick.getInt("required.clothing.helmet", 0);
        int requiredChestplate = stick.getInt("required.clothing.helmet", 0);
        int requiredLeggings = stick.getInt("required.clothing.helmet", 0);
        int requiredBoots = stick.getInt("required.clothing.helmet", 0);
        int requiredClothingDye = stick.getInt("required.clothing.dye", -1);
        boolean requireAllClothing = stick.getBoolean("required.clothing.requireAll", true);
        boolean requireAllRequirements = stick.getBoolean("required.requireAll", false);
        int penaltyHealth = stick.getInt("penalty.health", 0);
        int penaltyExp = stick.getInt("penalty.exp", 0);
        int penaltyMoney = stick.getInt("penalty.money", 0);
        List<Integer> takeItems = stick.getIntegerList("take.items");
        List<Integer> friskItems = stick.getIntegerList("frisk.items");
        int uniformHelmet = stick.getInt("uniform.helmet", 0);
        int uniformChestplate = stick.getInt("uniform.chestplate", 0);
        int uniformLeggings = stick.getInt("uniform.leggings", 0);
        int uniformBoots = stick.getInt("uniform.boots", 0);
        int uniformDye = stick.getInt("uniform.dye", -1);
        
        return new JailStick(itemId, range, time, jail, reason, requiredItems, requireAllItems, requiredHealth, requiredExp, requiredMoney, requiredHelmet, requiredChestplate, requiredLeggings, requiredBoots, requiredClothingDye, requireAllClothing, requireAllRequirements, penaltyHealth, penaltyExp, penaltyMoney, takeItems, friskItems, uniformHelmet, uniformChestplate, uniformLeggings, uniformBoots, uniformDye);
    }
    
    public static void reloadJailSticks() {
        ConfigurationSection jailSticksSection = InputOutput.getGlobalConfig().getConfigurationSection("JailSticks");
        for (String stick : jailSticksSection.getKeys(false)) {
            Jail.addJailStick(loadJailStick(Integer.parseInt(stick)));
        }
    }
    
    public static JailStick getJailStick(Player p) {
        // Check the player has jailstick enabled, if they are holding a valid jailstick and that they have permission to use that jailstick.
        if (Jail.hasJailStickEnabled(p) && Jail.containsJailStick(p.getItemInHand().getTypeId()) && Util.permission(p, "jail.usejailstick." + p.getItemInHand().getTypeId() , PermissionDefault.OP)) {
            JailStick stick = Jail.getJailStick(p.getItemInHand().getTypeId());
            ItemStack helmet = p.getInventory().getHelmet();
            if (stick.getUniformHelmet() != 0) {
                if (stick.getUniformHelmet() != helmet.getTypeId()
                        || ( helmet.getType() == Material.LEATHER_HELMET && stick.getUniformDye() >= 0
                        && ((LeatherArmorMeta) helmet.getItemMeta()).getColor().equals(new Wool(stick.getUniformDye()).getColor()) ) ) {
                    p.sendMessage("You are not wearing the correct helmet to use your current item as a jailstick.");
                    return null;
                }
            }
            if (stick.getUniformChestplate()!= 0) {
                ItemStack chestplate = p.getInventory().getChestplate();
                if (stick.getUniformChestplate() != chestplate.getTypeId()
                        || ( chestplate.getType() == Material.LEATHER_CHESTPLATE && stick.getUniformDye() >= 0
                        && ((LeatherArmorMeta) chestplate.getItemMeta()).getColor().equals(new Wool(stick.getUniformDye()).getColor()) ) ) {
                    p.sendMessage("You are not wearing the correct chestplate to use your current item as a jailstick.");
                    return null;
                }
            }
            if (stick.getUniformLeggings()!= 0) {
                ItemStack leggings = p.getInventory().getLeggings();
                if (stick.getUniformLeggings() != leggings.getTypeId()
                        || ( leggings.getType() == Material.LEATHER_LEGGINGS && stick.getUniformDye() >= 0
                        && ((LeatherArmorMeta) leggings.getItemMeta()).getColor().equals(new Wool(stick.getUniformDye()).getColor()) ) ) {
                    p.sendMessage("You are not wearing the correct leggings to use your current item as a jailstick.");
                    return null;
                }
            }
            if (stick.getUniformBoots()!= 0) {
                ItemStack boots = p.getInventory().getLeggings();
                if (stick.getUniformBoots() != boots.getTypeId()
                        || ( boots.getType() == Material.LEATHER_BOOTS && stick.getUniformDye() >= 0
                        && ((LeatherArmorMeta) boots.getItemMeta()).getColor().equals(new Wool(stick.getUniformDye()).getColor()) ) ) {
                    p.sendMessage("You are not wearing the correct boots to use your current item as a jailstick.");
                    return null;
                }
            }
            // The player passed all the conditions to use their jailstick, return the jailstick object
            return stick;
        } else {
            // Return null and do nothing, the player isn't holding a Jail Stick
            return null;
        }
    }
    
}
