/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser;

import com.vng.zing.jettyserver.WebServers;
import com.vng.zing.managementuser.handlers.UserHandler;
import com.vng.zing.managementuser.handlers.UserListHandler;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 *
 * @author namnq
 */
public class HServers {

    public boolean setupAndStart() {
        WebServers servers = new WebServers("managementuser");
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(UserListHandler.class, "/users");
        handler.addServletWithMapping(UserHandler.class, "/user");
        servers.setup(handler);
        return servers.start();
    }
}
