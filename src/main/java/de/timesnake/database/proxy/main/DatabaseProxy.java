package de.timesnake.database.proxy.main;

import de.timesnake.database.core.file.DatabaseNotConfiguredException;
import de.timesnake.database.proxy.file.Config;
import de.timesnake.database.util.Database;
import net.md_5.bungee.api.plugin.Plugin;


public class DatabaseProxy extends Plugin {

    @Override
    public void onEnable() {
        Config config = new Config();
        config.onEnable();
        config.load();
        try {
            Database.getInstance().connect(config);
        } catch (DatabaseNotConfiguredException e) {
            System.out.println(e.getMessage());
        }

        Database.getInstance().createTables();

        System.out.println("[Database] Databases loaded successfully!");

    }
}
