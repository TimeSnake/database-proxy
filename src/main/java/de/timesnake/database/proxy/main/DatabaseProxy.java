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

package de.timesnake.database.proxy.main;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import de.timesnake.database.core.file.DatabaseNotConfiguredException;
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
