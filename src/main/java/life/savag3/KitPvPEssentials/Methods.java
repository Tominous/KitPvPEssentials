package life.savag3.KitPvPEssentials;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Methods {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    public static String playerPH(String msg, Player p) {
        return msg.replaceAll("%player%", p.getName());
    }
    public static String killerPH(String msg, Player p) {
        return msg.replaceAll("%killer%", p.getName());
    }
    public static String streakPH(String msg, Player p) {
        return msg.replaceAll("%steak%", Integer.toString(DataController.getSteak(p)));
    }

    public static int rand (int max, int min) {
        int rand;
        rand = min + (int) (Math.random() * max);
        return rand;
    }

}
