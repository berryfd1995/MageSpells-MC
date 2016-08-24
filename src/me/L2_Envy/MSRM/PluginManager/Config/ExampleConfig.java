package me.L2_Envy.MSRM.PluginManager.Config;

import me.L2_Envy.MSRM.Main;

import java.io.*;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by Daniel on 8/9/2016.
 */
public class ExampleConfig {
    public Main main;
    public ExampleConfig(){

    }
    public void link(Main main){
        this.main = main;
    }
    public void exportExamples() {
        File location = new File(main.getDataFolder() + "/Examples/");
        if (location.exists()) {
            //deformats, saves to pregamedata
        } else {
            location.mkdirs();
            main.logger.info("Creating Examples Folder...");
            export("SpellExample.yml");
            export("TeamExample.yml");
            export("WandExample.yml");
            export("CustomItemExample.yml");
            export("PlayerDataExample.yml");
        }
    }
    public void export(String name) {
        try {
            String home = getClass().getProtectionDomain()
                    .getCodeSource().getLocation()
                    .getPath().replaceAll("%20", " ");
            JarFile jar = new JarFile(home);
            ZipEntry entry = jar.getEntry(name);
            File efile = new File("plugins/MageSpellsRemastered/Examples/", entry.getName());

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
