package cn.korostudio.jsme.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TestButton extends JComponent{
    protected String buttonString=null;
    protected Font font=null;
    public TestButton(){
        setOpaque(true);
    }

    @Override
    protected void printComponent(Graphics g) {
        super.printComponent(g);
        Graphics2D g2d=(Graphics2D)g;
        //绘制底层
        Rectangle2D rec = new Rectangle2D.Double(0,0,getWidth(),getHeight());
        g2d.setColor(UICore.UI.MainColor);
        g2d.fill(rec);
        //绘制边框
        //g2d.rect;
    }
}
