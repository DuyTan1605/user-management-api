/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vng.zing.managementuser.modules.ProfilerModule;

/**
 *
 * @author tanhd
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ProfilerModule());
        HServers hServers = injector.getInstance(HServers.class);

        if (!hServers.setupAndStart()) {
            System.err.println("Could not start http servers! Exit now.");
            System.exit(1);
        }
    }
}
