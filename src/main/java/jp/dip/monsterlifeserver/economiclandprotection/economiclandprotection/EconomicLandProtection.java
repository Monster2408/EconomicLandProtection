package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection;

import com.google.common.collect.Table;
import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.Color;
import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.CustomConfig;
import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.CustomLocation;
import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.MainSystem;
import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.Event.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.MainHand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public final class EconomicLandProtection extends JavaPlugin implements Listener {

    public static final Logger log = Logger.getLogger("Minecraft");

    public static Plugin plugin;
    public static CustomConfig config;
    public static CustomConfig locate;

    public static Economy econ = null;

    public static HashMap<String, Location> _PosA;
    public static HashMap<String, Location> _PosB;

    public static double block_buy_cost;
    public static double block_sell_cost;
    public static HashMap<String, Double> player_cost;

    public static String PLPrefix;

    public static Table<String, String, Location> PosA;
    public static Table<String, String, Location> PosB;
    public static Table<String, String, List<String>> Flag;
    public static Table<String, String, List<String>> Member;

    private static CustomLocation CustomLocation;

    private static ArrayList<Player> rightClick;

    @Override
    public void onEnable() {
        plugin = this;
        config = new CustomConfig(this);
        locate = new CustomConfig(this, "data.yml");

        config.saveDefaultConfig();
        locate.saveDefaultConfig();

        getCommand("land").setExecutor(new CustomCommand());

        getServer().getPluginManager().registerEvents(this, this);

        getServer().getPluginManager().registerEvents(new onBreakBlock(), this);
        getServer().getPluginManager().registerEvents(new onBreakFrame(), this);
        getServer().getPluginManager().registerEvents(new onBreakPaint(), this);
        getServer().getPluginManager().registerEvents(new onExplode(), this);
        getServer().getPluginManager().registerEvents(new onMonsters(), this);
        getServer().getPluginManager().registerEvents(new onOpenDoor(), this);
        getServer().getPluginManager().registerEvents(new onOpenTrapDoor(), this);
        getServer().getPluginManager().registerEvents(new onPiston(), this);
        getServer().getPluginManager().registerEvents(new onPlace(), this);

        MainSystem.loadPlugin(getDescription());

        CustomLocation = new CustomLocation();
        CustomLocation.loadCustomLocation();

        rightClick = new ArrayList<>();
    }

    @Override
    public void onDisable() {
        locate.getConfig().set("protection", null);
        CustomLocation.saveCustomLocation();
        locate.saveConfig();
    }

    @EventHandler
    public void onCreateRegion(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand().getType() == Material.STICK) {
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getPlayer().getMainHand() == MainHand.RIGHT) {
                    if (!rightClick.contains(player)) {
                        rightClick.add(player);
                        e.setCancelled(true);
                        Block block = e.getClickedBlock();
                        Location l = block.getLocation();
                        _PosA.put(player.getUniqueId().toString(), l);
                        double x = l.getX();
                        double z = l.getZ();
                        String w = l.getWorld().getName();
                        String _x = String.format("&b&lX &a: &b&l%.2f ", x);
                        String _z = String.format("&b&lZ &a: &b&l%.2f", z);
                        if (_PosB.get(player.getUniqueId().toString()) != null && _PosA.get(player.getUniqueId().toString()).getWorld().getName().equals(_PosB.get(player.getUniqueId().toString()).getWorld().getName())) {
                            double x_x = Math.abs(_PosA.get(player.getUniqueId().toString()).getX() - _PosB.get(player.getUniqueId().toString()).getX()) + 1;
                            double z_z = Math.abs(_PosA.get(player.getUniqueId().toString()).getZ() - _PosB.get(player.getUniqueId().toString()).getZ()) + 1;
                            double all = x_x * z_z * block_buy_cost;
                            _z = _z + "&c[The cost of land is $ " + all + " .]";
                            player_cost.put(player.getUniqueId().toString(), all);
                        }
                        player.sendMessage(Color.replaceColorCode(_x + _z));
                        return;
                    } else {
                        rightClick.remove(player);
                    }
                }
            }
            if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                e.setCancelled(true);
                Block block = e.getClickedBlock();
                Location l = block.getLocation();
                _PosB.put(player.getUniqueId().toString(), l);
                double x = l.getX();
                double z = l.getZ();
                String w = l.getWorld().getName();
                String _x = String.format("&b&lX &a: &b&l%.2f, ", x);
                String _z = String.format("&b&lZ &a: &b&l%.2f", z);
                if (_PosA.get(player.getUniqueId().toString()) != null && _PosA.get(player.getUniqueId().toString()).getWorld().getName().equals(_PosB.get(player.getUniqueId().toString()).getWorld().getName())) {
                    double x_x = Math.abs(_PosA.get(player.getUniqueId().toString()).getX() - _PosB.get(player.getUniqueId().toString()).getX()) + 1;
                    double z_z = Math.abs(_PosA.get(player.getUniqueId().toString()).getZ() - _PosB.get(player.getUniqueId().toString()).getZ()) + 1;
                    double all = x_x * z_z * block_buy_cost;
                    _z = _z + "&c[The cost of land is $ " + all + " .]";
                }
                player.sendMessage(Color.replaceColorCode(_x + _z));
            }
        }
    }

}
