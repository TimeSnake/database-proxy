package de.timesnake.database.proxy.file;

import de.timesnake.database.core.file.DatabaseConfig;
import de.timesnake.database.core.file.DatabaseNotConfiguredException;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config implements DatabaseConfig {

    private File configFile = new File("plugins/database/config.yml");
    private Configuration config;

    public void onEnable() {

        //ConfigFile
        File dir = new File("plugins/database");
        if (dir.exists() == false) {
            dir.mkdir();
        }


        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }


            this.load();

            config.set("database.servers.url", "jdbc:mysql://localhost:3306/servers");
            config.set("database.servers.name", "servers");
            config.set("database.servers.tables.lobbys", "lobbys");
            config.set("database.servers.tables.games", "game");
            config.set("database.servers.tables.tempGames", "temp_games");
            config.set("database.servers.tables.builds", "builds");

            config.set("database.users.url", "jdbc:mysql://localhost:3306/users");
            config.set("database.users.name", "users");
            config.set("database.users.tables.info", "info");
            config.set("database.users.tables.aliases", "aliases");
            config.set("database.users.tables.punishments", "punishments");
            config.set("database.users.tables.coins", "time_coins");
            config.set("database.users.tables.mails", "mails");

            config.set("database.permissions.url", "jdbc:mysql://localhost:3306/permissions");
            config.set("database.permissions.name", "permissions");
            config.set("database.permissions.tables.permissions", "permissions");

            config.set("database.groups.url", "jdbc:mysql://localhost:3306/groups");
            config.set("database.groups.name", "groups");
            config.set("database.groups.tables.permGroups", "perm_groups");

            config.set("database.games_info.url", "jdbc:mysql://localhost:3306/games_info");
            config.set("database.games_info.name", "game_info");
            config.set("database.games_info.tables.infos", "infos");

            config.set("database.games_teams.url", "jdbc:mysql://localhost:3306/games_teams");
            config.set("database.games_teams.name", "game_teams");

            config.set("database.games_maps.url", "jdbc:mysql://localhost:3306/games_maps");
            config.set("database.games_maps.name", "game_maps");
            config.set("database.games_lounges.tables.info", "info");
            config.set("database.games_lounges.tables.locations", "locations");

            config.set("database.games_kits.url", "jdbc:mysql://localhost:3306/games_kits");
            config.set("database.games_kits.name", "game_kits");

            config.set("database.games_lounges.url", "jdbc:mysql://localhost:3306/games_lounges");
            config.set("database.games_lounges.name", "game_lounges");
            config.set("database.games_lounges.tables.maps", "maps");

            config.set("database.support.url", "jdbc:mysql://localhost:3306/support");
            config.set("database.support.name", "support");
            config.set("database.support.tables.tickets", "tickets");

            config.set("database.survival.url", "jdbc:mysql://localhost:3306/survival");
            config.set("database.survival.name", "survival");
            config.set("database.survival.tables.privateblocks", "privateblocks");

            config.set("database.decorations.url", "jdbc:mysql://localhost:3306/decorations");
            config.set("database.decorations.name", "decorations");
            config.set("database.decorations.tables.heads", "heads");

            config.set("database.url", "jdbc:mysql://localhost:3306/mysql");
            config.set("database.user", "root");
            config.set("database.password", "MineCraft");

            this.save();
        }
    }

    public void load() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getString(String path) {
        return config.getString(path);
    }

    @Override
    public String getDatabaseName(String databaseType) throws DatabaseNotConfiguredException {
        String name = config.getString("database." + databaseType + ".name");
        if (name != null) {
            return name;
        }
        throw new DatabaseNotConfiguredException(databaseType, "name");
    }

    @Override
    public String getDatabaseUrl(String databaseType) throws DatabaseNotConfiguredException {
        String url = config.getString("database." + databaseType + ".url");
        if (url != null) {
            return url;
        }
        throw new DatabaseNotConfiguredException(databaseType, "url");
    }

    @Override
    public String getDatabaseTable(String databaseType, String tableType) throws DatabaseNotConfiguredException {
        String tableName = config.getString("database." + databaseType + ".tables." + tableType);
        if (tableName != null) {
            return tableName;
        }
        throw new DatabaseNotConfiguredException(databaseType, tableType);
    }

}
