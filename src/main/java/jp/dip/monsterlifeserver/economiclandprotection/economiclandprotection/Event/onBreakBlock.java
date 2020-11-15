package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.Event;


import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.CustomLocation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.EconomicLandProtection.PLPrefix;

public class onBreakBlock implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Location loc = e.getBlock().getLocation();
        Player player = e.getPlayer();
        if (CustomLocation.isPlayerRegion(loc, player, "BREAK")) {
            e.setCancelled(true);
            player.sendMessage(PLPrefix + ChatColor.RED + "Block destruction is prohibited because it is private land");
        }
    }

}
