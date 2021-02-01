package cn.korostudio.sweng.core;

import cn.korostudio.sweng.data.Configuration;

abstract public class Application {
    //final public
    protected ApplicationCore applicationCore;

    abstract public Configuration init();

    public static void start(Class app) throws Exception{
        Application application=(Application) app.newInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                application.applicationCore=new ApplicationCore(application.init());
                application.load();
            }
        });
    }
    abstract public void load();

    public ApplicationCore getApplicationCore() {
        return applicationCore;
    }
}