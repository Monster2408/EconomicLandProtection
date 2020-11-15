package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API;

import com.google.common.collect.HashBasedTable;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.HashMap;

import static jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.EconomicLandProtection.*;
import static org.bukkit.Bukkit.getServer;

public class MainSystem {

    public static void loadPlugin(PluginDescriptionFile pluginDescriptionFile) {
        _PosA = new HashMap<>();
        _PosB = new HashMap<>();

        PosA = HashBasedTable.create();
        PosB = HashBasedTable.create();
        Flag = HashBasedTable.create();
        Member = HashBasedTable.create();

        player_cost = new HashMap<>();

        block_buy_cost = config.getConfig().getDouble("BuyCost");
        block_sell_cost = config.getConfig().getDouble("SellCost");

        PLPrefix = config.getConfig().getString("Prefix");
        PLPrefix = Color.replaceColorCode(PLPrefix);

        log.info(PLPrefix + ChatColor.AQUA + "プラグイン起動完了");

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", pluginDescriptionFile.getName()));
            getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    private static boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }


    public static Economy getEconomy() {
        return econ;
    }

}
