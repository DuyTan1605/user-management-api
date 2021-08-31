/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.handlers;

import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.models.UserListModel;
import com.vng.zing.managementuser.models.UserModel;
import com.vng.zing.stats.Profiler;
import com.vng.zing.stats.ThreadProfiler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author tanhd
 */
public class UserHandler extends HttpServlet {

    private static final Logger _Logger = ZLogger.getLogger(UserHandler.class);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doProcess(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doProcess(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doProcess(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) {
        ThreadProfiler profiler = Profiler.createThreadProfilerInHttpProc("UserHandler", req);
        System.out.println(req.getMethod());
        try {
            switch (req.getMethod()) {
                case "GET": {
                    UserModel.Instance.getUser(req, resp);
                    break;
                }
                case "POST": {
                    UserModel.Instance.createUser(req, resp);
                    break;
                }
                case "PUT": {
                    UserModel.Instance.updateUser(req, resp);
                    break;
                }
                case "DELETE": {
                    UserModel.Instance.deleteUser(req, resp);
                    break;
                }
            }

        } catch (Exception ex) {
            _Logger.error(null, ex);
        } finally {
            Profiler.closeThreadProfiler();
        }
    }
}
