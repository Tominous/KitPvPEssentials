package life.savag3.KitPvPEssentials;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Methods {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String placeholders(String msg, Player p, Player k) {

        if (msg.contains("%Player%")) {
            msg.replaceAll("%Player%", p.getName());
        }
        if (msg.contains("%Killer%")) {
            msg.replaceAll("%Killer%", k.getName());
        }
        return msg;
    }

    public static int rand (int max, int min) {
        int rand;
        rand = min + (int) (Math.random() * max);
        return rand;
    }

}
