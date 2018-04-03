package life.savag3.KitPvPEssentials;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DataController {
   static SettingsManager settings = SettingsManager.getInstance();
   static  FileConfiguration data = settings.getData();

    public static boolean createSave(Player p ) {
        String dl = "Players." + p.getUniqueId();

        if (!data.contains("Players." + p.getUniqueId())) {
            data.addDefault("Players", p.getUniqueId());
            data.addDefault(dl, "lastKills");
            data.addDefault(dl, "lastMurder");
            data.addDefault(dl, "deaths");
            data.addDefault(dl, "kills");
            Bukkit.getLogger().info("KitPvPEssentials: Creating Player server for " + p);
            settings.saveData();
            return true;
        }

        return false;
    }

    public static int getDeaths (Player p) {
      return data.getInt("Players." + p.getUniqueId() + ".deaths");
    }

    public static int getKills(Player p) {
        return data.getInt("Players." + p.getUniqueId() + ".kills");
    }
    public static int getSteak(Player p) {
        return data.getInt("Players." + p.getUniqueId() + "killStreak");

    }

    public static String[] geDataSize() {
        String[] test = data.getStringList("Players").toArray(new String[10000]);
        return test;
    }


}
