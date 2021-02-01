package cn.korostudio.sweng.window;

import cn.korostudio.sweng.data.Configuration;

import javax.swing.*;
import java.awt.*;

public class WindowController {
    protected Window window;
    protected Configuration conf;
    protected boolean iswindow=false;
    protected boolean show=false;
    public WindowController(Configuration conf){
        this.conf=conf;
        init();
    }
    //内部初始化方法
    private void init(){
        this.iswindow=conf.window;
        if (iswindow)this.window=new JWindow();
        else this.window=new JFrame(conf.title);
        setFullSceen(conf.fullSceen);
    }
    //设置配置文件
    public void setConf(Configuration conf){
        this.conf=conf;
        Component components[]=window.getComponents();
        window.setVisible(false);
        init();
        for (Component component:components)
        window.add(component);
    }
    //设置全屏方法
    private Window setFullSceen(Window window){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //通过调用GraphicsEnvironment的getDefaultScreenDevice方法获得当前的屏幕设备了
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        // 全屏设置
        gd.setFullScreenWindow(window);
        return window;
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
