/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser;

/**
 *
 * @author tanhd
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HServers hServers = new HServers();
        if (!hServers.setupAndStart()) {
            System.err.println("Could not start http servers! Exit now.");
            System.exit(1);
        }
    }
}
