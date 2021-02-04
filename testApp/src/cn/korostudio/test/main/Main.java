package cn.korostudio.test.main;


import cn.korostudio.sweng.core.Application;
import cn.korostudio.sweng.data.Configuration;

public class Main extends Application {

    @Override
    public Configuration init() {
        return new Configuration();
    }

    @Override
    public void load() {

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
