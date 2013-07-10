/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matejdro.bukkit.jail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;

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
        this.range = range;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public JailZone getJail() {
        return jail;
    }

    public void setJail(JailZone jail) {
        this.jail = jail;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Integer> getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(List<Integer> requiredItems) {
        this.requiredItems = requiredItems;
    }

    public boolean isRequireAllItems() {
        return requireAllItems;
    }

    public void setRequireAllItems(boolean requireAllItems) {
        this.requireAllItems = requireAllItems;
    }

    public int getRequiredHealth() {
        return requiredHealth;
    }

    public void setRequiredHealth(int requiredHealth) {
        this.requiredHealth = requiredHealth;
    }

    public int getRequiredExp() {
        return requiredExp;
    }

    public void setRequiredExp(int requiredExp) {
        this.requiredExp = requiredExp;
    }

    public int getRequiredMoney() {
        return requiredMoney;
    }

    public void setRequiredMoney(int requiredMoney) {
        this.requiredMoney = requiredMoney;
    }

    public int getRequiredHelmet() {
        return requiredHelmet;
    }

    public void setRequiredHelmet(int requiredHelmet) {
        this.requiredHelmet = requiredHelmet;
    }

    public int getRequiredChestplate() {
        return requiredChestplate;
    }

    public void setRequiredChestplate(int requiredChestplate) {
        this.requiredChestplate = requiredChestplate;
    }

    public int getRequiredLeggings() {
        return requiredLeggings;
    }

    public void setRequiredLeggings(int requiredLeggings) {
        this.requiredLeggings = requiredLeggings;
    }

    public int getRequiredBoots() {
        return requiredBoots;
    }

    public void setRequiredBoots(int requiredBoots) {
        this.requiredBoots = requiredBoots;
    }

    public int getRequiredClothingDye() {
        return requiredClothingDye;
    }

    public void setRequiredClothingDye(int requiredClothingDye) {
        this.requiredClothingDye = requiredClothingDye;
    }

    public boolean isRequireAllClothing() {
        return requireAllClothing;
    }

    public void setRequireAllClothing(boolean requireAllClothing) {
        this.requireAllClothing = requireAllClothing;
    }

    public boolean isRequireAllRequirements() {
        return requireAllRequirements;
    }

    public void setRequireAllRequirements(boolean requireAllRequirements) {
        this.requireAllRequirements = requireAllRequirements;
    }

    public int getPenaltyHealth() {
        return penaltyHealth;
    }

    public void setPenaltyHealth(int penaltyHealth) {
        this.penaltyHealth = penaltyHealth;
    }

    public int getPenaltyExp() {
        return penaltyExp;
    }

    public void setPenaltyExp(int penaltyExp) {
        this.penaltyExp = penaltyExp;
    }

    public int getPenaltyMoney() {
        return penaltyMoney;
    }

    public void setPenaltyMoney(int penaltyMoney) {
        this.penaltyMoney = penaltyMoney;
    }

    public List<Integer> getTakeItems() {
        return takeItems;
    }

    public void setTakeItems(List<Integer> takeItems) {
        this.takeItems = takeItems;
    }

    public List<Integer> getFriskItems() {
        return friskItems;
    }

    public void setFriskItems(List<Integer> friskItems) {
        this.friskItems = friskItems;
    }

    public int getUniformHelmet() {
        return uniformHelmet;
    }

    public void setUniformHelmet(int uniformHelmet) {
        this.uniformHelmet = uniformHelmet;
    }

    public int getUniformChestplate() {
        return uniformChestplate;
    }

    public void setUniformChestplate(int uniformChestplate) {
        this.uniformChestplate = uniformChestplate;
    }

    public int getUniformLeggings() {
        return uniformLeggings;
    }

    public void setUniformLeggings(int uniformLeggings) {
        this.uniformLeggings = uniformLeggings;
    }

    public int getUniformBoots() {
        return uniformBoots;
    }

    public void setUniformBoots(int uniformBoots) {
        this.uniformBoots = uniformBoots;
    }

    public int getUniformDye() {
        return uniformDye;
    }

    public void setUniformDye(int uniformDye) {
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
    
}
