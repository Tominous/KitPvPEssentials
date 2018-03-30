package life.savag3.KitPvPEssentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import life.savag3.KitPvPEssentials.Methods;
public class main extends JavaPlugin {

    FileConfiguration config = getConfig();
    SettingsManager settings = SettingsManager.getInstance();
    PluginManager pm = Bukkit.getServer().getPluginManager();

    @Override
    public void onEnable() {
        pm.registerEvents(new EventDeath(), this);
        settings.setup(this);
    }

    @Override
    public void onDisable() {


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            // /pvp command
            if (label.equalsIgnoreCase("pvp")) {

            }

            // /killstreak command
            if (label.equalsIgnoreCase("killstreak")) {

            }

        }
        return false;
    }
}
