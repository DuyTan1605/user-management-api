/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.managementuser.entity.UserDTO;
import com.vng.zing.stats.Profiler;
import com.vng.zing.stats.ThreadProfiler;
import com.vng.zing.userservice.thrift.ListUserParams;
import com.vng.zing.userservice.thrift.ListUserResult;
import com.vng.zing.userservice.thrift.User;
import com.vng.zing.userservice.thrift.wrapper.UserMwClient;
import com.vng.zing.managementuser.utils.DateTimeUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

public class UserListService {

    private static final Logger _Logger = ZLogger.getLogger(UserListService.class);
    public static final UserMwClient client = new UserMwClient("Main");
    private ApiResponse apiResponse = new ApiResponse();

    public UserListService() {
    }

    public Object getList() throws TException, JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        JSONArray arrayData = new JSONArray();
        ListUserResult result = client.getUsers(new ListUserParams());
        profiler.push(this.getClass(), "output");
        for (User user : result.getData()) {
            UserDTO myUser = new UserDTO(user.id, user.name, user.username, user.gender, DateTimeUtils.getLocalDateTime(user.birthday), DateTimeUtils.getLocalDateTime(user.createtime), DateTimeUtils.getLocalDateTime(user.updatetime));
            arrayData.add(myUser.getUserResponse());
        }
        apiResponse.setCode(result.getCode());
        apiResponse.setData(new JSONObject().put("users", arrayData));
        profiler.pop(this.getClass(), "output");
        return apiResponse;
    }
}