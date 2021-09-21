/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.vng.zing.jettyserver.WebServers;
import com.vng.zing.managementuser.handlers.UserHandler;
import com.vng.zing.managementuser.handlers.UserListHandler;
import com.vng.zing.managementuser.modules.ProfilerModule;
import com.vng.zing.managementuser.services.UserListService;
import com.vng.zing.managementuser.services.UserService;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HServers {

    private UserListHandler userListHandler;
    private UserHandler userHandler;

    @Inject
    public HServers(UserListHandler userListHandler, UserHandler userHandler) {
        this.userListHandler = userListHandler;
        this.userHandler = userHandler;
    }

    public boolean setupAndStart() {
        WebServers servers = new WebServers("managementuser");
        ServletHandler handler = new ServletHandler();

        handler.addServletWithMapping(new ServletHolder(userListHandler), "/users");
        handler.addServletWithMapping(new ServletHolder(userHandler), "/user");
        servers.setup(handler);
        return servers.start();
    }
}
