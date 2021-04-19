package cn.korostudio.jsme.core;

import cn.korostudio.jsme.data.Configuration;
import com.formdev.flatlaf.FlatLightLaf;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;

abstract public class Application {
    protected ApplicationCore applicationCore;
    protected Application application=this;
    protected BasePanel basePanel;
    static public boolean VLCSupport=true;
    static public boolean FXSupport=true;
    private static boolean beInit=false;
    protected static boolean useBE=false;
    private static boolean flInit=false;
    protected static boolean useFL=true;

    abstract public Configuration init();

    private static void beInit() throws Exception {
        beInit=true;
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        UIManager.put("RootPane.setupButtonVisible", false);
    }
    private static void flInit() throws Exception{
        flInit=true;
        FlatLightLaf.install();
        UIManager.put( "Button.arc", 0 );
        UIManager.put( "Component.arc", 0 );
    }


    public static void start(Class app) throws Exception{
        Application application=(Application) app.newInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                application.applicationCore=new ApplicationCore(application.init(),application);
                if (useBE){
                    if (!beInit) {
                        try {
                            beInit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (useFL){
                    if (!flInit){
                        try {
                            flInit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                application.basePanel =new BasePanel(application.applicationCore.conf);
                application.applicationCore.init(application.basePanel);
                application.load(application.basePanel);
                application.basePanel.repaint();
                application.getApplicationCore().getWindowController().repaint();
            }
        }).start();
    }
    abstract public void load(BasePanel basePanel);

    public abstract void stop();

    public ApplicationCore getApplicationCore() {
        return applicationCore;
    }
}