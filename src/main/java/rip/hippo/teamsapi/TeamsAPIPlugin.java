package rip.hippo.teamsapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * @author Hippo
 */
public final class TeamsAPIPlugin extends JavaPlugin {
  @Override
  public void onEnable() {
    Bukkit.getLogger().log(Level.INFO, "Loaded Teams API.");
  }
}
