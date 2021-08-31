/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser.handlers;

import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.stats.Profiler;
import com.vng.zing.stats.ThreadProfiler;
import com.vng.zing.managementuser.services.UserListService;
import com.vng.zing.userservice.thrift.ListUserResult;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class UserListHandler extends HttpServlet {

    private static final Logger _Logger = ZLogger.getLogger(UserListHandler.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            JSONObject result = UserListService.Instance.getList();
            out.print(result);
        } catch (Exception ex) {
            _Logger.error(null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
