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

        Player p = e.getEntity().getPlayer();
        Player k = p.getKiller();
        int rand;
        ConsoleCommandSender console = Bukkit.getConsoleSender();

        //Lightning
        if (k.getHealth() > 10) {
            if (config.getBoolean("Settings.Lightning")) {
                k.getWorld().strikeLightning(k.getLocation());
            }
        }


        //Commands onDeathEvent
        String[] groups = config.getStringList("Settings.Group").toArray(new String[0]);
        String[] g = new String[0];
        for (int i = 0; i < groups.length; i++) {
            String groupname = "NAME." + groups[i];
            g = config.getStringList("Settings.Group." + groups[i]).toArray(new String[0]);

            if (!k.hasPermission("killrewards." + groups[i])) {
                break;
            }
            if (k.hasPermission("killrewards" + groups[i])) {
                String[] cmd = config.getStringList("Settings.Groups." + groups[i] + ".Commands").toArray(new String[0]);
                for (int x = 0; x < cmd.length; x++) {
                    if (!(cmd[0] == null)) {
                        String command = cmd[0];
                        int count = cmd.length;
                        String[] parts = command.split(",");
                        if (Integer.getInteger(parts[0]) >= 100) {
                            command = Methods.color(command);
                            command = Methods.placeholders(command, p, k);
                            Bukkit.dispatchCommand(console, command);
                        } else {
                            rand = Methods.rand(1, 100);
                            if (rand < Integer.getInteger(parts[0])) {
                                command = Methods.color(command);
                                command = Methods.placeholders(command, p, k);
                                Bukkit.dispatchCommand(console, command);
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


                    //Death Messages
                    if (config.getBoolean("Settings.DeathMessage.Enabled")) {
                        if (config.getBoolean("Settings.DebugInConsole")) {
                            Bukkit.getLogger().info("KitPvPEssentials: [DEBUG] Printing DeathMessage");
                        }

                        String BroadcastMessage = config.getString("Settings.DeathMessage.Message");
                        BroadcastMessage = Methods.placeholders(BroadcastMessage, p, k);
                        BroadcastMessage = Methods.color(BroadcastMessage);
                        Bukkit.broadcastMessage(BroadcastMessage);
                    }


                }
            }
        }
    }
}



/*

* KitPvP Essentials *

Features:

- Lightning on death
- Execute Commands on Death
- Commands + Chance option
- Toggle Player Drops
- Kill Streak + Config rewards
- -database support
- PlayerDeath messages


Commands:
- killstreak
    - top ~ Top Kill Streak's
    - null ~ Players Current Kill Streak
- pvp
    - reload ~ Reload Config
    - list
        - groups / null ~ list groups
        - <groupname> ~ list groups drops + chance


 */
