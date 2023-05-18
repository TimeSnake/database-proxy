/*
 * Copyright (C) 2023 timesnake
 */

package de.timesnake.database.proxy.file;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import de.timesnake.database.core.DatabaseConfig;
import de.timesnake.database.core.DatabaseNotConfiguredException;
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
