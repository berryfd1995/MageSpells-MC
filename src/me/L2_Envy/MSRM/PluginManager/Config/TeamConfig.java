package me.L2_Envy.MSRM.PluginManager.Config;

import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.TeamObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.Main;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by Daniel on 7/25/2016.
 */
public class TeamConfig {
    public Main main;
    public TeamConfig(){

    }
    public void link(Main main){
        this.main = main;
    }
    public void loadTeamConfigs() {
        File location = new File(main.getDataFolder() + "/Teams/");
        if (location.exists()) {
            main.logger.info("Loading Teams...");
            for (File fileData : location.listFiles()) {
                try {
                    if(!fileData.isHidden()) {
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(fileData);
                        String teamName  =FilenameUtils.getBaseName(fileData.getName());
                        TeamObject teamData = deformatData(teamName, fileData, config);
                        if(teamData != null) {
                            main.mageSpellsManager.teamManager.addTeamObject(teamData);
                            main.logger.info(teamName + " loaded.");
                        }else{
                            main.logger.info("Error loading " + teamName);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error with file " + fileData.getName());
                    e.printStackTrace();
                }
            }
            //deformats, saves to pregamedata
        } else {
            main.logger.info("Creating Teams Folder...");
            location.mkdirs();
            //firstLoad();
        }
    }
    public void firstLoad() {
        try {
            String home = getClass().getProtectionDomain()
                    .getCodeSource().getLocation()
                    .getPath().replaceAll("%20", " ");
            JarFile jar = new JarFile(home);
            ZipEntry entry = jar.getEntry("RedTeam.yml");
            File efile = new File("plugins/MageSpellsRemastered/Teams/", entry.getName());

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
    public TeamObject deformatData(String teamName, File file ,YamlConfiguration config) {
        try {
            String leader = config.getString("Leader");
            UUID leaderuuid = UUID.fromString(leader);
            List<String> officers = config.getStringList("Officers");
            List<UUID> officeruuids = new ArrayList<>();
            if(!officeruuids.isEmpty() && officers != null) {
                for(String officer : officers){
                    UUID officeruuid = UUID.fromString(officer);
                    if(officeruuid != null){
                        officeruuids.add(officeruuid);
                    }
                }
            }
            List<String> members = config.getStringList("Members");
            List<UUID> memberuuids = new ArrayList<>();
            if(!members.isEmpty() && members != null) {
                memberuuids = new ArrayList<UUID>();
                for (String member : members) {
                    UUID memberuuid = UUID.fromString(member);
                    if (memberuuid != null) {
                        memberuuids.add(memberuuid);
                    }
                }
            }
            return new TeamObject(teamName,leaderuuid,officeruuids,memberuuids, file, config);
        }catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public void saveTeamData(TeamObject teamObject){
        Bukkit.getScheduler().runTaskAsynchronously(main,() -> {
            try {
                formatData(teamObject);
                teamObject.getTeamconfig().save(teamObject.getTeamFile());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    public void saveTeamDataNOW(TeamObject teamObject){
        try {
            formatData(teamObject);
            teamObject.getTeamconfig().save(teamObject.getTeamFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void formatData(TeamObject teamObject){
        if(teamObject.getLeader() != null){
            teamObject.getTeamconfig().createSection("Leader");
            teamObject.getTeamconfig().set("Leader", teamObject.getLeader().toString());
        }
        if(teamObject.getOfficer() != null){
            teamObject.getTeamconfig().createSection("Officer");
            teamObject.getTeamconfig().set("Officer", teamObject.getOfficer().toString());
        }else{
            teamObject.getTeamconfig().createSection("Officer");
            teamObject.getTeamconfig().set("Officer", null);
        }
        if(teamObject.getMemebers() != null){
            if(!teamObject.getMemebers().isEmpty()){
                teamObject.getTeamconfig().createSection("Members");
                teamObject.getTeamconfig().set("Members", teamObject.getMemebers());
            }
        }
    }
    public void createTeamData(String teamname, UUID uuid){
        TeamObject teamObject;
        File teamFile = createTeamFile(teamname);
        FileConfiguration teamConfig = YamlConfiguration.loadConfiguration(teamFile);
        teamObject = new TeamObject(teamname,uuid,new ArrayList<>(),new ArrayList<>(), teamFile,teamConfig);
        main.mageSpellsManager.teamManager.addTeamObject(teamObject);
        saveTeamData(teamObject);
    }
    public void removeTeam(TeamObject teamObject){
        teamObject.getTeamFile().delete();
    }
    private File createTeamFile(String teamname) {
        try {
            File gameFile = new File(main.getDataFolder() + "/Teams/", teamname + ".yml");
            if (!gameFile.exists()) {
                gameFile.createNewFile();
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            },20L);
            return gameFile;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
