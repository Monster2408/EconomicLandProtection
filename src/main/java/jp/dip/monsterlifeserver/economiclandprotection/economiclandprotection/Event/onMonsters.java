package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.Event;

import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.CustomLocation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class onMonsters implements Listener {

    @EventHandler
    public void onMonstersAttack(EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        Entity attacker = e.getDamager();
        Location loc = victim.getLocation();
        if (victim.getType() == EntityType.PLAYER) {
            if (attacker.getType() == EntityType.BLAZE || attacker.getType() == EntityType.CAVE_SPIDER || attacker.getType() == EntityType.CREEPER || attacker.getType() == EntityType.DRAGON_FIREBALL || attacker.getType() == EntityType.ENDERMAN || attacker.getType() == EntityType.ENDERMITE || attacker.getType() == EntityType.EVOKER || attacker.getType() == EntityType.FIREBALL || attacker.getType() == EntityType.GHAST || attacker.getType() == EntityType.GIANT || attacker.getType() == EntityType.GUARDIAN || attacker.getType() == EntityType.HUSK || attacker.getType() == EntityType.ILLUSIONER || attacker.getType() == EntityType.MAGMA_CUBE || attacker.getType() == EntityType.SHULKER || attacker.getType() == EntityType.SHULKER_BULLET || attacker.getType() == EntityType.SKELETON || attacker.getType() == EntityType.SLIME || attacker.getType() == EntityType.SPIDER || attacker.getType() == EntityType.STRAY || attacker.getType() == EntityType.VEX || attacker.getType() == EntityType.VINDICATOR || attacker.getType() == EntityType.WITCH || attacker.getType() == EntityType.WITHER || attacker.getType() == EntityType.WITHER_SKELETON || attacker.getType() == EntityType.ZOMBIE || attacker.getType() == EntityType.ZOMBIE_VILLAGER) {
                if (CustomLocation.isNoPlayerRegion(loc, "MONSTER")) {
                    e.setCancelled(true);
                    attacker.remove();
                    return;
                }
                return;
            }
        }
    }

}
