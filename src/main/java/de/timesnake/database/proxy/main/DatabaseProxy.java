/*
 * Copyright (C) 2023 timesnake
 */

package de.timesnake.database.proxy.main;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import de.timesnake.database.core.DatabaseNotConfiguredException;
import de.timesnake.database.proxy.file.Config;
import de.timesnake.database.util.Database;
import java.util.logging.Logger;

@Plugin(id = "database-proxy", name = "DatabaseProxy", version = "1.0-SNAPSHOT",
    url = "https://git.timesnake.de", authors = {"MarkusNils"},
    dependencies = {
        @Dependency(id = "channel-proxy")
    })
public class DatabaseProxy {

  public static ProxyServer getServer() {
    return server;
  }

  public static Logger getLogger() {
    return logger;
  }

  private static ProxyServer server;
  private static Logger logger;

  @Inject
  public DatabaseProxy(ProxyServer server, Logger logger) {
    DatabaseProxy.server = server;
    DatabaseProxy.logger = logger;
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) {
    Config config = new Config();
    try {
      Database.getInstance().connect(config);
    } catch (DatabaseNotConfiguredException e) {
      logger.warning(e.getMessage());
    }

    Database.getInstance().createTables();

    logger.info("[Database] Databases loaded successfully!");

  }
}
