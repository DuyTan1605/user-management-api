/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.vng.zing.userservice.thrift.UserService;
import com.vng.zing.userservice.thrift.wrapper.UserMwClient;

/**
 *
 * @author tanhd
 */
public class UserMwModule extends AbstractModule {

    @Override
    protected void configure() {
//        try {
//            bind(UserService.Iface.class).toConstructor(UserMwClient.class.getConstructor(String.class));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Provides
    public UserMwClient provideUserMwClient() {
        return new UserMwClient("Main");
    }
}
