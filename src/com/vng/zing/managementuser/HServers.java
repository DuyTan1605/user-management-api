/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vng.zing.jettyserver.WebServers;
import com.vng.zing.managementuser.handlers.UserHandler;
import com.vng.zing.managementuser.handlers.UserListHandler;
import com.vng.zing.managementuser.modules.UserListModule;
import com.vng.zing.managementuser.modules.UserListServiceModule;
import com.vng.zing.managementuser.modules.UserServiceModule;
import com.vng.zing.managementuser.services.UserListService;
import com.vng.zing.managementuser.services.UserService;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HServers {

    public boolean setupAndStart() {
        WebServers servers = new WebServers("managementuser");
        ServletHandler handler = new ServletHandler();

        Injector injector = Guice.createInjector(new UserListServiceModule(), new UserServiceModule());
        UserListService userListService = injector.getInstance(UserListService.class);
        UserService userService = injector.getInstance(UserService.class);

        handler.addServletWithMapping(new ServletHolder(new UserListHandler(userListService)), "/users");
        handler.addServletWithMapping(new ServletHolder(new UserHandler(userService)), "/user");
        servers.setup(handler);
        return servers.start();
    }
}
