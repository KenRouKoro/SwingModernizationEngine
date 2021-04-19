package cn.korostudio.phy.main;

import cn.korostudio.jsme.core.Application;
import cn.korostudio.jsme.core.BasePanel;
import cn.korostudio.jsme.data.Configuration;
import cn.korostudio.jsme.layout.FormDatas;
import cn.korostudio.jsme.window.Loop;
import cn.korostudio.phy.panel.MainPanel;

import java.awt.*;

public class Main extends Application {
    protected Configuration configuration;
    protected MainPanel mainPanel;
    protected Loop loop;
    @Override
    public Configuration init() {
        configuration=new Configuration();
        configuration.title="测试用物理平面绘图程序";
        configuration.closingStop=true;
        configuration.fps=120;
        //configuration.game=true;
        return configuration;
    }

    @Override
    public void load(BasePanel basePanel) {
        FormDatas formDatas=new FormDatas(0f,configuration.windowH,0f, configuration.windowW);
        mainPanel=new MainPanel(configuration);
        //mainPanel.setBackground(Color.orange);
        basePanel.add(mainPanel,formDatas);
        loop=new Loop(configuration);
        loop.add(mainPanel);
        loop.start();
        mainPanel.getLooper().start();
    }

    @Override
    public void stop() {

    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl","true");
        try {
            Application.start(Main.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
