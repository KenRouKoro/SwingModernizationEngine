package cn.korostudio.jsme.window;

import cn.korostudio.jsme.data.Configuration;
import cn.korostudio.jsme.listener.CallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowController {
    private final static Logger logger = LoggerFactory.getLogger(WindowController.class);
    protected Window window;
    protected Configuration conf;
    protected boolean isWindow = false;
    protected boolean show = false;
    protected CallBack closeCallBack = null;
    protected Loop loop = null;

    public WindowController(Configuration conf) {
        this.conf = conf;
        init();
    }

    public void setCloseCallBack(CallBack closeCallBack) {
        this.closeCallBack = closeCallBack;
    }

    public void repaint() {
        window.repaint();
    }

    public void removeCloseCallBack() {
        closeCallBack = null;
    }

    //内部初始化方法
    private void init() {
        this.isWindow = conf.window;
        if (isWindow) {
            this.window = new JWindow();
            ((JWindow) window).setBackground(new Color(255, 255, 255, 0));
            ((JWindow) window).setIconImage(conf.icon);
        } else {
            this.window = new JFrame(conf.title);
            ((JFrame) window).setResizable(conf.resize);
            ((JFrame) window).setIconImage(conf.icon);
        }
        logger.debug("IsWindow:" + isWindow);
        window.setSize(conf.windowW, conf.windowH);
        logger.debug("Window Size is:w=" + conf.windowW + " h=" + conf.windowH);
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

                if (closeCallBack != null) closeCallBack.run();
                if (conf.closingStop) System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        setFullSceen(conf.fullSeen);
        window.setLayout(null);
        loop = new Loop(conf);
        loop.add(window);
        logger.info("WindowController Init Ending.");
    }

    public void startLoop() {
        loop.start();
        logger.debug("Start Game Loop");
    }

    public void stopLoop() {
        loop.stop();
        logger.debug("Stop Game Loop");
    }

    //设置窗口图标
    public void setICON(Image image) {
        window.setIconImage(image);
        logger.debug("SetIcon:" + image.toString());
    }

    //设置全屏方法
    private void setFullSceen(Window window) {
        if (window != null) ((JFrame) window).getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //通过调用GraphicsEnvironment的getDefaultScreenDevice方法获得当前的屏幕设备了
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        // 全屏设置
        gd.setFullScreenWindow(window);
    }

    //将当前窗口设置全屏
    public void setFullSceen(boolean fullSceen) {
        if (fullSceen) {
            setFullSceen(window);
        } else {
            setFullSceen(null);
        }
        logger.debug("SetFullSceen:" + fullSceen);
    }

    public Window getWindow() {
        return window;
    }

    //设置标题
    public void setTitle(String title) {
        if (!isWindow) {
            JFrame frame = (JFrame) window;
            frame.setTitle(title);
        }
        window.repaint();
        logger.debug("SetTitle:" + title);
    }

    //设置窗口是否显示
    public void show(boolean show) {
        this.show = show;
        window.setVisible(show);
        window.repaint();
    }

    public boolean isShow() {
        return show;
    }

    public boolean isWindow() {
        return isWindow;
    }

    public Configuration getConf() {
        return conf;
    }

    //设置配置文件
    public void setConf(Configuration conf) {
        this.conf = conf;
        Component[] components = window.getComponents();
        window.setVisible(false);
        init();
        for (Component component : components)
            window.add(component);
    }
}
