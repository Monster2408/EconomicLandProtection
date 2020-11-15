package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.Event;


import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.CustomLocation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;

public class onBreakFrame implements Listener {

    @EventHandler
    public void onBreakFrame(HangingBreakEvent e) {
        Location loc = e.getEntity().getLocation();
        Entity ent = e.getEntity();
        if (ent.getType() == EntityType.ITEM_FRAME) {
            if (CustomLocation.isNoPlayerRegion(loc, "FRAME")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreakFrameItem(EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        Entity attacker = e.getDamager();
        Location loc = victim.getLocation();
        if (victim.getType() == EntityType.ITEM_FRAME) {
            if (attacker != null) {
                if (attacker instanceof Player) {
                    Player player = (Player) attacker;
                    if (CustomLocation.isPlayerRegion(loc, player, "FRAME")) {
                        e.setCancelled(true);
                        return;
                    }
                    return;
                }
            }
            if (CustomLocation.isNoPlayerRegion(loc, "FRAME")) {
                e.setCancelled(true);
            }
        }

    }

}
