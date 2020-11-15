package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.Event;


import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.CustomLocation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;

public class onBreakPaint implements Listener {

    @EventHandler
    public void onBreakFrame(HangingBreakEvent e) {
        Location loc = e.getEntity().getLocation();
        Entity ent = e.getEntity();
        if (ent.getType() == EntityType.PAINTING) {
            if (CustomLocation.isNoPlayerRegion(loc, "PAINT")) {
                e.setCancelled(true);
            }
        }
    }

}
