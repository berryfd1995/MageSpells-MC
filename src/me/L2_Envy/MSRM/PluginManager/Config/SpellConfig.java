package me.L2_Envy.MSRM.PluginManager.Config;

import me.L2_Envy.MSRM.Core.Effects.Preset.NormalEffect;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ParticleObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Spells.SpellManager;
import me.L2_Envy.MSRM.Main;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by Daniel on 7/25/2016.
 */
public class SpellConfig {
    public Main main;
    public SpellManager spellManager;
    public SpellConfig(){

    }
    public void link(Main main){
        this.main = main;
    }
    public void loadSpellConfigs() {
        File location = new File(main.getDataFolder() + "/Spells/");
        if (location.exists()) {
            main.logger.info("Loading Spells...");
            for (File fileData : location.listFiles()) {
                try {
                    if(!fileData.isHidden()) {
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(fileData);
                        String spellName  = FilenameUtils.getBaseName(fileData.getName());
                        SpellObject spellData = deformatData(spellName, config, fileData);
                        if(spellData!= null) {
                            main.mageSpellsManager.spellManager.addSpellObject(spellData);
                           // main.logger.info(spellData.getName() + " loaded.");
                        }else{
                            main.logger.info("Error loading " + spellName);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error with file " + fileData.getName());
                    e.printStackTrace();
                }
            }
            //deformats, saves to pregamedata
        } else {
            main.logger.info("Creating Spells Folder...");
            location.mkdirs();
            firstLoad("AirShot.yml");
            firstLoad("BasicSpell.yml");
            firstLoad("Blizzard.yml");
            firstLoad("DeathGrip.yml");
            firstLoad("EarthQuake.yml");
            firstLoad("Fireball.yml");
            firstLoad("Heal.yml");
            firstLoad("Rocket.yml");
            firstLoad("Thor.yml");
            firstLoad("ChainLightning.yml");
            firstLoad("DrainLife.yml");
            firstLoad("ForcePush.yml");
            firstLoad("LightningStorm.yml");
            firstLoad("Medusa.yml");
            firstLoad("MeteorShower.yml");
            firstLoad("MeteorStrike.yml");
            firstLoad("WallOfFire.yml");
            loadSpellConfigs();
        }
    }
    public void firstLoad(String spell) {
        try {
            String home = getClass().getProtectionDomain()
                    .getCodeSource().getLocation()
                    .getPath().replaceAll("%20", " ");
            JarFile jar = new JarFile(home);
            ZipEntry entry = jar.getEntry(spell);
            File efile = new File("plugins/MageSpellsRemastered/Spells/", entry.getName());

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
    public SpellObject deformatData(String spellName, YamlConfiguration config, File file) {
        try {
            String displayname = main.utils.colorize(config.getString("DisplayName"));
            String lore = main.utils.colorize(config.getString("Lore"));
            String spellnode = config.getString("SpellNode");
            boolean boltenabled = config.getBoolean("Bolt.Enabled");
            int boltradius = config.getInt("Bolt.DamageRadius");
            double boltdamage = config.getDouble("Bolt.BoltDamage");
            boolean auraenabled = config.getBoolean("Aura.Enabled");
            int auratime = config.getInt("Aura.ActiveTime");
            int auraradius = config.getInt("Aura.AuraRadius");
            double auradamage = config.getDouble("Aura.AuraDamage");
            boolean sprayenabled = config.getBoolean("Spray.Enabled");
            int sprayradius = config.getInt("Spray.SprayRadius");
            double spraydamage = config.getDouble("Spray.SprayDamage");
            int armorpiercing = config.getInt("ArmorPiercing");
            int manacost = config.getInt("ManaCost");
            int moneycost = config.getInt("MoneyCost");
            int cooldown = config.getInt("Cooldown");
            int chargetime = config.getInt("ChargeTime");
            int traveldistance = config.getInt("TravelDistance");
            if(!config.contains("SpellSpeed")){
                config.createSection("SpellSpeed");
                config.set("SpellSpeed", "200");
            }
            int spellspeed = config.getInt("SpellSpeed");
            int requiredleveltobind = config.getInt("RequiredLevelToBind");
            int requiredleveltocast = config.getInt("RequiredLevelToCast");
            int requiredleveltodrop = config.getInt("RequiredLevelToDrop");
            boolean affectsmobs = config.getBoolean("AffectMobs");
            boolean affectself = config.getBoolean("AffectSelf");
            boolean affectenemy = config.getBoolean("AffectEnemy");
            boolean affectteammates = config.getBoolean("AffectTeammates");
            ItemStack spellbook = main.utils.getItemStack("Enchanted_Book", displayname, lore);
            String spelleffectname = config.getString("SpecialEffect");
            SpellEffect spelleffect;
            String soundname = config.getString("Sound.Name");
            Sound sound = Sound.valueOf(soundname.toUpperCase());
            double volume = config.getDouble("Sound.Volume");
            double pitch = config.getDouble("Sound.Pitch");
            float soundvolume = (float) volume;
            float soundpitch = (float) pitch;
            ArrayList<ParticleObject> particleObjects = new ArrayList<>();
            for(String particleKey : config.getConfigurationSection("Particles.").getKeys(false)){
                String particle = particleKey;
                int amount = config.getInt("Particles." + particleKey + ".Amount");
                double x = config.getDouble("Particles." + particleKey + ".offSetX");
                double y = config.getDouble("Particles." + particleKey + ".offSetY");
                double z = config.getDouble("Particles." + particleKey + ".offSetZ");
                double speedD = config.getDouble("Particles." + particleKey + ".Speed");
                float offSetX = (float) x;
                float offSetY = (float) y;
                float offSetZ = (float) z;
                float speed = (float) speedD;
                ParticleObject particleObject = new ParticleObject(particle,amount,offSetX,offSetY,offSetZ,speed);
                particleObjects.add(particleObject);
            }
            ArrayList<PotionEffect> potionEffects = new ArrayList<>();
            for (String potionKey : config.getConfigurationSection("PotionEffects.").getKeys(false)) {
                int duration = config.getInt("PotionEffects." + potionKey + ".Duration") * 20;
                int amplification = config.getInt("PotionEffects." + potionKey + ".Amplification");
                PotionEffectType potionEffectType = PotionEffectType.getByName(potionKey);
                PotionEffect potionEffect = new PotionEffect(potionEffectType,duration,amplification);
                potionEffects.add(potionEffect);
            }
            boolean mobdropsenabled = config.getBoolean("MobDrops.Settings.Enable");
            HashMap<EntityType, Double> mobDrops = new HashMap<>();
            if(mobdropsenabled){
                for(String mobtype : config.getConfigurationSection("MobDrops.Drops.").getKeys(false)){
                    EntityType entityType;
                    try {
                       entityType = EntityType.valueOf(mobtype.toUpperCase());
                    } catch (Exception e) {
                        entityType = null;
                    }
                    if (entityType != null) {
                        double chance = config.getDouble("MobDrops.Drops." + mobtype + ".Chance");
                        mobDrops.put(entityType, chance);
                    } else {
                        System.out.println("Could not register mob: " + mobtype);
                        System.out.println("Please make sure it is a valid mob!");
                    }
                }
            }
            HashMap<ItemStack, Integer> itemcost = new HashMap<>();
            boolean itemcostsenabled = config.getBoolean("ItemCost.Settings.Enabled");
            if(itemcostsenabled){
                for(String itemtype : config.getConfigurationSection("ItemCost.Items.").getKeys(false)){
                    ItemStack itemStack;
                    try {
                        itemStack = main.utils.getItemStack(itemtype);
                    } catch (Exception e) {
                        itemStack = null;
                    }
                    if (itemStack != null) {
                        int amount = config.getInt("ItemCost.Items." + itemtype);
                        itemcost.put(itemStack, amount);
                    } else {
                        System.out.println("Could not register Item: " + itemtype);
                        System.out.println("Please make sure it is a valid item!");
                    }
                }
            }
            //
            if(!config.contains("CastCommand")){
                config.createSection("CastCommand");
                config.set("CastCommand","");
            }
            String castcommand = config.getString("CastCommand");
            if(!config.contains("Variables")){
                config.createSection("Variables");
                config.createSection("Variables.1");
                config.set("Variables.1", "");
            }
            ArrayList<String> variables = new ArrayList<>();
            for(String var : config.getConfigurationSection("Variables.").getKeys(false)){
                variables.add(config.getString("Variables." + var));
            }
            try {
                config.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return new SpellObject(spellName,displayname,spellnode,lore,boltenabled,boltradius,boltdamage,
                    auraenabled,auratime,auraradius,auradamage,sprayenabled,sprayradius,spraydamage,
                    armorpiercing,moneycost,manacost,cooldown,chargetime,traveldistance,spellspeed,
                    requiredleveltobind,requiredleveltocast,requiredleveltodrop,
                    affectsmobs,affectself,affectenemy,affectteammates,
                    spellbook,spelleffectname,sound,soundvolume,soundpitch,
                    particleObjects,potionEffects,mobdropsenabled,mobDrops,
                    itemcostsenabled,itemcost, castcommand, variables);
            /**
            int count = 0;
            double damage = config.getDouble(path + ".Damage");
            boolean affectTeammates = config.getBoolean(path + ".AffectTeammates");
            boolean affectEnemy = config.getBoolean(path + ".AffectEnemy");
            boolean affectSelf = config.getBoolean(path + ".AffectSelf");
            boolean affectMobs = config.getBoolean(path +".AffectMobs");
            String tier = config.getString(path +".Tier");
            int radius = config.getInt(path + ".Radius");
            int timer = config.getInt(path +".Timer") * 20;
            double cost = config.getDouble(path + ".ManaCost");
            int cooldown = config.getInt(path + ".Cooldown");
            int moneyCost = config.getInt(path +  ".MoneyCost");
            double distance = config.getDouble(path + ".Distance");
            String sound = config.getString(path + ".Sound.Sound");
            Sound sound1 = Sound.valueOf(sound.toUpperCase());
            float volume = (float) config.getDouble(path + ".Sound.Volume");
            float pitch = (float) config.getDouble(path + ".Sound.Pitch");
            String SpellEffectName = config.getString(path + ".SpellEffect");
            SpellEffect spellEffect;
            if(main.mageSpellsManager.spellEffectManager.hasSpellEffect(SpellEffectName)){
                spellEffect = main.mageSpellsManager.spellEffectManager.getSpellEffect(SpellEffectName);
            }else{
                spellEffect = new NormalEffect();
            }
            int minimumlevel = config.getInt(path + ".MinimumLevel");
            // Gets particle info
            ArrayList<ParticleObject> effect = new ArrayList<>();
            for (String particleKey : config.getConfigurationSection(path + ".Particle.").getKeys(false)) {
                // Mage.Spells.Fire.Particle
                int amount = config.getInt(path +".Particle." + particleKey + ".Amount");
                double x = config.getDouble(path +  ".Particle."
                        + particleKey + ".offSetX");
                double y = config.getDouble(path + ".Particle."
                        + particleKey + ".offSetY");
                double z = config.getDouble(path + ".Particle."
                        + particleKey + ".offSetZ");
                float offSetX = (float) x;
                float offSetY = (float) y;
                float offSetZ = (float) z;
                double speedD = config.getDouble(path + ".Particle."
                        + particleKey + ".Speed");
                float speed = (float) speedD;
                String particle = particleKey;
                ParticleObject peffect = new ParticleObject(particle,
                        amount, offSetX, offSetY, offSetZ, speed);
                effect.add(peffect);
            }
            //Potion info
            ArrayList<PotionEffect> potion = new ArrayList<>();
            for (String potionKey : config.getConfigurationSection(
                    path + ".Effect.").getKeys(false)) {
                int duration = config.getInt(path + ".Effect."
                        + potionKey + ".Duration") * 20;
                int amplification = config.getInt(path + ".Effect."
                        + potionKey + ".Amplification");
                PotionEffect peffect = new PotionEffect(
                        PotionEffectType.getByName(potionKey.toLowerCase()),
                        duration, amplification);
                potion.add(peffect);
            }

            //SpellBook info

            boolean enabled = config.getBoolean(path + ".Drops.Settings.Enable");
            String bookName = config.getString(path + ".Drops.Settings.BookTitle");
            String bookLore = config.getString(path + ".Drops.Settings.BookLore");
            HashMap<EntityType, Double> mobDrops = new HashMap<>();
            //Mob Drop
            for (String mobKey : config.getConfigurationSection(path + ".Drops.").getKeys(false)) {
                if (!mobKey.equalsIgnoreCase("settings")) {
                    EntityType eType;
                    try {
                        eType = EntityType.valueOf(mobKey.toUpperCase());
                    } catch (Exception e) {
                        eType = null;
                    }
                    if (eType != null) {
                        double db = config.getDouble(path + ".Drops." + mobKey + ".Chance");
                        mobDrops.put(eType, db);
                    } else {
                        System.out.println("Could not register mob: " + mobKey);
                        System.out.println("Please make sure it is a valid mob!");
                    }
                }
            }
            //
            HashMap<ItemStack, Integer> itemCost = new HashMap<>();
            for(String itemKey : config.getConfigurationSection(path +".ItemCost").getKeys(false)){
                Material item;
                try {
                    short dmg = 0;
                    if (itemKey.contains("-")) {
                        dmg = Short.parseShort(itemKey.split("-")[1]);
                        item = Material.valueOf(itemKey.split("-")[0]);
                    } else {
                        item = Material.valueOf(itemKey);
                    }
                    if (item != null) {
                        ItemStack i = new ItemStack(item, 1, dmg);
                        itemCost.put(i,config.getInt(path + ".ItemCost." + itemKey));
                    }
                } catch (Exception e) {
                    System.out.println("Could not find the right material for this spells Item Cost: " + spellName);
                }
            }
            return new SpellObject(spellName,spellEffect,minimumlevel,effect,
                    potion,mobDrops,itemCost,enabled,damage,radius,distance,timer,
                    cost,cooldown,affectTeammates,affectEnemy,affectMobs,moneyCost,
                    affectSelf,sound1,pitch,volume,bookName,bookLore);
             **/
        }catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
