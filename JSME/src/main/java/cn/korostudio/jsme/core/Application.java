package cn.korostudio.jsme.core;

import cn.korostudio.jsme.data.Configuration;
import com.formdev.flatlaf.FlatLightLaf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

abstract public class Application {
    protected ApplicationCore applicationCore;
    protected Application application=this;
    protected BasePanel basePanel;
    static public boolean VLCSupport=true;
    private static boolean flInit=false;
    protected static boolean useFL=true;
    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    abstract public Configuration init();

    private static void flInit() throws Exception{
        flInit=true;
        FlatLightLaf.install();
        UIManager.put( "Button.arc", 0 );
        UIManager.put( "Component.arc", 0 );
        logger.info("Flat is Init.");
    }

    //标准启动方法
    public static void start(Class app) throws Exception{
        Application application=(Application) app.newInstance();
        Thread running = new Thread(new Runnable() {
            @Override
            public void run() {
                application.applicationCore=new ApplicationCore(application.init(),application);
                logger.debug("Flat Support:"+useFL);
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
        });
        running.setName("application");
        running.start();
    }
    abstract public void load(BasePanel basePanel);

    public abstract void stop();

    public ApplicationCore getApplicationCore() {
        return applicationCore;
    }
}