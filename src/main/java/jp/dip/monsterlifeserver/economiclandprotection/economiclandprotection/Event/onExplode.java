package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.Event;

import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.CustomLocation;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class onExplode implements Listener {

    @EventHandler
    public void onExplode(BlockExplodeEvent e) {
        for (Block block : e.blockList()) {
            if (CustomLocation.isNoPlayerRegion(block.getLocation(), "EXPLODE")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        for (Block block : e.blockList()) {
            if (CustomLocation.isNoPlayerRegion(block.getLocation(), "EXPLODE")) {
                e.setCancelled(true);
            }
        }
    }

}
