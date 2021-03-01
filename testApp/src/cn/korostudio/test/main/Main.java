package cn.korostudio.test.main;

import cn.korostudio.jsme.core.Application;
import cn.korostudio.jsme.core.BasePanel;
import cn.korostudio.jsme.data.Configuration;
import cn.korostudio.jsme.layout.FormAttachment;
import cn.korostudio.jsme.layout.FormData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main extends Application {

    @Override
    public Configuration init() {
        Configuration configuration= new Configuration();
        configuration.window=false;
        configuration.closingStop=true;
        configuration.fullSceen=false;
        configuration.title="Test";
        return configuration;
    }

    @Override
    public void load(BasePanel basePanel) {
        basePanel.setBackground(new Color(255,165,0, 128));
        JButton jButton = new JButton("Hello World!");
        final FormData formData = new FormData();
        formData.top = new FormAttachment(0.2f, 0);
        formData.left = new FormAttachment(0.5f, 0);
        formData.bottom = new FormAttachment(0.2f, 60);
        formData.right = new FormAttachment(0.5f, 100);
        basePanel.add(jButton,formData);
        jButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //application.getApplicationCore().getWindowController().setFullSceen(true);
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
