package life.savag3.KitPvPEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    FileConfiguration config = getConfig();
    SettingsManager settings = SettingsManager.getInstance();
    PluginManager pm = Bukkit.getServer().getPluginManager();

    @Override
    public void onEnable() {
        pm.registerEvents(new EventDeath(), this);
        settings.setup(this);

        if (DataController.geDataSize().length >= 5000) {
            getLogger().info("data.yml file is not holding more then 5000 players info.");
            getLogger().info("Performance will begin to fade if you do not make the switch");
            getLogger().info("to MySQL data storage. If you are seeing this message, contact Developer.");
        }
    }

    @Override
    public void onDisable() {


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {


            Player r;
            int deaths;
            int kills;
            int streak;
            String msg;

            // /pvp command
            if (label.equalsIgnoreCase("pvp")) {

                //reload

                //rewards

                //groups
                    // <groupname>

            }

            // /killstreak command
            if (label.equalsIgnoreCase("killstreak") || label.equalsIgnoreCase("streak")) {
                if (!(args.length >= 1)) {
                    r = (Player) sender;
                    streak = DataController.getSteak(r);
                    msg = "&aYou current KillStreak is " + streak;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                } else {
                    r = Bukkit.getPlayer(args[0]);
                    streak = DataController.getSteak(r);
                    msg = "&a" + r.getName() + "'s current KillStreak is " + streak;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }


            if (label.equalsIgnoreCase("kdr")) {

                if (!(args.length >= 1)) {
                    // self
                    r = (Player) sender;
                    deaths = DataController.getDeaths(r);
                    kills = DataController.getKills(r);
                    msg = "&aYour current KDR is: " + kills + "/" + deaths;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    return true;
                } else {
                    // others
                    r = Bukkit.getPlayer(args[0]);
                    deaths = DataController.getDeaths(r);
                    kills = DataController.getKills(r);
                    msg = "&a" + r.getName() + "'s current KDR is: " + kills + "/" + deaths;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    return true;
                }

            }

        }
        return false;
    }
}
