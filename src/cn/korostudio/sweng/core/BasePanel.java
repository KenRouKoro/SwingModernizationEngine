package cn.korostudio.sweng.core;

import aurelienribon.tweenengine.BaseTween;
import cn.korostudio.sweng.data.Configuration;
import cn.korostudio.sweng.layout.FormLayout;

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
