package me.L2_Envy.MSRM.PluginManager.Config;

import me.L2_Envy.MSRM.Core.Objects.TeamObject;
import me.L2_Envy.MSRM.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by Daniel on 8/8/2016.
 */
public class Notes {
    public Main main;
    public Notes(){

    }
    public void link(Main main){
        this.main = main;
    }
    public void exportNotes() {
        File location = new File(main.getDataFolder() + "/Notes/");
        if (location.exists()) {
            //deformats, saves to pregamedata
        } else {
            location.mkdirs();
            main.logger.info("Creating Notes Folder...");
            export("EffectList.txt");
            export("SoundList.txt");
            export("PotionEffectList.txt");
            export("ParticleList.txt");
            export("MaterialsList.txt");
            export("README.txt");
            export("Tutorial.txt");
        }
    }
    public void export(String name) {
        try {
            String home = getClass().getProtectionDomain()
                    .getCodeSource().getLocation()
                    .getPath().replaceAll("%20", " ");
            JarFile jar = new JarFile(home);
            ZipEntry entry = jar.getEntry(name);
            File efile = new File("plugins/MageSpellsRemastered/Notes/", entry.getName());

            InputStream in =
                    new BufferedInputStream(jar.getInputStream(entry));
            OutputStream out =
                    new BufferedOutputStream(new FileOutputStream(efile));
            byte[] buffer = new byte[2048];
            for (; ; ) {
                int nBytes = in.read(buffer);
                if (nBytes <= 0) break;
                out.write(buffer, 0, nBytes);
            }
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
