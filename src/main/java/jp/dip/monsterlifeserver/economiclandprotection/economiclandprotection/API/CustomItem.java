package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CustomItem {

    public static ItemStack createGuiItem(Material material, String name, String...lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Color.replaceColorCode(name));
        ArrayList<String> metalore = new ArrayList<String>();

        for(String lorecomments : lore) {
            metalore.add(lorecomments);
        }
        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createGuiItem(Material material, String name) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Color.replaceColorCode(name));
        item.setItemMeta(meta);
        return item;
    }

}
