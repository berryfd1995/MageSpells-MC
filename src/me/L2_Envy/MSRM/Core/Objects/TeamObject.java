package me.L2_Envy.MSRM.Core.Objects;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by Daniel on 7/24/2016.
 */
public class TeamObject {
    private String teamname;
    private UUID leader;
    private UUID officer;
    private List<UUID> memebers;
    private File teamFile;
    private FileConfiguration teamconfig;
    public TeamObject(String teamname, UUID leader, UUID officer, List<UUID> members, File teamFile, FileConfiguration teamconfig){
        this.teamname = teamname;
        this.leader = leader;
        this.officer = officer;
        this.memebers = members;
        this.teamFile = teamFile;
        this.teamconfig = teamconfig;
    }
    public FileConfiguration getTeamconfig(){
        return teamconfig;
    }
    public File getTeamFile(){
        return teamFile;
    }
    public String getTeamname() {
        return teamname;
    }

    public UUID getLeader() {
        return leader;
    }

    public UUID getOfficer() {
        return officer;
    }
    public void setOfficer(UUID uuid){
        this.officer = uuid;
    }
    public void addPlayer(UUID uuid){
        if(!memebers.contains(uuid)){
            memebers.add(uuid);
        }
    }
    public void removePlayer(UUID uuid){
        if(memebers.contains(uuid)){
            memebers.remove(uuid);
        }
    }
    public boolean hasPlayer(UUID uuid){
        if(leader != null){
            if(leader.equals(uuid)){
                return true;
            }
        }
        if(officer != null){
            if(officer.equals(uuid)){
                return true;
            }
        }
        if(memebers.contains(uuid)){
            return true;
        }
        return false;
    }
    public List<UUID> getMemebers(){
        return memebers;
    }


}
