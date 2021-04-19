package cn.korostudio.jsme.core;

import cn.korostudio.jsme.data.Configuration;
import cn.korostudio.jsme.layout.FormLayout;
import cn.korostudio.jsme.window.Loop;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {
    protected Configuration configuration=null;
    protected boolean game=false;
    protected Loop gameLoop=null;
    public BasePanel(Configuration configuration){
        setOpaque(true);
        setBackground(Color.white);
        setBounds(0,0,configuration.windowW,configuration.windowH);
        //setSize(configuration.windowW,configuration.windowH);
        setLayout(new FormLayout());
        this.configuration=configuration;
        if (configuration.game){
            gameLoop=new Loop(configuration);
            gameLoop.add(this);
        }
    }

    public void startLoop(){
        gameLoop.start();
    }
    public void stopLoop(){
        gameLoop.stop();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
