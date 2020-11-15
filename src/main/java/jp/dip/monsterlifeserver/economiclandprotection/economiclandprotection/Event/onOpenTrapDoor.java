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

public class onOpenTrapDoor implements Listener {

    @EventHandler
    public void onOpenTrapDoor(PlayerInteractEvent e) {
        Action action = e.getAction();
        Block clicked = e.getClickedBlock();
        //Left or Right click?
        if (action == Action.RIGHT_CLICK_BLOCK) {
            //Door Block?
            if (clicked.getType() == Material.ACACIA_TRAPDOOR || clicked.getType() == Material.BIRCH_TRAPDOOR || clicked.getType() == Material.DARK_OAK_TRAPDOOR || clicked.getType() == Material.JUNGLE_TRAPDOOR || clicked.getType() == Material.OAK_TRAPDOOR || clicked.getType() == Material.SPRUCE_TRAPDOOR) {
                Location loc = clicked.getLocation();
                Player player = e.getPlayer();
                if (CustomLocation.isPlayerRegion(loc, player, "TRAP_DOOR")) {
                    e.setCancelled(true);
                    player.sendMessage(PLPrefix + ChatColor.RED + "Opening and closing the trap door is prohibited because it is located on private land");
                }
            }
        }
    }

}
