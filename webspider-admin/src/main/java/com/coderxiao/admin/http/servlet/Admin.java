package com.coderxiao.admin.http.servlet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/admin")
public class Admin {

    @GET
    @Path("/")
    @Produces("application/json;charset=UTF-8")
    public String getAll() {
        return "";
    }

    @GET
    @Path("/{methodName}")
    @Produces("application/json;charset=UTF-8")
    public String get(@PathParam("methodName") String methodName) {
        String result = methodName;
        return result;
    }
}
