package com.coderxiao.admin.http.servlet;

import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.*;


@Path("/site")
public class Site {

    @GET
    @Path("/")
    @Produces("application/json;charset=UTF-8")
    public String getAll() {
        return "";
    }

    @GET
    @Path("/{id}")
    @Produces("application/json;charset=UTF-8")
    public String getId(@PathParam("id") Long id) {
        String result = ""  + id;
        return result;

    }

    @GET
    @Path("/{id}/{methodName}")
    @Produces("application/json;charset=UTF-8")
    public String get(@PathParam("id") Long id, @PathParam("methodName") String methodName) {
        String result = id + methodName;
        return JSONObject.toJSONString(result);
    }

    @POST
    @Path("/{id}/{methodName}")
    @Produces("application/json;charset=UTF-8")
    public String post(@PathParam("id") Long id, @PathParam("methodName") String methodName) {
        String result = id + methodName;
        return JSONObject.toJSONString(result);
    }

}
