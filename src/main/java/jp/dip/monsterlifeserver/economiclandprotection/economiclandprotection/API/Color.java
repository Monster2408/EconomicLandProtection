package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API;

import org.bukkit.ChatColor;

public class Color {

    public static String replaceColorCode(String source) {
        if (source == null)
            return null;
        return ChatColor.translateAlternateColorCodes('&', source);
    }

    public static String deleteColorCode(String source) {
        if (source == null) {
            return null;
        }
        return ChatColor.stripColor(source);
    }

}
