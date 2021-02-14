package cn.korostudio.test.main;


import cn.korostudio.sweng.core.Application;
import cn.korostudio.sweng.core.BasePanel;
import cn.korostudio.sweng.data.Configuration;

import java.awt.*;

public class Main extends Application {

    @Override
    public Configuration init() {
        Configuration configuration= new Configuration();
        configuration.window=false;
        configuration.closingStop=true;
        return configuration;
    }

    @Override
    public void load(BasePanel basePanel) {
        basePanel.setOpaque(true);

    }

    @Override
    public void stop(){

    }

    public static void main(String[] args) {
        try {
            start(Main.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
