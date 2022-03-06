/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.methods.others.manager;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemManager {

    public ItemStack get (Material material, int amount, int id, String name) {
        ItemStack stack = new ItemStack(material, amount, (short) id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack get (Material material, int amount, int id) {
        return new ItemStack(material, amount, (short) id);
    }

    public ItemStack getLeatherBoots (String name, Color color) {
        ItemStack stack = new ItemStack(Material.LEATHER_BOOTS, 1, (short) 0);
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setColor(color);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack getHead (Material material, int amount, int id, String name, String owner) {
        ItemStack stack = new ItemStack(material, amount, (short) id);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setOwner(owner);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack getEnchant (Material material, int amount, int id, String name) {
        ItemStack stack = new ItemStack(material, amount, (short) id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        stack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return stack;
    }

    public ItemStack getBowEnchant (Material material, int amount, int id, String name, Integer i) {
        ItemStack stack = new ItemStack(material, amount, (short) id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        stack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        if (i == 1) {
            stack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        } else if (i == 2) {
            stack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
            stack.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        }
        return stack;
    }

    public ItemStack getSwordEnchant (Material material, int amount, int id, String name, Integer i) {
        ItemStack stack = new ItemStack(material, amount, (short) id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        stack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        if (i == 0) {
            stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        } else if (i == 1) {
            stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        } else if (i == 2) {
            stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        }
        return stack;
    }

    public ItemStack getPickAxeEnchant (Material material, int amount, int id, String name) {
        ItemStack stack = new ItemStack(material, amount, (short) id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        stack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        stack.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
        return stack;
    }

    public ItemStack getChestEnchant (Material material, int amount, int id, String name, Integer i) {
        ItemStack stack = new ItemStack(material, amount, (short) id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        stack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        if (i == 2) {
            stack.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        } else if (i == 3) {
            stack.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        }
        return stack;
    }

    public ItemStack getRustung (Material material, int amount, int id, String name, Color color) {
        ItemStack stack = new ItemStack(material, amount, (short) id);
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(color);
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        stack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return stack;
    }

}
