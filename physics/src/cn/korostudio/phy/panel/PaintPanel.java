package cn.korostudio.phy.panel;

import cn.korostudio.jsme.data.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PaintPanel extends JPanel {
    protected int x=0;
    protected int y=0;
    protected BufferedImage bufferedImage;
    protected Configuration configuration;
    protected Graphics2D g2d;

    public PaintPanel(Configuration configuration){
        this.configuration=configuration;
        setBackground(Color.darkGray);
        bufferedImage=new BufferedImage(configuration.windowW, configuration.windowH, BufferedImage.TYPE_INT_ARGB);
        g2d=(Graphics2D) bufferedImage.getGraphics();
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d=(Graphics2D) g;
        g2d.setColor(Color.red);
        g2d.drawImage(bufferedImage,0,0,null);
        this.g2d.fillRect(getWidth()/2+x,getHeight()/2+y,5,5);

    }
}
