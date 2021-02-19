package cn.korostudio.sweng.window;

import cn.korostudio.sweng.data.Configuration;
import cn.korostudio.sweng.listener.CallBack;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowController {
    protected Window window;
    protected Configuration conf;
    protected boolean iswindow=false;
    protected boolean show=false;
    protected CallBack closeCallBack=null;

    public void setCloseCallBack(CallBack closeCallBack) {
        this.closeCallBack = closeCallBack;
    }

    public WindowController(Configuration conf){
        this.conf=conf;
        init();
    }

    public void removeCloseCallBack(){
        closeCallBack=null;
    }
    //内部初始化方法
    private void init(){
        this.iswindow=conf.window;
        if (iswindow) {
            this.window = new JWindow();
            ((JWindow)window).setBackground(new Color(255,255,255,0));
        }
        else {
            this.window = new JFrame(conf.title);
            ((JFrame)window).setResizable(conf.resize);
        }
        window.setSize(conf.windowW,conf.windowH);
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (closeCallBack!=null)closeCallBack.run();
                if (conf.closingStop)System.exit(0);
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

        setFullSceen(conf.fullSceen);
    }
    //设置配置文件
    public void setConf(Configuration conf){
        this.conf=conf;
        Component[] components =window.getComponents();
        window.setVisible(false);
        init();
        for (Component component:components)
        window.add(component);
    }
    //设置窗口图标
    public void setICON(Image image){
        window.setIconImage(image);
    }
    //设置全屏方法
    private void setFullSceen(Window window){
        if (window!=null)((JFrame)window).getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //通过调用GraphicsEnvironment的getDefaultScreenDevice方法获得当前的屏幕设备了
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        // 全屏设置
        gd.setFullScreenWindow(window);
    }
    //将当前窗口设置全屏
    public void setFullSceen(boolean fullSceen){
        if (fullSceen){
            setFullSceen(window);
        }else {
            setFullSceen(null);
        }
    }

    public Window getWindow() {
        return window;
    }
    //设置标题
    public void setTitle(String title){
        if(!iswindow){
            JFrame frame=(JFrame)window;
            frame.setTitle(title);
        }
        window.repaint();
    }
    //设置窗口是否显示
    public void show(boolean show){
        this.show=show;
        window.setVisible(show);
        window.repaint();
    }

    public boolean isShow() {
        return show;
    }

    public boolean isWindow() {
        return iswindow;
    }

    public Configuration getConf() {
        return conf;
    }
}
