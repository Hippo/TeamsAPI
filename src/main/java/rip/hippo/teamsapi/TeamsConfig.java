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
    this.teamMap = new HashMap<>();

    try {
      if (!file.exists()) {
        if (!file.createNewFile()) {
          throw new IOException("Failed to create teams config!");
        }
        return;
      }
      fileConfiguration.load(file);
    } catch (IOException | InvalidConfigurationException e) {
      throw new RuntimeException(e);
    }


    for (String key : fileConfiguration.getKeys(false)) {
      teamMap.put(key, fileConfiguration.getInt(key));
    }
  }

  TeamsConfig(JavaPlugin javaPlugin) {
    this(javaPlugin, "teams.yml");
  }

  Integer getTeam(Player player) {
    return getTeamByUUID(player.getUniqueId());
  }

  Integer getTeam(String playerName) {
    return getTeamByUUID(UUIDService.getUUID(playerName));
  }

  Integer getTeamByUUID(UUID uuid) {
    return getTeamByUUID(uuid.toString().replace("-", ""));
  }

  Integer getTeamByUUID(String uuid) {
    return teamMap.get(uuid);
  }

  void setTeam(Player player, Integer team) {
    setTeamByUUID(player.getUniqueId(), team);
  }

  void setTeam(String playerName, Integer team) {
    setTeamByUUID(UUIDService.getUUID(playerName), team);
  }

  void setTeamByUUID(UUID uuid, Integer team) {
    setTeamByUUID(uuid.toString().replace("-", ""), team);
  }

  void setTeamByUUID(String uuid, Integer team) {
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
