package me.L2_Envy.MSRM.Core.Teams;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.TeamObject;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Daniel on 7/23/2016.
 */
public class TeamManager {
    public MageSpellsManager mageSpellsManager;
    private ArrayList<TeamObject> teamObjects;
    private HashMap<TeamObject, UUID> invitelist;
    private boolean usercreatesteam = false;

    /**
     * TODO: Fix invite system/ double check it.
     * TODO: More options for leaders
     * TODO: Kick feature.
     * TODO: For below, create option(s)
     * TODO: Other features: Tags, Stat tracker, Managability(Kick, Promote, invite)
     * TODO: Add in API features
     * TODO: Work on the deletion factor
     */
    public TeamManager(){
        teamObjects = new ArrayList<>();
        invitelist = new HashMap<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void addTeamObject(TeamObject teamObject){
        if(!teamObjects.contains(teamObject)){
            teamObjects.add(teamObject);
        }
    }
    public void removeTeamObject(TeamObject teamObject){
        if(!teamObjects.contains(teamObject)){
            teamObjects.remove(teamObject);
        }
    }
    public void saveTeams(){
        for(TeamObject teamObject : (ArrayList<TeamObject>)teamObjects.clone()){
            mageSpellsManager.main.pluginManager.teamConfig.saveTeamDataNOW(teamObject);
        }
    }
    public void setUsercreatesteam(boolean usercreatesteam){
        this.usercreatesteam = usercreatesteam;
    }
    public boolean getUsercreatesteam(){
        return usercreatesteam;
    }
    public boolean onSameTeam(Player player, Player player2){
        TeamObject teamObject = getTeam(player);
        TeamObject teamObject1 = getTeam(player2);
        if(teamObject!= null && teamObject1 !=null){
            if(teamObject.equals(teamObject1)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    public boolean promotePlayer(Player promoter, Player promotee){
        TeamObject teamObject = getTeam(promoter);
        TeamObject teamObject1 = getTeam(promotee);
        if(teamObject != null && teamObject1 == null){
            if(teamObject.equals(teamObject1)){
                if(teamObject.getLeader().equals(promoter.getUniqueId())){
                    teamObject.addOfficer(promotee.getUniqueId());
                    return true;
                }
            }
        }
        return false;
    }
    public boolean addPlayer(String team, Player player){
        TeamObject teamObject = getTeam(player);
        TeamObject teamObject1 = getTeam(team);
        if(teamObject == null && teamObject1 != null){
            teamObject1.addPlayer(player.getUniqueId());
            return true;
        }
        return false;
    }
    public boolean removePlayer(String team, Player player){
        TeamObject teamObject = getTeam(player);
        TeamObject teamObject1 = getTeam(team);
        if(teamObject != null && teamObject1 != null){
            teamObject1.removePlayer(player.getUniqueId());
            return true;
        }
        return false;
    }
    public boolean leaveTeam(Player player){
        TeamObject teamObject = getTeam(player);
        if(teamObject != null){
            if(!teamObject.getLeader().equals(player.getUniqueId())){
                if(teamObject.getOfficer().equals(player.getUniqueId())){
                    teamObject.addOfficer(null);
                    teamObject.removePlayer(player.getUniqueId());
                    return true;
                }
            }
        }
        return false;
    }
    public boolean createTeam(String teamname, Player player){
        TeamObject teamObject = getTeam(player);
        TeamObject teamObject2 = getTeam(teamname);
        if(teamObject == null && teamObject2 == null) {
            mageSpellsManager.main.pluginManager.teamConfig.createTeamData(teamname,player.getUniqueId());
            return true;
        }
        return false;
    }
    public boolean disbandTeam(Player player){
        TeamObject teamObject = getTeam(player);
        if(teamObject != null) {
            if (teamObject.getLeader().equals(player.getUniqueId())) {
                mageSpellsManager.main.pluginManager.teamConfig.removeTeam(teamObject);
                teamObjects.remove(teamObject);
            }
        }
        return false;
    }
    public boolean invitePlayer(Player inviter,Player invitee){
        TeamObject teamObject = getTeam(inviter);
        TeamObject teamObject1 = getTeam(invitee);
        if(teamObject != null && teamObject1 == null){
            if(teamObject.getLeader() == inviter.getUniqueId() || teamObject.isOfficer(inviter.getUniqueId())){
                invitelist.put(teamObject,invitee.getUniqueId());
                return true;
            }
        }
        return false;
    }
    public boolean acceptInvite(Player player, String team){
        TeamObject teamObject = getTeam(player);
        TeamObject teamObject1 = getTeam(team);
        if(teamObject == null && teamObject1 != null){
            if(invitelist.containsKey(teamObject1)){
                if(invitelist.get(teamObject1).equals(player.getUniqueId())){
                    invitelist.remove(teamObject1);
                    teamObject1.addPlayer(player.getUniqueId());
                    return true;
                }
            }
        }
        return false;
    }
    public boolean declineInvite(Player player, String team){
        TeamObject teamObject1 = getTeam(team);
        if(teamObject1 != null){
            if(invitelist.containsKey(teamObject1)){
                if(invitelist.get(teamObject1).equals(player.getUniqueId())){
                    invitelist.remove(teamObject1);
                    return true;
                }
            }
        }
        return false;
    }
    public TeamObject getTeam(PlayerObject playerObject){
        for(TeamObject teamObject : teamObjects){
            if(teamObject.hasPlayer(playerObject.getUuid())){
                return teamObject;
            }
        }
        return null;
    }
    public TeamObject getTeam(Player player){
        for(TeamObject teamObject : teamObjects){
            if(teamObject.hasPlayer(player.getUniqueId())){
                return teamObject;
            }
        }
        return null;
    }
    public TeamObject getTeam(String name){
        for(TeamObject teamObject : teamObjects){
            if(teamObject.getTeamname().equalsIgnoreCase(name)){
                return teamObject;
            }
        }
        return null;
    }
}
