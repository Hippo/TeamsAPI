package rip.hippo.teamsapi;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import rip.hippo.uuidservice.UUIDService;

import java.util.UUID;

/**
 * @author Hippo
 */
public final class TeamsAPI {

  private final TeamsConfig teamsConfig;

  public TeamsAPI(JavaPlugin javaPlugin) {
    this.teamsConfig = new TeamsConfig(javaPlugin);
  }

  public TeamsAPI(JavaPlugin javaPlugin, String configName) {
    this.teamsConfig = new TeamsConfig(javaPlugin, configName);
  }

  public Integer getTeam(Player player) {
    return teamsConfig.getTeamByUUID(player.getUniqueId());
  }

  public Integer getTeam(String playerName) {
    return teamsConfig.getTeamByUUID(UUIDService.getUUID(playerName));
  }

  public Integer getTeamByUUID(UUID uuid) {
    return teamsConfig.getTeamByUUID(uuid.toString().replace("-", ""));
  }

  public Integer getTeamByUUID(String uuid) {
    return teamsConfig.getTeamByUUID(uuid);
  }

  public void setTeam(Player player, Integer team) {
    teamsConfig.setTeamByUUID(player.getUniqueId(), team);
  }

  public void setTeam(String playerName, Integer team) {
     teamsConfig.setTeamByUUID(UUIDService.getUUID(playerName), team);
  }

  public void setTeamByUUID(UUID uuid, Integer team) {
    teamsConfig.setTeamByUUID(uuid.toString().replace("-", ""), team);
  }

  public void setTeamByUUID(String uuid, Integer team) {
    teamsConfig.setTeamByUUID(uuid, team);
  }
}
