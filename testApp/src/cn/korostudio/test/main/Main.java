package cn.korostudio.test.main;


import cn.korostudio.sweng.core.Application;
import cn.korostudio.sweng.core.BasePanel;
import cn.korostudio.sweng.data.Configuration;

import java.awt.*;

public class Main extends Application {

    @Override
    public Configuration init() {
        Configuration configuration= new Configuration();
        configuration.window=true;
        return configuration;
    }

    @Override
    public void load(BasePanel basePanel) {
        

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
