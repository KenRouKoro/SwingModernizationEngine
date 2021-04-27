package cn.korostudio.jsme.core;

import cn.korostudio.jsme.data.Configuration;
import com.formdev.flatlaf.FlatLightLaf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;

abstract public class Application {
    private final static Logger logger = LoggerFactory.getLogger(Application.class);
    static public boolean VLCSupport = true;
    protected static boolean useFL = true;
    private static boolean flInit = false;
    protected ApplicationCore applicationCore;
    protected Application application = this;
    protected BasePanel basePanel;

    //flat内部初始化方法
    private static void flInit() throws Exception {
        flInit = true;
        FlatLightLaf.install();
        UIManager.put("Button.arc", 0);
        UIManager.put("Component.arc", 0);
        logger.info("Flat is Init.");
    }

    //标准启动方法
    public static void start(Class app) throws Exception {
        insideStart(app, (Object) null);
    }

    //标准启动方法，带参数
    public static void start(Class app, Object... sent) throws Exception {
        insideStart(app, sent);
    }

    //内部的统一启动方法
    private static void insideStart(Class app, Object... sent) throws Exception {
        Application application = (Application) app.newInstance();
        logger.info("正在启动：" + application.getName());
        application.sentData(sent);
        Thread running = new Thread(new Runnable() {
            @Override
            public void run() {
                application.applicationCore = new ApplicationCore(application.init(), application);
                logger.debug("Flat Support:" + useFL);
                if (useFL) {
                    if (!flInit) {
                        try {
                            flInit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                application.basePanel = new BasePanel(application.applicationCore.conf);
                application.applicationCore.init(application.basePanel);
                application.load(application.basePanel);
                application.basePanel.repaint();
                application.getApplicationCore().getWindowController().repaint();
            }
        });
        running.setName("application");
        running.start();
    }

    public ArrayList<Object> main(String... args) {
        return null;
    }

    public String getName() {
        return "NULL";
    }

    //初始化并向引擎提供配置参数
    abstract public Configuration init();

    //向静态启动实现的类传递参数
    public void sentData(Object... obj) {
    }

    //应用加载方法
    abstract public void load(BasePanel basePanel);

    //在窗口被关闭时会调用
    public abstract void stop();

    public ApplicationCore getApplicationCore() {
        return applicationCore;
    }
}