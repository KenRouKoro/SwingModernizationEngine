package cn.korostudio.sweng.core;

import cn.korostudio.sweng.data.Configuration;

import java.awt.*;

abstract public class Application {
    protected ApplicationCore applicationCore;
    protected Application application=this;

    abstract public Configuration init();

    public static void start(Class app) throws Exception{
        Application application=(Application) app.newInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                application.applicationCore=new ApplicationCore(application.init(),application);
                application.load();

            }
        }).start();
    }
    abstract public Component load();

    public abstract void stop();

    public ApplicationCore getApplicationCore() {
        return applicationCore;
    }
}