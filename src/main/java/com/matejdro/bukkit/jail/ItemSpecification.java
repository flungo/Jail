/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matejdro.bukkit.jail;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Fabrizio
 */
public class ItemSpecification {
	
	private final int id;
	private Short dv;

	public ItemSpecification(int id) {
		this(id, null);
	}

	public ItemSpecification(int id, Short dv) {
		this.id = id;
		this.dv = dv;
	}
	
	public ItemSpecification(String string) {
		String[] item = string.split(":");
		this.id = Integer.parseInt(item[0]);
	    if (item.length > 1) {
			this.dv = Short.parseShort(item[1]);
		} else {
			this.dv = null;
		}
	}

	public int getId() {
		return id;
	}

	public Short getDv() {
		return dv;
	}
	
	public boolean match(ItemSpecification spec) {
		if (spec == null) {
			return false;
		}
		if (spec == this) {
			return true;
		}
		if (spec.id != this.id) {
			return false;
		}
		if (this.dv != null && !spec.dv.equals(this.dv)) {
			return false;
		}
		return true;
	}
	
	public boolean match(ItemStack item) {
		if (item == null) {
			return false;
		}
		if (this.id != item.getTypeId()) {
			return false;
		}
		if (this.dv != null && !this.dv.equals(item.getDurability())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ItemSpec[id=" + id + ", dv=" + dv + "]";
	}
	
	public static List<ItemSpecification> convertStringList(List<String> stringList) {
		List<ItemSpecification> itemList = new ArrayList<ItemSpecification>();
		for (String item : stringList) {
			itemList.add(new ItemSpecification(item));
		}
		return itemList;
	}
	
	public static boolean listContains(List<ItemSpecification> list, ItemStack stack) {
		if (stack == null) {
			return false;
		}
		for (ItemSpecification spec : list) {
			if (spec.match(stack)) {
				return true;
			}
		}
		return false;
	}
	
}
