package jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection;

import com.google.common.collect.Table;
import jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.API.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static jp.dip.monsterlifeserver.economiclandprotection.economiclandprotection.EconomicLandProtection.*;

public class CustomCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            log.info(ChatColor.RED + "Command cannot be sent except by player");
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("land")) {
            /**
             * /land help - 保護コマンドヘルプ
             * /land list - 私有地リスト
             */
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(ChatColor.GOLD + "========================" + ChatColor.RED + " ELP " + ChatColor.GOLD + "========================");
                    sender.sendMessage(ChatColor.GREEN + "/land help" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Displays help for protected commands.");
                    sender.sendMessage(ChatColor.GREEN + "/land list" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Display the list of lands owned by you.");
                    sender.sendMessage(ChatColor.GREEN + "/land flag list" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Display the land setting list.");
                    sender.sendMessage(ChatColor.GREEN + "/land create <AreaName>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Buy the selected range.");
                    sender.sendMessage(ChatColor.GREEN + "/land remove <AreaName>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Sell your land.");
                    sender.sendMessage(ChatColor.GREEN + "/land flag <Options> <AreaName>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set the specified land.");
                    sender.sendMessage(ChatColor.GREEN + "/land flag list <AreaName>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Displays the setting items of the specified land.");

                    sender.sendMessage(ChatColor.GREEN + "/land member add <Player> <AreaName>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Add players to private land members.");
                    sender.sendMessage(ChatColor.GREEN + "/land member remove <Player> <AreaName>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Remove a player from a private land member.");
                    sender.sendMessage(ChatColor.GREEN + "/land member delete <AreaName>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Remove a private land member.");
                    sender.sendMessage(ChatColor.GREEN + "/land member list <AreaName>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Displays a list of private land members.");
                    if (player.isOp()) {
                        sender.sendMessage(ChatColor.GREEN + "/land list admin" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Display the public land list.");
                        sender.sendMessage(ChatColor.GREEN + "/land flag list <Area> admin" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Displays public land settings.");
                        sender.sendMessage(ChatColor.GREEN + "/land flag <Options> <Area> admin" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set up public land.");
                        sender.sendMessage(ChatColor.GREEN + "/land create <Area> admin" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set up public land.");
                        sender.sendMessage(ChatColor.GREEN + "/land remove <Area> admin" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Open public land.");

                        sender.sendMessage(ChatColor.GREEN + "/land member add <Player> <AreaName> admin" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Add players to public land members.");
                        sender.sendMessage(ChatColor.GREEN + "/land member remove <Player> <AreaName> admin" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Remove a player from a public land member.");
                        sender.sendMessage(ChatColor.GREEN + "/land member delete <AreaName> admin" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Remove public land members.");
                        sender.sendMessage(ChatColor.GREEN + "/land member list <AreaName> admin" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Display the list of public land members.");
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("list")) {
                    int i = 0;
                    sender.sendMessage(ChatColor.GOLD + "========================" + ChatColor.RED + " Purchased land list " + ChatColor.GOLD + "========================");
                    for (Table.Cell<String, String, Location> _PosA : PosA.cellSet()) {
                        if (_PosA.getRowKey().equals(player.getUniqueId().toString())) {
                            i++;
                            sender.sendMessage(ChatColor.GOLD + String.valueOf(i) + ". " + _PosA.getColumnKey());
                        }
                    }
                    return true;
                }
                sender.sendMessage(PLPrefix + ChatColor.RED + "There is no such command.");
                return true;
            }
            /**
             * /land list admin
             * /land flag list
             * /land create <Area>
             * /land remove <Area>
             */
            if (args.length == 2) {
                /**
                 * /land list admin
                 */
                if (args[0].equalsIgnoreCase("list") && args[1].equalsIgnoreCase("admin")) {
                    if (sender.isOp()) {
                        String Area;
                        sender.sendMessage(ChatColor.GOLD + "========================" + ChatColor.RED + " Public land list " + ChatColor.GOLD + "========================");
                        for (Table.Cell<String, String, Location> Test : PosA.cellSet()) {
                            if (Test.getRowKey().equals("admin")) {
                                Area = Test.getColumnKey();
                                sender.sendMessage(ChatColor.GREEN + Area);
                            }
                        }
                        return true;
                    }
                    sender.sendMessage(PLPrefix + ChatColor.RED + "You do not have permission to execute the command.");
                    return true;
                }
                /**
                 * /land flag list
                 */
                if (args[0].equalsIgnoreCase("flag") && args[1].equalsIgnoreCase("list")) {
                    sender.sendMessage(ChatColor.GOLD + "========================" + ChatColor.RED + " Land setting items " + ChatColor.GOLD + "========================");

                    sender.sendMessage(ChatColor.GOLD + "BREAK" + ChatColor.GREEN + " - " + ChatColor.GOLD + "Destruction of blocks");
                    sender.sendMessage(ChatColor.GOLD + "PLACE" + ChatColor.GREEN + " - " + ChatColor.GOLD + "Installation of blocks");
                    sender.sendMessage(ChatColor.GOLD + "EXPLODE" + ChatColor.GREEN + " - " + ChatColor.GOLD + "Land collapse due to explosion");
                    sender.sendMessage(ChatColor.GOLD + "DOOR" + ChatColor.GREEN + " - " + ChatColor.GOLD + "Opening and closing the door");
                    sender.sendMessage(ChatColor.GOLD + "TRAP_DOOR" + ChatColor.GREEN + " - " + ChatColor.GOLD + "Opening and closing the trap door");
                    sender.sendMessage(ChatColor.GOLD + "FRAME" + ChatColor.GREEN + " - " + ChatColor.GOLD + "Picture frame destruction");
                    sender.sendMessage(ChatColor.GOLD + "PAINT" + ChatColor.GREEN + " - " + ChatColor.GOLD + "Destruction of paintings");
                    sender.sendMessage(ChatColor.GOLD + "MONSTER" + ChatColor.GREEN + " - " + ChatColor.GOLD + "Existence of enemy MOB");
                    sender.sendMessage(ChatColor.GOLD + "PISTON" + ChatColor.GREEN + " - " + ChatColor.GOLD + "Use of piston");
                    return true;
                }
                /**
                 * /land create <Area>
                 */
                if (args[0].equalsIgnoreCase("create")) {
                    if (player_cost.get(player.getUniqueId().toString()) <= econ.getBalance(player.getName())) {
                        if (PosA.get(player.getUniqueId().toString(), args[1]) == null) {

                            Location A = _PosA.get(player.getUniqueId().toString());
                            Location B = _PosB.get(player.getUniqueId().toString());

                            double xa = Math.max(A.getBlockX(), B.getBlockX());
                            double xb = Math.min(A.getBlockX(), B.getBlockX());
                            double za = Math.max(A.getBlockZ(), B.getBlockZ());
                            double zb = Math.min(A.getBlockZ(), B.getBlockZ());

                            Location location;

                            for (double x = xb; x <= xa; x++) {
                                for (double z = zb; z <= za; z++) {
                                    location = new Location(A.getWorld(), x, 0, z);
                                    if (CustomLocation.isCheckRegion(location)) {
                                        sender.sendMessage(PLPrefix + ChatColor.RED + "It could not be purchased because it was covered with other private land or public areas.");
                                        return true;
                                    }
                                }
                            }

                            ArrayList<String> list = new ArrayList<>();
                            list.add("PLACE");
                            list.add("BREAK");
                            list.add("EXPLODE");

                            Flag.put(player.getUniqueId().toString(), args[1], list);

                            sender.sendMessage(PLPrefix + ChatColor.AQUA + "Purchased land");
                            econ.withdrawPlayer(player.getName(), player_cost.get(player.getUniqueId().toString()));

                            PosA.put(player.getUniqueId().toString(), args[1], A);
                            PosB.put(player.getUniqueId().toString(), args[1], B);
                            return true;
                        }
                        sender.sendMessage(PLPrefix + ChatColor.RED + "A land with that name has already been created.");
                        return true;
                    }
                    sender.sendMessage(PLPrefix + ChatColor.RED + "Could not buy land");
                    return true;
                }
                /**
                 * /land remove <Area>
                 */
                if (args[0].equalsIgnoreCase("remove")) {
                    if (PosA.get(player.getUniqueId().toString(), args[1]) != null) {
                        double A_X = PosA.get(player.getUniqueId().toString(), args[1]).getX();
                        double A_Z = PosA.get(player.getUniqueId().toString(), args[1]).getZ();

                        double B_X = PosB.get(player.getUniqueId().toString(), args[1]).getX();
                        double B_Z = PosB.get(player.getUniqueId().toString(), args[1]).getZ();

                        double x_x = Math.abs(A_X - B_X) + 1;
                        double z_z = Math.abs(A_Z - B_Z) + 1;
                        double all = x_x * z_z * block_sell_cost;

                        PosA.remove(player.getUniqueId().toString(), args[1]);
                        PosB.remove(player.getUniqueId().toString(), args[1]);
                        econ.depositPlayer(player.getName(), all);
                        sender.sendMessage(PLPrefix + ChatColor.AQUA + "Sold land for " + econ.format(all));
                        return true;
                    }
                    sender.sendMessage(PLPrefix + ChatColor.RED + "You don't have a land named " + args[1]);
                    return true;
                }

            }
            /**
             * /land create <Area> admin
             * /land remove <Area> admin
             * /land flag <Options> <Area>
             * /land flag list <Area>
             * /land member list <Area>
             * /land member delete <Area>
             */
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("member")) {
                    /**
                     * /land member list <Area>
                     * /land member delete <Area>
                     */
                    if (PosA.get(player.getUniqueId().toString(), args[2]) != null) {
                        if (args[1].equalsIgnoreCase("list")) {
                            int i = 0;
                            sender.sendMessage(ChatColor.GOLD + "========================" + ChatColor.RED + " Land Member List " + ChatColor.GOLD + "========================");
                            for (String uuid : Member.get(player.getUniqueId().toString(), args[2])) {
                                i++;
                                sender.sendMessage(ChatColor.GREEN + String.valueOf(i) + ". " + Bukkit.getPlayer(UUID.fromString(uuid)).getName());
                            }
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("delete")) {
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Land members have been deleted.");
                            new CustomLocation().deleteRegionMember(player.getUniqueId().toString(), args[2]);
                        }
                        sender.sendMessage(PLPrefix + ChatColor.RED + "There is no such command. Check with /land help.");
                        return true;
                    }
                    sender.sendMessage(PLPrefix + ChatColor.RED + "There is no land with such a name.");
                    return true;
                }
                /**
                 * /land create <Area> admin
                 */
                if (args[0].equalsIgnoreCase("create")) {
                    if (args[2].equalsIgnoreCase("admin")) {
                        if (sender.isOp()) {
                            if (PosA.get("admin", args[1]) == null) {

                                Location A = _PosA.get(player.getUniqueId().toString());
                                Location B = _PosB.get(player.getUniqueId().toString());

                                double xa = Math.max(A.getBlockX(), B.getBlockX());
                                double xb = Math.min(A.getBlockX(), B.getBlockX());
                                double za = Math.max(A.getBlockZ(), B.getBlockZ());
                                double zb = Math.min(A.getBlockZ(), B.getBlockZ());

                                Location location;

                                for (double x = xb; x <= xa; x++) {
                                    for (double z = zb; z <= za; z++) {
                                        location = new Location(A.getWorld(), x, 0, z);
                                        if (CustomLocation.isCheckRegion(location)) {
                                            sender.sendMessage(PLPrefix + ChatColor.RED + "It could not be purchased because it was covered with other private land or public areas.");
                                            return true;
                                        }
                                    }
                                }

                                ArrayList<String> list = new ArrayList<>();
                                list.add("PLACE");
                                list.add("BREAK");
                                list.add("EXPLODE");

                                sender.sendMessage(PLPrefix + ChatColor.AQUA + "Land protected");

                                PosA.put("admin", args[1], A);
                                PosB.put("admin", args[1], B);
                                Flag.put("admin", args[1], list);
                                return true;
                            }
                            sender.sendMessage(PLPrefix + ChatColor.RED + "A land with that name has already been created.");
                            return true;
                        }
                        sender.sendMessage(PLPrefix + ChatColor.RED + "You do not have permission to execute the command.");
                        return true;
                    }
                    sender.sendMessage(PLPrefix + ChatColor.RED + "Could not create because " + args[2] + " authority does not exist.");
                    return true;
                }
                /**
                 * /land remove <Area> admin
                 */
                if (args[0].equalsIgnoreCase("remove")) {
                    if (args[2].equalsIgnoreCase("admin")) {
                        if (sender.isOp()) {
                            if (PosA.get("admin", args[1]) != null) {
                                PosA.remove("admin", args[1]);
                                PosB.remove("admin", args[1]);
                                sender.sendMessage(PLPrefix + ChatColor.AQUA + "Land has been opened.");
                                return true;
                            }
                            sender.sendMessage(PLPrefix + ChatColor.RED + "Could not open because there is no land named " + args[1]);
                            return true;
                        }
                        sender.sendMessage(PLPrefix + ChatColor.RED + "You do not have permission to execute the command.");
                        return true;
                    }
                    sender.sendMessage(PLPrefix + ChatColor.RED + "Could not release because authority " + args[2] + " does not exist.");
                    return true;
                }
                /**
                 * /land flag <Options> <Area>
                 */
                if (args[0].equalsIgnoreCase("flag")) {
                    if (args[1].equalsIgnoreCase("piston")) {
                        if (Flag.get(player.getUniqueId().toString(), args[2]).contains("PISTON")) {
                            Flag.get(player.getUniqueId().toString(), args[2]).remove("PISTON");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Allowed use of piston");
                            return true;
                        }
                        Flag.get(player.getUniqueId().toString(), args[2]).add("PISTON");
                        sender.sendMessage(PLPrefix + ChatColor.GREEN + "Banned the use of pistons");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("break")) {
                        if (Flag.get(player.getUniqueId().toString(), args[2]).contains("BREAK")) {
                            Flag.get(player.getUniqueId().toString(), args[2]).remove("BREAK");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Block destruction allowed");
                            return true;
                        }
                        Flag.get(player.getUniqueId().toString(), args[2]).add("BREAK");
                        sender.sendMessage(PLPrefix + ChatColor.GREEN + "Block destruction prohibited");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("place")) {
                        if (Flag.get(player.getUniqueId().toString(), args[2]).contains("PLACE")) {
                            Flag.get(player.getUniqueId().toString(), args[2]).remove("PLACE");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Block allowed");
                            return true;
                        }
                        Flag.get(player.getUniqueId().toString(), args[2]).add("PLACE");
                        sender.sendMessage(PLPrefix + ChatColor.GREEN + "Block installation prohibited");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("explode")) {
                        if (Flag.get(player.getUniqueId().toString(), args[2]).contains("EXPLODE")) {
                            Flag.get(player.getUniqueId().toString(), args[2]).remove("EXPLODE");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Permitted land collapse due to explosion");
                            return true;
                        }
                        Flag.get(player.getUniqueId().toString(), args[2]).add("EXPLODE");
                        sender.sendMessage(PLPrefix + ChatColor.GREEN + "Banned land collapse due to explosion");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("door")) {
                        if (Flag.get(player.getUniqueId().toString(), args[2]).contains("DOOR")) {
                            Flag.get(player.getUniqueId().toString(), args[2]).remove("DOOR");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Door open / close allowed");
                            return true;
                        }
                        Flag.get(player.getUniqueId().toString(), args[2]).add("DOOR");
                        sender.sendMessage(PLPrefix + ChatColor.GREEN + "Door opening and closing is prohibited");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("TRAP_DOOR")) {
                        if (Flag.get(player.getUniqueId().toString(), args[2]).contains("TRAP_DOOR")) {
                            Flag.get(player.getUniqueId().toString(), args[2]).remove("TRAP_DOOR");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Allowed to open and close trap door");
                            return true;
                        }
                        Flag.get(player.getUniqueId().toString(), args[2]).add("TRAP_DOOR");
                        sender.sendMessage(PLPrefix + ChatColor.GREEN + "Prohibition of opening and closing the trap door");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("FRAME")) {
                        if (Flag.get(player.getUniqueId().toString(), args[2]).contains("FRAME")) {
                            Flag.get(player.getUniqueId().toString(), args[2]).remove("FRAME");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Allowed destruction of picture frame");
                            return true;
                        }
                        Flag.get(player.getUniqueId().toString(), args[2]).add("FRAME");
                        sender.sendMessage(PLPrefix + ChatColor.GREEN + "Banned frame destruction");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("PAINT")) {
                        if (Flag.get(player.getUniqueId().toString(), args[2]).contains("PAINT")) {
                            Flag.get(player.getUniqueId().toString(), args[2]).remove("PAINT");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Permitted to destroy paintings");
                            return true;
                        }
                        Flag.get(player.getUniqueId().toString(), args[2]).add("PAINT");
                        sender.sendMessage(PLPrefix + ChatColor.GREEN + "Banned painting destruction");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("Monster")) {
                        if (Flag.get(player.getUniqueId().toString(), args[2]).contains("MONSTER")) {
                            Flag.get(player.getUniqueId().toString(), args[2]).remove("MONSTER");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Allowed the presence of enemy MOBs");
                            return true;
                        }
                        Flag.get(player.getUniqueId().toString(), args[2]).add("MONSTER");
                        sender.sendMessage(PLPrefix + ChatColor.GREEN + "Banned enemy MOBs");
                        return true;
                    }

                    /**
                     * /land flag list <Area>
                     */
                    if (args[1].equalsIgnoreCase("list")) {
                        if (Flag.get(player.getUniqueId().toString(), args[2]) != null) {
                            sender.sendMessage(ChatColor.GOLD + "========================" + ChatColor.RED + " Land setting list " + ChatColor.GOLD + "========================");
                            for (String option : Flag.get(player.getUniqueId().toString(), args[2])) {
                                sender.sendMessage(ChatColor.GOLD + option);
                            }
                            return true;
                        }
                    }
                    sender.sendMessage(PLPrefix + ChatColor.RED + "No such settings");
                    return true;
                }
            }
            /**
             * /land flag <Options> <Area> admin
             * /land flag list <Area> admin
             * /land member add <Player> <Area>
             * /land member remove <Player> <Area>
             * /land member list <Area> admin
             * /land member delete <Area> admin
             */
            if (args.length == 4) {
                /**
                 * /land member add/remove/list/delete [<Player>] <Area>
                 */
                if (args[0].equalsIgnoreCase("member")) {
                    /**
                     * /land member add <Player> <Area>
                     */
                    if (args[1].equalsIgnoreCase("add")) {
                        if (CustomLocation.getOnlinePlayersName().contains(args[2])) { // プレイヤーがオンライン
                            Player player1 = Bukkit.getPlayer(args[2]);
                            if (Member.get(player.getUniqueId().toString(), args[3]).contains(player1.getUniqueId().toString())) { // もしも選択したプレイヤーがエリアメンバーだった場合
                                sender.sendMessage(PLPrefix + ChatColor.RED + "The player is already a member.");
                                return true;
                            }
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + args[2] + " has been made a member.");
                            new CustomLocation().addRegionMember(player.getUniqueId().toString(), args[3], player1.getUniqueId().toString());
                            return true;
                        }
                        sender.sendMessage(PLPrefix + ChatColor.RED + args[2] + " is not online.");
                        return true;
                    }
                    /**
                     * /land member remove <Player> <Area>
                     */
                    if (args[1].equalsIgnoreCase("remove")) {
                        if (CustomLocation.getOnlinePlayersName().contains(args[2])) { // プレイヤーがオンライン
                            Player player1 = Bukkit.getPlayer(args[2]);
                            if (Member.get(player.getUniqueId().toString(), args[3]).contains(player1.getUniqueId().toString())) { // もしも選択したプレイヤーがエリアメンバーだった場合
                                sender.sendMessage(PLPrefix + ChatColor.GREEN + args[2] + " has been removed from the list.");
                                new CustomLocation().removeRegionMember(player.getUniqueId().toString(), args[3], player1.getUniqueId().toString());
                                return true;
                            }
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + args[2] + " is not registered as a member.");
                            return true;
                        }
                        sender.sendMessage(PLPrefix + ChatColor.RED + args[2] + " is not online.");
                        return true;
                    }
                    /**
                     * /land member list <Area> admin
                     * /land member delete <Area> admin
                     */
                    if (args[3].equalsIgnoreCase("admin")) {
                        if (sender.isOp()) {
                            if (PosA.get("admin", args[2]) != null) {
                                if (args[1].equalsIgnoreCase("list")) {
                                    int i = 0;
                                    sender.sendMessage(ChatColor.GOLD + "========================" + ChatColor.RED + " Land Member List " + ChatColor.GOLD + "========================");
                                    for (String uuid : CustomLocation.getRegionMember("admin", args[2])) {
                                        i++;
                                        sender.sendMessage(ChatColor.GREEN + String.valueOf(i) + ". " + Bukkit.getPlayer(UUID.fromString(uuid)).getName());
                                    }
                                    return true;
                                }
                                if (args[1].equalsIgnoreCase("delete")) {
                                    sender.sendMessage(PLPrefix + ChatColor.GREEN + "Land members have been deleted.");
                                    new CustomLocation().deleteRegionMember("admin", args[2]);
                                }
                                sender.sendMessage(PLPrefix + ChatColor.RED + "There is no such command. Check with /land help.");
                                return true;
                            }
                            sender.sendMessage(PLPrefix + ChatColor.RED + "There is no public land with such a name.");
                            return true;
                        }
                        sender.sendMessage(PLPrefix + ChatColor.RED + "You do not have permission to execute the command.");
                        return true;
                    }
                    sender.sendMessage(PLPrefix + ChatColor.RED + "There is no such command. Check with /land help.");
                    return true;
                }
                /**
                 * /land flag <Options> <Area> admin
                 */
                if (args[0].equalsIgnoreCase("flag") && args[3].equalsIgnoreCase("admin")) {
                    if (sender.isOp()) {
                        if (args[1].equalsIgnoreCase("piston")) {
                            if (Flag.get("admin", args[2]).contains("PISTON")) {
                                Flag.get("admin", args[2]).remove("PISTON");
                                sender.sendMessage(PLPrefix + ChatColor.GREEN + "Allowed use of piston");
                                return true;
                            }
                            Flag.get("admin", args[2]).add("PISTON");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Banned the use of pistons");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("break")) {
                            if (Flag.get("admin", args[2]).contains("BREAK")) {
                                Flag.get("admin", args[2]).remove("BREAK");
                                sender.sendMessage(PLPrefix + ChatColor.GREEN + "Block destruction allowed");
                                return true;
                            }
                            Flag.get("admin", args[2]).add("BREAK");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Block destruction prohibited");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("place")) {
                            if (Flag.get("admin", args[2]).contains("PLACE")) {
                                Flag.get("admin", args[2]).remove("PLACE");
                                sender.sendMessage(PLPrefix + ChatColor.GREEN + "Block allowed");
                                return true;
                            }
                            Flag.get("admin", args[2]).add("PLACE");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Block installation prohibited");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("explode")) {
                            if (Flag.get("admin", args[2]).contains("EXPLODE")) {
                                Flag.get("admin", args[2]).remove("EXPLODE");
                                sender.sendMessage(PLPrefix + ChatColor.GREEN + "Permitted land collapse due to explosion");
                                return true;
                            }
                            Flag.get("admin", args[2]).add("EXPLODE");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Banned land collapse due to explosion");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("door")) {
                            if (Flag.get("admin", args[2]).contains("DOOR")) {
                                Flag.get("admin", args[2]).remove("DOOR");
                                sender.sendMessage(PLPrefix + ChatColor.GREEN + "Door open / close allowed");
                                return true;
                            }
                            Flag.get("admin", args[2]).add("DOOR");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Door opening and closing is prohibited");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("TRAP_DOOR")) {
                            if (Flag.get("admin", args[2]).contains("TRAP_DOOR")) {
                                Flag.get("admin", args[2]).remove("TRAP_DOOR");
                                sender.sendMessage(PLPrefix + ChatColor.GREEN + "Allowed to open and close trap door");
                                return true;
                            }
                            Flag.get("admin", args[2]).add("TRAP_DOOR");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Prohibition of opening and closing the trap door");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("FRAME")) {
                            if (Flag.get("admin", args[2]).contains("FRAME")) {
                                Flag.get("admin", args[2]).remove("FRAME");
                                sender.sendMessage(PLPrefix + ChatColor.GREEN + "Allowed destruction of picture frame");
                                return true;
                            }
                            Flag.get("admin", args[2]).add("FRAME");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Banned frame destruction");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("PAINT")) {
                            if (Flag.get("admin", args[2]).contains("PAINT")) {
                                Flag.get("admin", args[2]).remove("PAINT");
                                sender.sendMessage(PLPrefix + ChatColor.GREEN + "Permitted to destroy paintings");
                                return true;
                            }
                            Flag.get("admin", args[2]).add("PAINT");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Banned painting destruction");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("Monster")) {
                            if (Flag.get("admin", args[2]).contains("MONSTER")) {
                                Flag.get("admin", args[2]).remove("MONSTER");
                                sender.sendMessage(PLPrefix + ChatColor.GREEN + "Allowed the presence of enemy MOBs");
                                return true;
                            }
                            Flag.get("admin", args[2]).add("MONSTER");
                            sender.sendMessage(PLPrefix + ChatColor.GREEN + "Banned enemy MOBs");
                            return true;
                        }

                        /**
                         * /land flag list <Area> admin
                         */
                        if (args[1].equalsIgnoreCase("list")) {
                            if (Flag.get("admin", args[2]) != null) {
                                sender.sendMessage(ChatColor.GOLD + "========================" + ChatColor.RED + " Land setting list " + ChatColor.GOLD + "========================");
                                for (String option : Flag.get("admin", args[2])) {
                                    sender.sendMessage(ChatColor.GOLD + option);
                                }
                                return true;
                            }
                        }
                        sender.sendMessage(PLPrefix + ChatColor.RED + "There is no such setting item.");
                        return true;
                    }
                    sender.sendMessage(PLPrefix + ChatColor.RED + "You do not have permission to execute the command.");
                    return true;
                }
            }
            if (args.length == 5) {
                if (args[0].equalsIgnoreCase("member")) {
                    if (args[4].equalsIgnoreCase("admin")) {
                        if (sender.isOp()) {
                            /**
                             * /land member add <Player> <Area> admin
                             */
                            if (args[1].equalsIgnoreCase("add")) {
                                if (CustomLocation.getOnlinePlayersName().contains(args[2])) { // プレイヤーがオンライン
                                    Player player1 = Bukkit.getPlayer(args[2]);
                                    if (Member.get("admin", args[3]).contains(player1.getUniqueId().toString())) { // もしも選択したプレイヤーがエリアメンバーだった場合
                                        sender.sendMessage(PLPrefix + ChatColor.RED + "The player is already a member.");
                                        return true;
                                    }
                                    sender.sendMessage(PLPrefix + ChatColor.GREEN + args[2] + " has been made a member.");
                                    new CustomLocation().addRegionMember("admin", args[3], player1.getUniqueId().toString());
                                    return true;
                                }
                                sender.sendMessage(PLPrefix + ChatColor.RED + args[2] + " is not online.");
                                return true;
                            }
                            /**
                             * /land member remove <Player> <Area> admin
                             */
                            if (args[1].equalsIgnoreCase("remove")) {
                                if (CustomLocation.getOnlinePlayersName().contains(args[2])) { // プレイヤーがオンライン
                                    Player player1 = Bukkit.getPlayer(args[2]);
                                    if (Member.get("admin", args[3]).contains(player1.getUniqueId().toString())) { // もしも選択したプレイヤーがエリアメンバーだった場合
                                        sender.sendMessage(PLPrefix + ChatColor.GREEN + args[2] + " has been removed from the list.");
                                        new CustomLocation().removeRegionMember("admin", args[3], player1.getUniqueId().toString());
                                    }
                                    sender.sendMessage(PLPrefix + ChatColor.GREEN + args[2] + " is not a member.");
                                    return true;
                                }
                                sender.sendMessage(PLPrefix + ChatColor.RED + args[2] + " is not online.");
                                return true;
                            }
                        }
                    }
                }
            }
            sender.sendMessage(PLPrefix + ChatColor.RED + "そのようなコマンドはありません。/land help で確認してください。");
            return true;
        }
        return false;
    }
}
