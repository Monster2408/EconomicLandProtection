package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.Event;

import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.CustomLocation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import static jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.EconomicLandProtection.PLPrefix;

public class onPlace implements Listener {

    @EventHandler
    public void onBreak(BlockPlaceEvent e) {
        Location loc = e.getBlock().getLocation();
        Player player = e.getPlayer();
        if (CustomLocation.isPlayerRegion(loc, player, "PLACE")) {
            e.setCancelled(true);
            player.sendMessage(PLPrefix + ChatColor.RED + "Block installation is prohibited because it is private land");
        }
    }

}
