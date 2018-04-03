package life.savag3.KitPvPEssentials;

import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

    public class SettingsManager {

        static SettingsManager instance = new SettingsManager();

        public static SettingsManager getInstance() {
            return instance;
        }

        Plugin p;
        FileConfiguration config;
        File cfile;

        FileConfiguration data;
        File datafile;

        public void setup(Plugin p) {
            if(!p.getDataFolder().exists()) {
                p.getDataFolder().mkdir();
            }
            cfile = new File(p.getDataFolder(), "config.yml");
            if(!cfile.exists()) {
                try {
                    File en = new File(p.getDataFolder(), "/config.yml");
                    InputStream E = getClass().getResourceAsStream("/config.yml");
                    copyFile(E, en);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
            config = YamlConfiguration.loadConfiguration(cfile);

            datafile = new File(p.getDataFolder(), "data.yml");
            if(!datafile.exists()) {
                try {
                    File en = new File(p.getDataFolder(), "/data.yml");
                    InputStream E = getClass().getResourceAsStream("/data.yml");
                    copyFile(E, en);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
            data = YamlConfiguration.loadConfiguration(datafile);


        }

        public FileConfiguration getConfig() {
            return config;
        }

        public FileConfiguration getData() {
            return data;
        }
        public void saveData() {
            try {
                data.save(datafile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        public static void copyFile(InputStream in, File out) throws Exception { // https://bukkit.org/threads/extracting-file-from-jar.16962/
            InputStream fis = in;
            FileOutputStream fos = new FileOutputStream(out);
            try {
                byte[] buf = new byte[1024];
                int i = 0;
                while((i = fis.read(buf)) != -1) {
                    fos.write(buf, 0, i);
                }
            }catch(Exception e) {
                throw e;
            }finally {
                if(fis != null) {
                    fis.close();
                }
                if(fos != null) {
                    fos.close();
                }
            }
        }
    }

