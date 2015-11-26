package com.coderxiao;

import com.coderxiao.http.jetty.JettyServer;
import com.coderxiao.webservice.SpiderServer;

public class Main {

    public static void main(String[] args) {
        Thread webservice = new Thread(new Runnable() {
            @Override
            public void run() {
                SpiderServer spiderServer = new SpiderServer();
                spiderServer.start();
                System.out.println("Spider WebService start...");
            }
        });

        Thread jetty = new Thread(new Runnable() {
            @Override
            public void run() {
                JettyServer jettyServer = new JettyServer();
                jettyServer.start();
                System.out.println("Spider Jetty start...");
            }


        });

        webservice.start();
        jetty.start();

    }
}
