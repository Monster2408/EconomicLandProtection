package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.Event;

import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.CustomLocation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.EconomicLandProtection.PLPrefix;

public class onOpenDoor implements Listener {

    @EventHandler
    public void onOpenDoor(PlayerInteractEvent e) {
        Action action = e.getAction();
        Block clicked = e.getClickedBlock();
        //Left or Right click?
        if (action == Action.RIGHT_CLICK_BLOCK) {
            //Door Block?
            if (clicked.getType() == Material.OAK_DOOR || clicked.getType() == Material.ACACIA_DOOR || clicked.getType() == Material.BIRCH_DOOR || clicked.getType() == Material.DARK_OAK_DOOR || clicked.getType() == Material.JUNGLE_DOOR || clicked.getType() == Material.SPRUCE_DOOR) {
                Location loc = clicked.getLocation();
                Player player = e.getPlayer();
                if (CustomLocation.isPlayerRegion(loc, player, "DOOR")) {
                    e.setCancelled(true);
                    player.sendMessage(PLPrefix + ChatColor.RED + "Opening and closing of the door is prohibited because it is installed on private land");
                }
            }
        }
    }

}
