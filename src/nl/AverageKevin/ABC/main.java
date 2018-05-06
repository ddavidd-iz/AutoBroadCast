package nl.AverageKevin.ABC;

import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

@SuppressWarnings("unused")
public class main
  extends JavaPlugin
{
  public void onEnable()
  {
    autobroadcast();
    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args)
  {
    if (alias.equalsIgnoreCase("abc")) {
      if (args.length == 0) {
        sender.sendMessage(ChatColor.STRIKETHROUGH + "-----------------------------------------" + "\n" + 
          "\n" + 
          ChatColor.GOLD + "Autobroadcast by AverageKevin" + "\n" + 
          "\n" + 
          ChatColor.GREEN + "/abc - for help" + "\n" + 
          ChatColor.GREEN + "/abc reload - reload config" + "\n" + 
          "\n" + 
          ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "-----------------------------------------");
      } else if (args[0] == null) {
        sender.sendMessage(ChatColor.STRIKETHROUGH + "-----------------------------------------" + "\n" + 
          "\n" + 
          ChatColor.GOLD + "Autobroadcast by AverageKevin" + "\n" + 
          "\n" + 
          ChatColor.GREEN + "/abc - for help" + "\n" + 
          ChatColor.GREEN + "/abc reload - reload config" + "\n" + 
          "\n" + 
          ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "-----------------------------------------");
      } else if (args[0].equalsIgnoreCase("reload")) {
        if (!sender.hasPermission("abc.reload"))
        {
          String nopermission = getConfig().getString("nopermissionmessage");
          sender.sendMessage(nopermission);
        }
        else
        {
          reloadConfig();
          
          sender.sendMessage(ChatColor.GREEN + "The config file has been updated. Reload Succes!");
        }
      }
    }
    return false;
  }
  
  public void autobroadcast()
  {
    final String sound = getConfig().getString("sound");
    int interval = getConfig().getInt("interval");
    final String header = getConfig().getString("Header");
    final String footer = getConfig().getString("Footer");
    
    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
    {
      public void run()
      {
        @SuppressWarnings("rawtypes")
		List messages = main.this.getConfig().getStringList("messages");
        Random rand = new Random();
        int choice = rand.nextInt(messages.size());
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', header) + "§r\n ");
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', (String)messages.get(choice)));
        Bukkit.broadcastMessage("\n" + ChatColor.translateAlternateColorCodes('&', footer));
        for (Player p : Bukkit.getOnlinePlayers()) {
          p.playSound(p.getLocation(), Sound.valueOf(sound), 10.0F, 1.0F);
        }
      }
    }, 0L, interval * 20L);
  }
}