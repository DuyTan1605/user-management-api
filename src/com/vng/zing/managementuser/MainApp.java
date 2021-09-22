/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vng.zing.dmp.common.module.CommonModule;
import com.vng.zing.managementuser.modules.UserMwModule;
import com.vng.zing.userservice.thrift.ListUserParams;
import com.vng.zing.userservice.thrift.wrapper.UserMwClient;
import org.apache.thrift.TException;

/**
 *
 * @author tanhd
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws TException {
        Injector injector = Guice.createInjector(new CommonModule(), new UserMwModule());
        HServers hServers = injector.getInstance(HServers.class);

        if (!hServers.setupAndStart()) {
            System.err.println("Could not start http servers! Exit now.");
            System.exit(1);
        }
    }
}
