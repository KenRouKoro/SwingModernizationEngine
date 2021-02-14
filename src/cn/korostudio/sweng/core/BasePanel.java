package cn.korostudio.sweng.core;

import aurelienribon.tweenengine.BaseTween;
import cn.korostudio.sweng.data.Configuration;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {
    public BasePanel(Configuration configuration){
        setOpaque(true);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
