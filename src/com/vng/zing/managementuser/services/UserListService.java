/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.managementuser.entity.UserDTO;
import com.vng.zing.userservice.thrift.ListUserParams;
import com.vng.zing.userservice.thrift.ListUserResult;
import com.vng.zing.userservice.thrift.User;
import com.vng.zing.userservice.thrift.wrapper.UserMwClient;
import com.vng.zing.managementuser.utils.DateTimeUtils;
import com.vng.zing.stats.Profiler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

public class UserListService {

    private static final Logger logger = ZLogger.getLogger(UserListService.class);
    public static final UserMwClient client = new UserMwClient("Main");

    private ApiResponse apiResponse = new ApiResponse();

    public UserListService() {
    }

    public List<UserDTO> getList() throws TException {
        Profiler.getThreadProfiler().push(this.getClass(), "getListUser");
        List<UserDTO> arrayData = new ArrayList<UserDTO>();
        try {
            ListUserResult result = client.getUsers(new ListUserParams());
            if (result.getCode() != 0) {
                return Collections.EMPTY_LIST;
            }
            for (User user : result.getData()) {
                UserDTO myUserDTO = new UserDTO();
                myUserDTO.setId(user.id);
                myUserDTO.setName(user.name);
                myUserDTO.setUserName(user.username);
                myUserDTO.setGender(user.gender);
                myUserDTO.setBirthday(DateTimeUtils.getLocalDateTime(user.birthday));
                myUserDTO.setUpdateTime(DateTimeUtils.getLocalDateTime(user.updatetime));
                myUserDTO.setCreateTime(DateTimeUtils.getLocalDateTime(user.createtime));
                arrayData.add(myUserDTO);
            }
        } finally {
            Profiler.getThreadProfiler().pop(this.getClass(), "getListUser");
        }
        return arrayData;
    }
}
