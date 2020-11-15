package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API;

import com.google.common.collect.Table;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.EconomicLandProtection.*;

public class CustomLocation {


    private static Vector maxVector = null;
    private static Vector minVector = null;

    public CustomLocation() {
    }

    static public void setVector(Location A, Location B) {
        double xA = A.getBlockX();
        double yA = A.getBlockY();
        double zA = A.getBlockZ();

        double xB = B.getBlockX();
        double yB = B.getBlockY();
        double zB = B.getBlockZ();

        maxVector = new Vector(Math.min(xA, xB), Math.min(yA, yB), Math.min(zA, zB));
        minVector = new Vector(Math.min(xA, xB), Math.min(yA, yB), Math.min(zA, zB));
    }

    public Vector getMaxVector() {
        return maxVector;
    }

    public Vector getMinVector() {
        return minVector;
    }

    public void saveCustomLocation() {
        String UUID;
        String Name;
        Location locationA;
        Location locationB;
        ArrayList<String> list;
        ArrayList<String> members;
        for (Table.Cell<String, String, Location> _PosA : PosA.cellSet()) {
            UUID = _PosA.getRowKey();
            Name = _PosA.getColumnKey();
            locationA = _PosA.getValue();
            locationB = PosB.get(UUID, Name);
            locate.getConfig().set("protection." + UUID + "." + Name + ".A.W", locationA.getWorld().getName());
            locate.getConfig().set("protection." + UUID + "." + Name + ".A.X", locationA.getBlockX());
            locate.getConfig().set("protection." + UUID + "." + Name + ".A.Z", locationA.getBlockZ());

            locate.getConfig().set("protection." + UUID + "." + Name + ".B.W", locationB.getWorld().getName());
            locate.getConfig().set("protection." + UUID + "." + Name + ".B.X", locationB.getBlockX());
            locate.getConfig().set("protection." + UUID + "." + Name + ".B.Z", locationB.getBlockZ());

            list = (ArrayList<String>) Flag.get(UUID, Name);
            locate.getConfig().set("protection." + UUID + "." + Name + ".Flag", list);

            members = (ArrayList<String>) Member.get(UUID, Name);
            locate.getConfig().set("protection." + UUID + "." + Name + ".Member", members);
        }
    }

    public void loadCustomLocation() {
        World wldA;
        double XA;
        double ZA;
        Location locA;

        World wldB;
        double XB;
        double ZB;
        Location locB;

        ArrayList<String> flags;
        ArrayList<String> members;
        if (locate == null) {
            locate = new CustomConfig(plugin, "locate.yml");
            locate.saveConfig();
        }
        if (locate.getConfig().isSet("protection")) {
            for (String UUID : locate.getConfig().getConfigurationSection("protection").getKeys(false)) {
                for (String name : locate.getConfig().getConfigurationSection("protection." + UUID).getKeys(false)) {
                    wldA = Bukkit.getWorld(locate.getConfig().getString("protection." + UUID + "." + name + ".A.W"));
                    XA = locate.getConfig().getDouble("protection." + UUID + "." + name + ".A.X");
                    ZA = locate.getConfig().getDouble("protection." + UUID + "." + name + ".A.Z");
                    locA = new Location(wldA, XA, 0.0, ZA);
                    PosA.put(UUID, name, locA);

                    wldB = Bukkit.getWorld(locate.getConfig().getString("protection." + UUID + "." + name + ".B.W"));
                    XB = locate.getConfig().getDouble("protection." + UUID + "." + name + ".B.X");
                    ZB = locate.getConfig().getDouble("protection." + UUID + "." + name + ".B.Z");
                    locB = new Location(wldB, XB, 0.0, ZB);
                    PosB.put(UUID, name, locB);
                    flags = (ArrayList<String>) locate.getConfig().getStringList("protection." + UUID + "." + name + ".Flag");
                    members = (ArrayList<String>) locate.getConfig().getStringList("protection." + UUID + "." + name + ".Member");

                    Flag.put(UUID, name, flags);
                    Member.put(UUID, name, members);
                }
            }
        }
    }

    public static Table<String, String, Location> getFirstRegions() {
        return PosA;
    }

    public static Table<String, String, Location> getSecondRegions() {
        return PosB;
    }

    public static List<String> getRegionMember(String OwnerUUID, String AreaName) {
        List<String> members;
        if (Member.get(OwnerUUID, AreaName) != null) {
            members = Member.get(OwnerUUID, AreaName);
            return members;
        }
        return null;
    }

    public void addRegionMember(String OwnerUUID, String AreaName, String addPlayerUUID) {
        List<String> members = new ArrayList<>();
        if (Member.get(OwnerUUID, AreaName) != null) {
            members = Member.get(OwnerUUID, AreaName);
        }
        members.add(addPlayerUUID);
    }

    public void removeRegionMember(String OwnerUUID, String AreaName, String addPlayerUUID) {
        List<String> members = new ArrayList<>();
        if (Member.get(OwnerUUID, AreaName) != null) {
            members = Member.get(OwnerUUID, AreaName);
        }
        members.remove(addPlayerUUID);
    }

