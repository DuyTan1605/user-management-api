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

    private Object data;

    public ApiResponse() {

    }

    public ApiResponse(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public JSONObject getFinalResponse() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("code", this.code);
        result.put("data", this.data);
        return result;
    }

    public JSONObject createMessageError(String message) throws JSONException {
        return new JSONObject().put("message", message);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
