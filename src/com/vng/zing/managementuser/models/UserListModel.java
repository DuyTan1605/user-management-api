/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser.models;

import com.vng.zing.logger.ZLogger;
import com.vng.zing.stats.Profiler;
import com.vng.zing.stats.ThreadProfiler;
import com.vng.zing.userservice.thrift.ListUserParams;
import com.vng.zing.userservice.thrift.ListUserResult;
import com.vng.zing.userservice.thrift.User;
import com.vng.zing.userservice.thrift.wrapper.UserMwClient;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

public class UserListModel {

    private static final Logger _Logger = ZLogger.getLogger(UserListModel.class);
    public static final UserListModel Instance = new UserListModel();

    private UserListModel() {
    }

    public void getList(HttpServletRequest req, HttpServletResponse resp) throws TException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            UserMwClient client = new UserMwClient("Main");
            ListUserResult result = client.getUsers(new ListUserParams());
            profiler.push(this.getClass(), "output");
            JSONObject obj = new JSONObject();
            JSONArray arrayData = new JSONArray();
            for (User user : result.getData()) {
                JSONObject temp = new JSONObject();
                temp.put("id", user.id);
                temp.put("name", user.name);
                temp.put("username", user.username);
                temp.put("password", user.password);
                temp.put("birthday", user.birthday);
                temp.put("createtime", user.createtime);
                temp.put("updatetime", user.updatetime);
                arrayData.add(temp);
            }
            obj.put("code", result.getCode());
            obj.put("data", new JSONObject().put("users", arrayData));
            out.println(obj);
            profiler.pop(this.getClass(), "output");
        } catch (Exception ex) {
            _Logger.error(null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
