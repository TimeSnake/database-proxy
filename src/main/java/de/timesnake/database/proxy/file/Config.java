/*
 * database-proxy.main
 * Copyright (C) 2022 timesnake
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package de.timesnake.database.proxy.file;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import de.timesnake.database.core.file.DatabaseConfig;
import de.timesnake.database.core.file.DatabaseNotConfiguredException;

import java.io.File;
import java.io.IOException;

public class Config implements DatabaseConfig {

    protected final File configFile;
    protected Toml config;
    protected TomlWriter writer;

    public Config() {
        this.configFile = new File("plugins/database/config.toml");

        //directory creation
        File dir = new File("plugins/database");
        if (!dir.exists()) {
            dir.mkdir();
        }

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = new Toml();

        this.load();
    }

    public void load() {
        config.read(configFile);
    }

    public void save() {
        writer.write(configFile);
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

}
