/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author tanhd
 */
public class ApiResponse {

    private int code;

    private JSONObject data;

    public ApiResponse() {

    }

    public ApiResponse(int code, JSONObject data) {
        this.code = code;
        this.data = data;
    }

    public JSONObject getFinalResponse() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("code", this.code);
        result.put("data", this.data);
        return result;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

}
