package life.savag3.KitPvPEssentials;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.PlayerDeathEvent;

public class EventDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        SettingsManager settings = SettingsManager.getInstance();
        FileConfiguration config = settings.getConfig();
        FileConfiguration data = settings.getData();
        Player p = e.getEntity().getPlayer();
        Player k = p.getKiller();
        int rand;
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        if (!data.contains("Players." + p.getUniqueId())) {
            DataController.createSave(p);
        }
        if (!data.contains("Players." + k.getUniqueId())) {
            DataController.createSave(k);
        }
        if (config.getBoolean("Settings.KillStreaks.AnnounceSteakEnd")) {
            if (DataController.getSteak(p) >= config.getInt("Settings.KillStreaks.AnnounceKillStreakEnd.MinAmountOfKills")) {
                String msg = config.getString("Settings.KillStreaks.AnnounceKillStreakEnd.AnnounceMessage");
                msg = Methods.killerPH(msg, k);
                msg = Methods.playerPH(msg, p);
                msg = Methods.streakPH(msg, p);
                msg = Methods.color(msg);
                Bukkit.broadcastMessage(msg);
            }
        }
        data.set("Players." + p.getUniqueId() + ".killStreak", 0);
        //Lightning
        if (k.getHealth() > 10) {
            if (config.getBoolean("Settings.Lightning")) {
                k.getWorld().strikeLightning(k.getLocation());
            }
        }
        //Commands onDeathEvent
        String[] groups = config.getStringList("Settings.Group").toArray(new String[10000]);
        String[] g = new String[0];
        for (int i = 0; i < groups.length; i++) {
            String groupname = "NAME." + groups[i];
            g = config.getStringList("Settings.Group." + groups[i]).toArray(new String[10000]);
            if (!k.hasPermission("killrewards." + groups[i])) {
                break;
            }
            if (k.hasPermission("killrewards" + groups[i])) {
                String[] cmd = config.getStringList("Settings.Groups." + groups[i] + ".Commands").toArray(new String[10000]);
                for (int x = 0; x < cmd.length; x++) {
                    if (!(cmd[x] == null)) {
                        String command = cmd[x];
                        String c;
                        int count = cmd.length;
                        String[] parts = command.split(",");
                        if (Integer.getInteger(parts[0]) >= 100) {
                            c = Methods.killerPH(parts[1], k);
                            c = Methods.playerPH(parts[1], p);
                            c = Methods.streakPH(parts[1], p);
                            c = Methods.color(parts[1]);
                            Bukkit.dispatchCommand(console, c);
                        } else {
                            rand = Methods.rand(1, 100);
                            if (rand < Integer.getInteger(parts[0])) {
                                c = Methods.killerPH(parts[1], k);
                                c = Methods.playerPH(parts[1], p);
                                c = Methods.streakPH(parts[1], p);
                                c = Methods.color(parts[1]);
                                Bukkit.dispatchCommand(console, c);
                            }
                        }
                    }
                    //Toggle Drops
                    if (config.getBoolean("Settings.DropItemsOnDeath")) {
                        if (config.getBoolean("Settings.DebugInConsole")) {
                            Bukkit.getLogger().info("KitPvPEssentials: [DEBUG] Clearing player drops on death!");
                        }
                        e.getDrops().clear();
                    }
                    //KillStreaks
                    boolean good = true;
                    if (config.getBoolean("Settings.KillStreaks.Enabled")) {
                        // Kill Farming Check
                        String[] d;
                        d = data.getString("Players." + k.getUniqueId() + "lastKills").split(",");
                        String word = p.getName();
                        int count = 0;
                        if (config.getBoolean("Settings.DebugInConsole")) {
                            Bukkit.getLogger().info("KitPvPEssentials: [DEBUG] Checking KillFarming Args");
                        }
                        for (String element : d.toString().split(",")) {
                            if (element.equalsIgnoreCase("word")) {
                                count++;
                            }
                        }
                        if (count < config.getInt("Settings.KillStreaks.stopKillFarming.Threshold") - 1) {
                            if (config.getBoolean("Settings.DebugInConsole")) {
                                Bukkit.getLogger().info("KitPvPEssentials: [DEBUG] Player was found not to be killfarming, continuing.");
                            }
                            // Fix Last Kills / Data Info
                            if (config.getBoolean("Settings.DebugInConsole")) {
                                Bukkit.getLogger().info("KitPvPEssentials: [DEBUG] Attempting to correct player data.");
                            }
                            data.set("Players." + p.getUniqueId() + ".deaths", data.getInt("Players." + p.getUniqueId() + "deaths") + 1); //Fixing p's deaths (+1)
                            data.set("Players." + k.getUniqueId() + ".kills", data.getInt("Players." + k.getUniqueId() + ".kills") + 1);  //Fixing k's kills (+1)
                            settings.saveData();

                            String raw = data.getString("Players." + k.getUniqueId() + "lastKills"); //Fixing lastKills (Removing kill 5, adding new kill)
                            String [] format = raw.split(",");
                            String newString = p.getName() + format[0] + format[1] + format[2] + format[3];
                            data.set("Players." + k.getUniqueId() + "lastKills", newString);

                            // Get Rewards
                            if (config.getBoolean("Settings.DebugInConsole")) {
                                Bukkit.getLogger().info("KitPvPEssentials: [DEBUG] Sending " + k.getName() + " their killstreak rewards.");
                            }
                        } else { // Cancelling Kill Farming.
                           k.sendMessage(Methods.color(config.getString("Settings.KillStreaks.stopKillFarming.CancelMessage")));
                        }
                    }
                    //Death Messages
                    if (config.getBoolean("Settings.DeathMessage.Enabled")) {
                        if (config.getBoolean("Settings.DebugInConsole")) {
                            Bukkit.getLogger().info("KitPvPEssentials: [DEBUG] Printing DeathMessage");
                        }
                        String b = config.getString("Settings.DeathMessage.Message");
                        b = Methods.killerPH(b, k);
                        b = Methods.playerPH(b, p);
                        b = Methods.streakPH(b, k);
                        Bukkit.broadcastMessage(b);
                    }
                }
            }
        }
    }
}
