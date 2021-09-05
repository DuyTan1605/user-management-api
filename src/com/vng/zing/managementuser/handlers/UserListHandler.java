/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser.handlers;

import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.managementuser.services.UserListService;
import com.vng.zing.zcommon.thrift.ECode;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONException;

public class UserListHandler extends HttpServlet {

    private static final Logger _Logger = ZLogger.getLogger(UserListHandler.class);
    private final UserListService userListService = new UserListService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        ApiResponse apiResponse = new ApiResponse();
        try {
            out = resp.getWriter();
            Object result = userListService.getList();
            apiResponse = (ApiResponse) result;
        } catch (Exception ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(ECode.C_FAIL.getValue());
            apiResponse.setData(null);
        } finally {
            try {
                out.print(apiResponse.getFinalResponse());
            } catch (JSONException ex) {
                _Logger.error(null, ex);
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
