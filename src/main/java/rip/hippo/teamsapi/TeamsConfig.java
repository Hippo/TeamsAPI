package rip.hippo.teamsapi;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import rip.hippo.uuidservice.UUIDService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Hippo
 */
final class TeamsConfig {
  private final Map<String, Integer> teamMap;
  private final FileConfiguration fileConfiguration;
  private final File file;
  private final JavaPlugin javaPlugin;

  TeamsConfig(JavaPlugin javaPlugin, String configName) {
    this.javaPlugin = javaPlugin;

    File directory = new File(javaPlugin.getDataFolder(), "teamsapi");
    if (!directory.exists() && !directory.mkdirs()) {
      throw new RuntimeException("Failed to create TeamsAPI directory.");
    }

    this.fileConfiguration = new YamlConfiguration();
    this.file = new File(directory, configName);

    try {
      fileConfiguration.load(file);
    } catch (IOException | InvalidConfigurationException e) {
      throw new RuntimeException(e);
    }

    this.teamMap = new HashMap<>();

    for (String key : fileConfiguration.getKeys(false)) {
      teamMap.put(key, fileConfiguration.getInt(key));
    }
  }

  TeamsConfig(JavaPlugin javaPlugin) {
    this(javaPlugin, "teams.yml");
  }

  int getTeam(Player player) {
    return getTeamByUUID(player.getUniqueId());
  }

  int getTeam(String playerName) {
    return getTeamByUUID(UUIDService.getUUID(playerName));
  }

  int getTeamByUUID(UUID uuid) {
    return getTeamByUUID(uuid.toString().replace("-", ""));
  }

  int getTeamByUUID(String uuid) {
    return teamMap.get(uuid);
  }

  void setTeam(Player player, int team) {
    setTeamByUUID(player.getUniqueId(), team);
  }

  void setTeam(String playerName, int team) {
    setTeamByUUID(UUIDService.getUUID(playerName), team);
  }

  void setTeamByUUID(UUID uuid, int team) {
    setTeamByUUID(uuid.toString().replace("-", ""), team);
  }

  void setTeamByUUID(String uuid, int team) {
    teamMap.put(uuid, team);
    fileConfiguration.set(uuid, team);
    save();
  }

  private void save() {
    Bukkit.getScheduler().runTaskAsynchronously(javaPlugin, () -> {
      try {
        fileConfiguration.save(file);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

}
