package cn.korostudio.sweng.core;

import cn.korostudio.sweng.data.Configuration;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;

abstract public class Application {
    protected ApplicationCore applicationCore;
    protected Application application=this;
    protected BasePanel basePanel;
    static public boolean VLCSupport=true;
    static public boolean FXSupport=true;

    abstract public Configuration init();

    public static void start(Class app) throws Exception{
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        UIManager.put("RootPane.setupButtonVisible", false);
        Application application=(Application) app.newInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                application.applicationCore=new ApplicationCore(application.init(),application);
                application.basePanel =new BasePanel(application.applicationCore.conf);
                application.applicationCore.init(application.basePanel);
                application.load(application.basePanel);

            }
        }).start();
    }
    abstract public void load(BasePanel basePanel);

    public abstract void stop();

    public ApplicationCore getApplicationCore() {
        return applicationCore;
    }
}