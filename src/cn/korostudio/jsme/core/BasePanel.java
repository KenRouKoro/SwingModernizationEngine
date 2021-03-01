package cn.korostudio.jsme.core;

import cn.korostudio.jsme.data.Configuration;
import cn.korostudio.jsme.layout.FormLayout;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {
    public BasePanel(Configuration configuration){
        setOpaque(true);
        setBackground(Color.white);
        setSize(configuration.windowW,configuration.windowH);
        setLayout(new FormLayout());

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
