package cn.korostudio.test.main;

import cn.korostudio.jsme.core.Application;
import cn.korostudio.jsme.core.BasePanel;
import cn.korostudio.jsme.data.Configuration;

import cn.korostudio.jsme.layout.FormDatas;
import cn.korostudio.test.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class Main extends Application {
    protected MainPanel mainPanel;

    @Override
    public Configuration init() {
        Configuration configuration= new Configuration();
        configuration.window=false;
        configuration.closingStop=true;
        configuration.fullSeen =false;
        configuration.title="Test";
        return configuration;
    }

    @Override
    public void load(BasePanel basePanel) {


        basePanel.setBackground(new Color(255,165,0, 128));
        JButton jButton = new JButton("Hello World!");
        FormDatas formData=new FormDatas(0.8f,50,0.8f,200);
        //VideoPlayer videoPlayer=new VideoPlayer();
        FormDatas videodata=new FormDatas(0.1f,400,0.1f,400);
        basePanel.add(jButton,formData);
        //basePanel.add(videoPlayer,videodata);
        //videoPlayer.setVideoFile(new File("C:\\Users\\koro\\Videos\\2021-02-21 21-36-03.mp4"));
        //videoPlayer.repaint();
    }

    @Override
    public void stop(){

    }

     int sub(int x,int y){
        int z=x+y;
        return 5;
     }

    public static void main(String[] args) {
        try {
            useBE=false;
            start(Main.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