    public void deleteRegionMember(String OwnerUUID, String AreaName) {
        List<String> members = new ArrayList<>();
        if (Member.get(OwnerUUID, AreaName) != null) {
            Member.put(OwnerUUID, AreaName, members);
        }
    }

    public static List<String> getOnlinePlayersName() {
        List<String> playersName = new ArrayList<>();
        for (Player player: Bukkit.getOnlinePlayers()) {
            playersName.add(player.getName());
        }
        return playersName;
    }

    /**
     * @param loc ブロックの座標
     * @param option オプション(BREAK, PLACE, CHECK)
     * @return true -> エリア内のブロック | false -> エリア外のブロック
     */
    public static boolean isPlayerRegion(Location loc, Player player, String option) {
        if (PosA.size() == 0) {
            return false;
        }
        String UUID;
        String Name;
        Location locationA;
        Location locationB;
        double xa;
        double za;
        double xb;
        double zb;
        for (Table.Cell<String, String, Location> _PosA : PosA.cellSet()) {
            UUID = _PosA.getRowKey();
            Name = _PosA.getColumnKey();
            locationA = _PosA.getValue();
            if (locationA.getWorld() == player.getWorld()) {
                if (Flag.get(UUID, Name).contains(option)) {
                    if (!player.getUniqueId().toString().equals(UUID)) {
                        locationB = PosB.get(UUID, Name);
                        xa = Math.max(locationA.getBlockX(), locationB.getBlockX());
                        xb = Math.min(locationA.getBlockX(), locationB.getBlockX());
                        za = Math.max(locationA.getBlockZ(), locationB.getBlockZ());
                        zb = Math.min(locationA.getBlockZ(), locationB.getBlockZ());
                        if (UUID.equals("admin")) {
                            if (player.isOp()) {
                                return false;
                            }
                            if (!player.isOp()) {
                                if (xa >= loc.getBlockX() && loc.getBlockX() >= xb && za >= loc.getBlockZ() && loc.getBlockZ() >= zb) {
                                    if (CustomLocation.getRegionMember(UUID, Name) != null) {
                                        if (!CustomLocation.getRegionMember(UUID, Name).contains(player.getUniqueId().toString())) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                        if (xa >= loc.getBlockX() && loc.getBlockX() >= xb && za >= loc.getBlockZ() && loc.getBlockZ() >= zb) {
                            if (CustomLocation.getRegionMember(UUID, Name) != null) {
                                if (!CustomLocation.getRegionMember(UUID, Name).contains(player.getUniqueId().toString())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param loc ブロックの座標
     * @param option オプション(EXPLODE, CHECK)
     * @return true -> エリア内のブロック | false -> エリア外のブロック
     */
    public static boolean isNoPlayerRegion(Location loc, String option) {
        if (PosA.size() == 0) {
            return false;
        }
        String UUID;
        String Name;
        Location locationA;
        Location locationB;
        double xa;
        double za;
        double xb;
        double zb;
        for (Table.Cell<String, String, Location> _PosA : PosA.cellSet()) {
            UUID = _PosA.getRowKey();
            Name = _PosA.getColumnKey();
            locationA = _PosA.getValue();
            if (Flag.get(UUID, Name).contains(option)) {
                locationB = PosB.get(UUID, Name);
                xa = Math.max(locationA.getBlockX(), locationB.getBlockX());
                xb = Math.min(locationA.getBlockX(), locationB.getBlockX());
                za = Math.max(locationA.getBlockZ(), locationB.getBlockZ());
                zb = Math.min(locationA.getBlockZ(), locationB.getBlockZ());
                if (xa >= loc.getBlockX() && loc.getBlockX() >= xb && za >= loc.getBlockZ() && loc.getBlockZ() >= zb) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param loc ブロックの座標
     * @return true -> エリア内のブロック | false -> エリア外のブロック
     */
    public static boolean isCheckRegion(Location loc) {
        if (PosA.size() == 0) {
            return false;
        }
        String UUID;
        String Name;
        Location locationA;
        Location locationB;
        double xa;
        double za;
        double xb;
        double zb;
        for (Table.Cell<String, String, Location> _PosA : PosA.cellSet()) {
            UUID = _PosA.getRowKey();
            Name = _PosA.getColumnKey();
            locationA = _PosA.getValue();
            locationB = PosB.get(UUID, Name);
            xa = Math.max(locationA.getBlockX(), locationB.getBlockX());
            xb = Math.min(locationA.getBlockX(), locationB.getBlockX());
            za = Math.max(locationA.getBlockZ(), locationB.getBlockZ());
            zb = Math.min(locationA.getBlockZ(), locationB.getBlockZ());
            if (xa >= loc.getBlockX() && loc.getBlockX() >= xb && za >= loc.getBlockZ() && loc.getBlockZ() >= zb) {
                return true;
            }
        }
        return false;
    }

}
