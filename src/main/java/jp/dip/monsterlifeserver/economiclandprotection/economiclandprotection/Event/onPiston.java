package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.Event;

import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.CustomLocation;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class onPiston implements Listener {

    @EventHandler
    public void onPiston(BlockPistonExtendEvent e) {
        for (Block block: e.getBlocks()) {
            if (CustomLocation.isNoPlayerRegion(block.getLocation(), "PISTON")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPiston(BlockPistonRetractEvent e) {
        for (Block block: e.getBlocks()) {
            if (CustomLocation.isNoPlayerRegion(block.getLocation(), "PISTON")) {
                e.setCancelled(true);
            }
        }
    }

}
