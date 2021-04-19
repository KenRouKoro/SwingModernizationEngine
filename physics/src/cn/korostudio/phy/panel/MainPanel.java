package cn.korostudio.phy.panel;

import cn.korostudio.jsme.data.Configuration;
import cn.korostudio.jsme.layout.FormDatas;
import cn.korostudio.jsme.layout.FormLayout;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    protected Configuration configuration;
    protected PaintPanel paintPanel;
    protected Looper looper;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
    public MainPanel(Configuration configuration){
        this.configuration=configuration;
        setLayout(null);
        paintPanel=new PaintPanel(configuration);
        paintPanel.setBounds((int)(0.2*configuration.windowW),0,(int)(0.6*configuration.windowW),(int)(0.6*configuration.windowH));
        add(paintPanel);
        looper =new Looper(paintPanel);
    }

    public Looper getLooper() {
        return looper;
    }
}
