package cn.korostudio.test.main;


import cn.korostudio.jsme.core.Application;
import cn.korostudio.jsme.core.BasePanel;
import cn.korostudio.jsme.data.Configuration;
import cn.korostudio.jsme.layout.FormDatas;
import cn.korostudio.test.panel.MainPanel;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main extends Application {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);
    protected MainPanel mainPanel;

    @Override
    public Configuration init() {
        Configuration configuration= new Configuration();
        configuration.window=false;
        configuration.closingStop=true;
        configuration.fullSeen =false;
        configuration.title="Test";
        configuration.game=true;
        return configuration;
    }

    @Override
    public void load(BasePanel basePanel) {

        basePanel.setBackground(new Color(255,165,0, 128));
        JButton jButton = new JButton("Hello World!");
        jButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logger.error("test");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        FormDatas formData=new FormDatas(0.8f,50,0.8f,200);
        FormDatas videoData=new FormDatas(0.1f,400,0.1f,400);
        basePanel.add(jButton,formData);
    }

    @Override
    public void stop(){

    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl","true");
        try {
            start(Main.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.error("test");
    }
}
