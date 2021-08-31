/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser.handlers;

import com.vng.zing.logger.ZLogger;
import com.vng.zing.stats.Profiler;
import com.vng.zing.stats.ThreadProfiler;
import com.vng.zing.managementuser.models.UserListModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class UserListHandler extends HttpServlet {

    private static final Logger _Logger = ZLogger.getLogger(UserListHandler.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) {
        ThreadProfiler profiler = Profiler.createThreadProfilerInHttpProc("UserListHandler", req);
        try {
            UserListModel.Instance.getList(req, resp);
        } catch (Exception ex) {
            _Logger.error(null, ex);
        } finally {
            Profiler.closeThreadProfiler();
        }
    }
}
